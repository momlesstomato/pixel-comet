package com.cometproject.server.api;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cometproject.api.config.Configuration;
import com.cometproject.api.config.api.ApiConfiguration;
import com.cometproject.api.utilities.Startable;
import com.cometproject.server.api.routes.SsoTicketRoutes;
import com.cometproject.server.api.routes.CurrencyRoutes;
import com.cometproject.server.api.routes.PhotoRoutes;
import com.cometproject.server.api.routes.PlayerRoutes;
import com.cometproject.server.api.routes.RoomRoutes;
import com.cometproject.server.api.routes.StatusRoutes;
import com.cometproject.server.api.routes.SystemRoutes;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.game.sso.exceptions.SsoBackendUnavailableException;

import io.javalin.Javalin;
import io.javalin.http.Context;


/**
 * Manages API runtime state for the HTTP API subsystem.
 */
public class APIManager implements Startable {
    private static final String OPENAPI_SPEC_PATH = "/openapi/spec";
    private static final String SWAGGER_PATH = "/swagger";
    private static final String STATUS_PATH = "/status";
    private static final Set<String> UNAUTHENTICATED_PATHS = Set.of(OPENAPI_SPEC_PATH, SWAGGER_PATH, STATUS_PATH);
    private static final List<String> AUTHENTICATED_PATH_PREFIXES = List.of(
        "/currencies",
        "/player/",
        "/room/",
        "/system/"
    );
    private static final Set<String> AUTHENTICATED_PATHS = Set.of(
        "/rooms/active/all",
        "/camera/purchase",
        "/sso/ticket"
    );

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(APIManager.class.getName());
    /**
     * Create an array of config properties that are required before enabling the API.
     * If none of these properties exist, the API will be automatically disabled
     */
        private static final String[] configProperties = new String[]{
            ApiConfiguration.ENABLED,
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
     * The token used for authentication.
     */
    private String authToken;
    private String authHeader;
    private boolean documentationEnabled;

    /**
     * Construct the API manager.
     */
    public APIManager() {

    }

    /**
     * Returns the instance for this HTTP API contract.
     *
     * @return Value exposed by the contract.
     */
    public static APIManager getInstance() {
        return CometBootstrap.resolve(APIManager.class);
    }

    /**
     * Initialize the API.
     */
    @Override
    public void start() {
        this.initializeConfiguration();

        if (!this.enabled) {
            return;
        }

        this.validateAuthenticationConfiguration();
    }

    /**
     * Stops this HTTP API component.
     */
    @Override
    public void stop() {
    }

    /**
     * Initialize the configuration.
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
        this.authToken = Configuration.currentConfig().getProperty(ApiConfiguration.TOKEN);
        this.authHeader = Configuration.currentConfig().getProperty(ApiConfiguration.TOKEN_HEADER);
        this.documentationEnabled = Boolean.parseBoolean(Configuration.currentConfig().getProperty(
            ApiConfiguration.DOCS_ENABLED,
            ApiConfiguration.defaults().get(ApiConfiguration.DOCS_ENABLED)
        ));
    }

    /**
     * Indicates whether the management API is enabled.
     *
     * @return True when the management API should be registered.
     */
    public boolean isEnabled() {
        return this.enabled;
    }

    /**
     * Registers the management API routes on the shared Javalin server.
     *
     * @param app The shared Javalin application.
     */
    public void configureRoutes(final Javalin app) {
        if (!this.enabled) {
            return;
        }

        app.before(context -> {
            if (this.requiresAuthentication(context.path())) {
                this.authenticate(context);
            }
        });
        app.exception(ApiUnauthorizedException.class, (exception, context) -> {
            context.header("www-authenticate", this.authHeader);
            ApiResponseUtils.error(context, 401, "invalid_auth_token", "Invalid authentication token.");
        });
        app.exception(SsoBackendUnavailableException.class, (exception, context) ->
            ApiResponseUtils.error(context, 503, "sso_backend_unavailable", "SSO backend is unavailable."));

        app.get("/", context -> ApiResponseUtils.error(context, 404, "not_found", "Invalid request."));
        app.get(STATUS_PATH, StatusRoutes::status);

        if (this.documentationEnabled) {
            app.get(OPENAPI_SPEC_PATH, this::openApiSpec);
            app.get(SWAGGER_PATH, this::swaggerUi);
        }

        app.get("/player/{id}/reload", PlayerRoutes::reloadPlayerData);
        app.get("/player/{id}/disconnect", PlayerRoutes::disconnect);
        app.post("/player/{id}/alert", PlayerRoutes::alert);
        app.get("/player/{id}/badge/{badge}", PlayerRoutes::giveBadge);
        app.get("/player/{id}/currencies", CurrencyRoutes::playerBalances);
        app.post("/player/{id}/currencies/{code}/add", CurrencyRoutes::addBalance);
        app.post("/player/{id}/currencies/{code}/remove", CurrencyRoutes::removeBalance);
        app.put("/player/{id}/currencies/{code}", CurrencyRoutes::setBalance);
        app.get("/player/{id}/currency-movements", CurrencyRoutes::movements);

        app.get("/currencies", CurrencyRoutes::listCurrencies);
        app.post("/currencies", CurrencyRoutes::upsertCurrency);
        app.patch("/currencies/{code}", CurrencyRoutes::upsertCurrency);
        app.delete("/currencies/{code}", CurrencyRoutes::disableCurrency);
        app.get("/currencies/{code}/roles", CurrencyRoutes::listRoleRules);
        app.put("/currencies/{code}/roles/{rank_id}", CurrencyRoutes::upsertRoleRule);
        app.delete("/currencies/{code}/roles/{rank_id}", CurrencyRoutes::deleteRoleRule);
        app.get("/currencies/{code}/aliases", CurrencyRoutes::listAliases);
        app.post("/currencies/{code}/aliases", CurrencyRoutes::upsertAlias);
        app.delete("/currencies/{code}/aliases/{alias}", CurrencyRoutes::deleteAlias);

        app.get("/rooms/active/all", RoomRoutes::getAllActiveRooms);
        app.get("/room/{id}/{action}", RoomRoutes::roomAction);

        app.get("/system/status", SystemRoutes::status);
        app.get("/system/shutdown", SystemRoutes::shutdown);
        app.get("/system/reload/{type}", SystemRoutes::reload);
        app.post("/sso/ticket", SsoTicketRoutes::issueTicket);
        app.post("/camera/purchase", PhotoRoutes::purchase);
        app.get("/camera/purchase", PhotoRoutes::purchase);
    }

    private void authenticate(final Context context) {
        final String providedToken = context.header(this.authHeader);

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

    private boolean isUnauthenticatedRoute(final String path) {
        return UNAUTHENTICATED_PATHS.contains(path);
    }

    private boolean requiresAuthentication(final String path) {
        if (this.isUnauthenticatedRoute(path)) {
            return false;
        }

        if (AUTHENTICATED_PATHS.contains(path)) {
            return true;
        }

        for (String pathPrefix : AUTHENTICATED_PATH_PREFIXES) {
            if (path.startsWith(pathPrefix)) {
                return true;
            }
        }

        return false;
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

    private void swaggerUi(final Context context) {
        context.contentType("text/html; charset=utf-8");
        context.result("""
                <!DOCTYPE html>
                <html lang=\"en\">
                <head>
                    <meta charset=\"utf-8\">
                    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">
                    <title>Pixel Comet Management API</title>
                    <link rel=\"stylesheet\" href=\"https://unpkg.com/swagger-ui-dist@5/swagger-ui.css\">
                    <style>
                        body { margin: 0; background: #fafafa; }
                    </style>
                </head>
                <body>
                    <div id=\"swagger-ui\"></div>
                    <script src=\"https://unpkg.com/swagger-ui-dist@5/swagger-ui-bundle.js\"></script>
                    <script>
                        window.ui = SwaggerUIBundle({
                            url: '%s',
                            dom_id: '#swagger-ui',
                            deepLinking: true,
                            persistAuthorization: true,
                            presets: [SwaggerUIBundle.presets.apis],
                            layout: 'BaseLayout'
                        });
                    </script>
                </body>
                </html>
                """.formatted(OPENAPI_SPEC_PATH));
    }

    private static final class ApiUnauthorizedException extends RuntimeException {
        private ApiUnauthorizedException() {
            super("Invalid authentication token");
        }
    }
}
