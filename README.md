# pixel-comet

A high-performance, production-grade Habbo retro game server written in Java, targeting **1:1 compliance with the [Pixel Protocol](https://momlesstomato.github.io/pixel-protocol/)**.

pixel-comet accepts connections over both raw TCP and WebSocket, making it compatible with modern browser-based clients (Nitro) and native clients that speak the Pixel binary protocol.

---

## Goals

- **Protocol fidelity.** Every incoming and outgoing message must match the Pixel Protocol specification byte-for-byte.
- **Production stability.** The server is designed for long-running, high-concurrency deployments. Correctness and resilience are prioritised over feature volume.
- **Extensibility.** The plugin and SDK surface (`api/`) is a first-class concern. Third-party modules must be able to add commands, composers, and game mechanics without touching core internals.
- **Maintainability.** All code follows the standards defined in [AGENTS.md](AGENTS.md): SOLID design, complete Javadoc, zero compiler warnings.

---

## Architecture Overview

```
pixel-comet
├── api/                        Public SDK surface and storage/network contracts
├── protocol/                   Netty codec pipeline, encryption, opcode headers, composers
├── server/                     Core emulator runtime
├── plugins/
│   ├── example/                Reference plugin
│   ├── groups/                 Guild/group module
│   ├── items/                  Inventory item types used by the emulator
│   └── rooms/                  Room data/model services module
├── gamecenter/
│   └── fastfood/               FastFood mini-game module
├── modules/                    Built plugin jars loaded at runtime
└── tools/                      Standalone utilities moved out of the main build graph
```

**Networking stack:**
- Netty 4.1 handles the raw `TCP` hotel transport.
- Javalin handles the `WEBSOCKETS` listeners, including the `/game` hotel transport endpoint and the `/ws` browser side-channel.
- The codec pipeline applies XML-policy decoding (legacy compatibility), length-prefix framing, RC4 encryption, and short-header message dispatch.
- The REST management API runs on Javalin; when `COMET_API_DOCS_ENABLED=true`, it also exposes `/openapi/spec` and `/swagger`.

**Storage:**
- MySQL via HikariCP connection pool.
- All persistence goes through the repository interfaces in `api/`; the current MySQL implementation lives in `server/`.
- Redis (Jedis) is available for distributed caching when `comet.cache.enabled=true`.

---

## Protocol Compliance

All packet opcodes are defined in:
- `protocol/.../protocol/headers/Events.java` — incoming client messages (305+ opcodes)
- `protocol/.../protocol/headers/Composers.java` — outgoing server messages (300+ opcodes)

The target specification is: **https://momlesstomato.github.io/pixel-protocol/**

Any deviation from the spec must be documented with an `@implNote` in the relevant Javadoc and tracked as an open issue.

---

## Requirements

| Dependency | Version |
|------------|---------|
| Java       | 17+     |
| Gradle     | 9+      |
| MySQL      | 5.7+ / 8.x |
| Redis      | 6+ (optional, for distributed cache) |

---

## Building

```bash
./gradlew build
```

The emulator distribution is produced under `server/build/`, and runtime-loaded module jars are written to `modules/`.

---

## Configuration

Create a local `.env` from `.env.example` and adjust the values for your environment:

```dotenv
# Database
COMET_DB_HOST=127.0.0.1
COMET_DB_NAME=pixel_comet
COMET_DB_USERNAME=root
COMET_DB_PASSWORD=changeme

# Network
COMET_NETWORK_HOST=0.0.0.0
COMET_NETWORK_PORT=2096
COMET_TRANSPORT_TCP_ENABLED=true
COMET_TRANSPORT_WEBSOCKETS_ENABLED=true
COMET_TRANSPORT_WEBSOCKETS_PORT=87

# REST API (disabled by default)
COMET_API_ENABLED=false
COMET_API_TOKEN=replace_with_output_of_openssl_rand_hex_32
COMET_API_TOKEN_HEADER=auth_token

# Connection registry
COMET_CONNECTION_REGISTRY_IMPLEMENTATION=inmemory
COMET_CONNECTION_REGISTRY_TTLSECONDS=3600
```

Pluggable modules are discovered automatically from the modules directory:

```text
modules/
  01-groups.jar
  02-rooms.jar
```

Each module JAR must contain a root-level `module.json`. Module-specific environment
variables use the `MODULE_<MODULE_NAME>_<KEY>` namespace. Bundled modules also accept
the shortened form without a leading `COMET_` segment, such as
`MODULE_GROUPS_CACHE_TTL_SECONDS`.

---

## Running

```bash
./gradlew :server:shadowJar
java -jar server/build/libs/server.jar
```

The server binds on the configured TCP port and on one shared Javalin port for `/game`, `/ws`, and the management API routes. The REST API starts only when `COMET_API_ENABLED=true`.

---

## Database Setup

Import the base schema before first run:

```bash
mysql -u root -p pixel_comet < SQL.sql
```

---

## Writing a Plugin Module

1. Add `api/` as a `compileOnly` Gradle dependency.
2. Extend `com.cometproject.api.modules.BaseModule`.
3. Register commands by implementing `com.cometproject.api.commands.ModuleChatCommand`.
4. Subscribe to game events via the event system in `com.cometproject.api.events`.
5. Package the module as a JAR, include a root `module.json`, and drop it into `modules/`.

All module code must follow the standards in [AGENTS.md](AGENTS.md).

---

## REST Management API

When enabled, the API listens on `COMET_TRANSPORT_WEBSOCKETS_PORT` and requires the header named by `COMET_API_TOKEN_HEADER`.

When `COMET_API_DOCS_ENABLED=true`, the API also exposes `/openapi/spec` and `/swagger` without authentication so Swagger UI can load the specification.

The default header is `auth_token`, and the token value should be generated with `openssl rand -hex 32` and stored in `COMET_API_TOKEN`.

| Method | Path | Description |
|--------|------|-------------|
| `GET`  | `/system/status` | Server metrics and online count |
| `GET`  | `/system/reload/:type` | Reload rooms, items, permissions, etc. |
| `GET`  | `/system/shutdown` | Graceful shutdown |
| `GET`  | `/openapi/spec` | OpenAPI YAML document, when `COMET_API_DOCS_ENABLED=true` |
| `GET`  | `/swagger` | Swagger UI, when `COMET_API_DOCS_ENABLED=true` |
| `GET`  | `/player/:id/reload` | Reload player data from DB |
| `GET`  | `/player/:id/disconnect` | Force-disconnect a player |
| `POST` | `/player/:id/alert` | Send an alert to a player |
| `GET`  | `/player/:id/badge/:badge` | Award a badge to a player |
| `GET`  | `/rooms/active/all` | List all active room instances |
| `GET`  | `/room/:id/:action` | Room control (kick, empty, …) |

The browser websocket side-channel is hosted at `/ws` on `COMET_TRANSPORT_WEBSOCKETS_PORT`. The hotel game websocket remains at `/game`, and the management HTTP routes share that same Javalin listener.

---

## Contributing

See [AGENTS.md](AGENTS.md) for the full contribution standards. The short version:

- Follow SOLID design principles.
- Write complete Javadoc on every public type and member.
- Compile with zero warnings.
- Match packet definitions to the Pixel Protocol spec.
- Expose new functionality through `Comet-API` interfaces, not core internals.

Open a pull request against `main`. Include a description of what changed and which protocol messages are affected.

---

## License

See [LICENSE.txt](LICENSE.txt).
