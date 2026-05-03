# pixel-comet

A high-performance, production-grade Habbo retro game server written in Java, targeting **1:1 compliance with the [Pixel Protocol](https://momlesstomato.github.io/pixel-protocol/)**.

pixel-comet accepts connections over both raw TCP and WebSocket, making it compatible with modern browser-based clients (Nitro) and native clients that speak the Pixel binary protocol.

---

## Goals

- **Protocol fidelity.** Every incoming and outgoing message must match the Pixel Protocol specification byte-for-byte.
- **Production stability.** The server is designed for long-running, high-concurrency deployments. Correctness and resilience are prioritised over feature volume.
- **Extensibility.** The plugin and SDK surface (`Comet-API`, `Comet-Networking-API`) is a first-class concern. Third-party modules must be able to add commands, composers, and game mechanics without touching core internals.
- **Maintainability.** All code follows the standards defined in [AGENTS.md](AGENTS.md): SOLID design, complete Javadoc, zero compiler warnings.

---

## Architecture Overview

```
pixel-comet
├── Comet-Launcher              Entry point and process lifecycle
├── Comet-Server                Core game server (rooms, players, commands, permissions, catalog, navigator)
├── Comet-Server-Protocol       Netty codec pipeline: framing, encryption (RSA/DH/RC4), message routing
├── Comet-Networking-API        Networking abstractions (IMessageEvent, IMessageComposer)
├── Comet-Networking-Composers  600+ outgoing message composers
├── Comet-API                   Public SDK surface: BaseModule, events, command interfaces
├── Comet-API-Example           Reference implementation of a third-party module
├── Comet-Common                Shared utilities (no game or network dependencies)
├── Comet-Storage-API           Repository interfaces (no SQL dependency)
├── Comet-Storage-MySQL         MySQL + HikariCP implementation of storage interfaces
│
├── Comet-Game-Groups           Pluggable guild/group module
├── Comet-Game-Items            Pluggable item definition module
├── Comet-Game-Rooms            Pluggable extended room logic module
├── Comet-Game-Catalog          Pluggable catalog module
├── Comet-Game-Achievements     Pluggable achievement module
├── Comet-GameCenter-FastFood   FastFood mini-game module
└── Comet-GameCenter-SnowStorm  SnowStorm mini-game module
```

**Networking stack:**
- Netty 4.1 handles all I/O (TCP + WebSocket).
- The codec pipeline applies XML-policy decoding (legacy compatibility), length-prefix framing, RC4 encryption, and short-header message dispatch.
- The REST management API runs on Spark/Jetty (port `30003` by default, disabled by default).

**Storage:**
- MySQL via HikariCP connection pool.
- All persistence goes through the repository interfaces in `Comet-Storage-API`; the MySQL implementation is swappable.
- Redis (Jedis) is available for distributed caching when `comet.cache.enabled=true`.

---

## Protocol Compliance

All packet opcodes are defined in:
- `Comet-Server-Protocol/.../protocol/headers/Events.java` — incoming client messages (305+ opcodes)
- `Comet-Server-Protocol/.../protocol/headers/Composers.java` — outgoing server messages (300+ opcodes)

The target specification is: **https://momlesstomato.github.io/pixel-protocol/**

Any deviation from the spec must be documented with an `@implNote` in the relevant Javadoc and tracked as an open issue.

---

## Requirements

| Dependency | Version |
|------------|---------|
| Java       | 8+      |
| Maven      | 3.6+    |
| MySQL      | 5.7+ / 8.x |
| Redis      | 6+ (optional, for distributed cache) |

---

## Building

```bash
mvn clean package -DskipTests
```

The shaded executable JAR is produced at `Comet-Launcher/target/Comet-Launcher-*.jar`.

---

## Configuration

Copy `config/comet.properties` and adjust:

```properties
# Database
comet.db.host=127.0.0.1
comet.db.name=pixel_comet
comet.db.username=root
comet.db.password=changeme

# Network
comet.network.host=0.0.0.0
comet.network.port=2096          # TCP game port
comet.websockets.enable=true
comet.websockets.port=87         # WebSocket game port

# REST API (disabled by default)
comet.api.enabled=false
comet.api.port=30003
comet.api.token=changeme
```

Pluggable modules are declared in `config/modules.json`:

```json
{
  "modules": [
    {
      "path": "modules/Comet-Game-Groups.jar",
      "alias": "Comet.Game.Groups",
      "config": {}
    }
  ]
}
```

---

## Running

```bash
java -jar Comet-Launcher/target/Comet-Launcher-*.jar
```

The server binds on the configured TCP and WebSocket ports. The REST API starts only when `comet.api.enabled=true`.

---

## Database Setup

Import the base schema before first run:

```bash
mysql -u root -p pixel_comet < SQL.sql
```

---

## Writing a Plugin Module

1. Add `Comet-API` as a `provided` Maven dependency.
2. Extend `com.cometproject.api.modules.BaseModule`.
3. Register commands by implementing `com.cometproject.api.commands.ModuleChatCommand`.
4. Subscribe to game events via the event system in `com.cometproject.api.events`.
5. Package as a JAR and declare it in `config/modules.json`.

All module code must follow the standards in [AGENTS.md](AGENTS.md).

---

## REST Management API

When enabled, the API listens on `comet.api.port` and requires the `authToken` header set to `comet.api.token`.

| Method | Path | Description |
|--------|------|-------------|
| `GET`  | `/system/status` | Server metrics and online count |
| `GET`  | `/system/reload/:type` | Reload rooms, items, permissions, etc. |
| `GET`  | `/system/shutdown` | Graceful shutdown |
| `GET`  | `/player/:id/reload` | Reload player data from DB |
| `GET`  | `/player/:id/disconnect` | Force-disconnect a player |
| `POST` | `/player/:id/alert` | Send an alert to a player |
| `GET`  | `/player/:id/badge/:badge` | Award a badge to a player |
| `GET`  | `/rooms/active/all` | List all active room instances |
| `GET`  | `/room/:id/:action` | Room control (kick, empty, …) |

---

## Contributing

See [AGENTS.md](AGENTS.md) for the full contribution standards. The short version:

- Follow SOLID design principles.
- Write complete Javadoc on every public type and member.
- Compile with zero warnings (`mvn clean package -Werror`).
- Match packet definitions to the Pixel Protocol spec.
- Expose new functionality through `Comet-API` interfaces, not core internals.

Open a pull request against `main`. Include a description of what changed and which protocol messages are affected.

---

## License

See [LICENSE.txt](LICENSE.txt).
