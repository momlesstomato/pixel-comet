package com.cometproject.server.api;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cometproject.api.config.Configuration;
import com.cometproject.api.config.api.ApiConfiguration;
import com.cometproject.api.utilities.Startable;
import com.cometproject.server.api.routes.PhotoRoutes;
import com.cometproject.server.api.routes.PlayerRoutes;
import com.cometproject.server.api.routes.RoomRoutes;
import com.cometproject.server.api.routes.SystemRoutes;
import com.cometproject.server.boot.CometBootstrap;

import io.javalin.Javalin;
import io.javalin.http.Context;


public class APIManager implements Startable {
    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(APIManager.class.getName());
    /**
     * Create an array of config properties that are required before enabling the API
     * If none of these properties exist, the API will be automatically disabled
     */
        private static final String[] configProperties = new String[]{
            ApiConfiguration.ENABLED,
            ApiConfiguration.PORT,
            ApiConfiguration.TOKEN,
            ApiConfiguration.TOKEN_HEADER
        };
    /**
     * The global API Manager instance
     */
    /**
     * Is the API enabled?
     */
    private boolean enabled;

    /**
     * The port the API server will listen on
     */
    private int port;

    /**
     * The token used for authentication
     */
    private String authToken;
    private String authHeader;
    private Javalin app;

    /**
     * Construct the API manager
     */
    public APIManager() {

    }

    public static APIManager getInstance() {
        return CometBootstrap.resolve(APIManager.class);
    }

    /**
     * Initialize the API
     */
    @Override
    public void start() {
        this.initializeConfiguration();

        if (!this.enabled) {
            return;
        }

        this.validateAuthenticationConfiguration();
        this.initializeJavalin();
        this.initializeRouting();
        this.app.start(this.port);
    }

    @Override
    public void stop() {
        if (this.app != null) {
            this.app.stop();
        }
    }

    /**
     * Initialize the configuration
     */
    private void initializeConfiguration() {
        for (String configProperty : configProperties) {
            if (!Configuration.currentConfig().containsKey(configProperty)) {
                LOGGER.warn("API configuration property not available: " + configProperty + ", API is disabled");
                this.enabled = false;

                return;
            }
        }

        this.enabled = Configuration.currentConfig().getProperty(ApiConfiguration.ENABLED).equals("true");
        this.port = Integer.parseInt(Configuration.currentConfig().getProperty(ApiConfiguration.PORT));
        this.authToken = Configuration.currentConfig().getProperty(ApiConfiguration.TOKEN);
        this.authHeader = Configuration.currentConfig().getProperty(ApiConfiguration.TOKEN_HEADER);
    }

    /**
     * Initializes the Javalin management API server.
     */
    private void initializeJavalin() {
        this.app = Javalin.create(config -> config.showJavalinBanner = false);
        this.app.before(this::authenticate);
        this.app.exception(ApiUnauthorizedException.class, (exception, context) -> {
            context.header("www-authenticate", this.authHeader);
            ApiResponseUtils.error(context, 401, "invalid_auth_token", "Invalid authentication token.");
        });
    }

    /**
     * Initialize the API routing
     */
    private void initializeRouting() {
        this.app.get("/", context -> ApiResponseUtils.error(context, 404, "not_found", "Invalid request."));
        this.app.get("/openapi/spec", this::openApiSpec);

        this.app.get("/player/{id}/reload", PlayerRoutes::reloadPlayerData);
        this.app.get("/player/{id}/disconnect", PlayerRoutes::disconnect);
        this.app.post("/player/{id}/alert", PlayerRoutes::alert);
        this.app.get("/player/{id}/badge/{badge}", PlayerRoutes::giveBadge);

        this.app.get("/rooms/active/all", RoomRoutes::getAllActiveRooms);
        this.app.get("/room/{id}/{action}", RoomRoutes::roomAction);

        this.app.get("/system/status", SystemRoutes::status);
        this.app.get("/system/shutdown", SystemRoutes::shutdown);
        this.app.get("/system/reload/{type}", SystemRoutes::reload);
        this.app.post("/camera/purchase", PhotoRoutes::purchase);
        this.app.get("/camera/purchase", PhotoRoutes::purchase);
    }

    private void authenticate(final Context context) {
        final String providedToken = ApiRequestUtils.firstNonBlank(
                context.header(this.authHeader),
                context.header("authToken")
        );

        if (!this.authToken.equals(providedToken)) {
            LOGGER.error("Unauthenticated request from: {} ; {}", context.ip(), context.path());
            throw new ApiUnauthorizedException();
        }
    }

    private void validateAuthenticationConfiguration() {
        if (StringUtils.isBlank(this.authHeader)) {
            throw new IllegalStateException("Management API auth header must be configured");
        }

        if (StringUtils.isBlank(this.authToken)
                || this.authToken.contains("replace_with_output_of_openssl_rand_hex_32")
                || this.authToken.length() < 32) {
            throw new IllegalStateException("Management API token must be configured with a secure random value, for example `openssl rand -hex 32`");
        }
    }

    private void openApiSpec(final Context context) {
        try (InputStream inputStream = APIManager.class.getResourceAsStream("/openapi/management-api.yaml")) {
            if (inputStream == null) {
                ApiResponseUtils.error(context, 404, "openapi_spec_not_found", "OpenAPI spec resource is not available.");
                return;
            }

            context.contentType("application/yaml");
            context.result(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8));
        } catch (Exception exception) {
            ApiResponseUtils.error(context, 500, "openapi_spec_error", "Unable to read the OpenAPI specification.");
        }
    }

    private static final class ApiUnauthorizedException extends RuntimeException {
        private ApiUnauthorizedException() {
            super("Invalid authentication token");
        }
    }
}
