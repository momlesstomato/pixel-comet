# Codebase Analysis — pixel-comet

> **Purpose:** This document provides a full technical inventory of the pixel-comet codebase as of the start of the Pixel Protocol alignment project. It answers: what is here, what protocol(s) does it speak, what is production-ready, what is incomplete or legacy, and what needs attention before claiming 1:1 Pixel Protocol compliance.

---

## Table of Contents

1. [Project Identity](#1-project-identity)
2. [Technology Stack](#2-technology-stack)
3. [Module Map](#3-module-map)
4. [Networking Layer](#4-networking-layer)
5. [Protocol Analysis](#5-protocol-analysis)
6. [Game Subsystems](#6-game-subsystems)
7. [Command System](#7-command-system)
8. [Permission System](#8-permission-system)
9. [REST API](#9-rest-api)
10. [Storage Layer](#10-storage-layer)
11. [Plugin & SDK Surface](#11-plugin--sdk-surface)
12. [Configuration System](#12-configuration-system)
13. [Known Issues & Technical Debt](#13-known-issues--technical-debt)
14. [Pixel Protocol Compliance Gap](#14-pixel-protocol-compliance-gap)
15. [Recommended Next Steps](#15-recommended-next-steps)

---

## 1. Project Identity

| Field | Value |
|-------|-------|
| **Upstream origin** | Comet by Dank074 |
| **Fork name** | KeyServers Edition (internal name at time of fork) |
| **Declared version** | 2.9.8-TEST1 |
| **Language / runtime** | Java 8, Maven build |
| **Approximate scale** | 2,080 Java source files; 600+ message handlers and composers |
| **Client targets** | Nitro (browser WebSocket), legacy Habbo Air/Flash (TCP) |
| **Protocol target** | Pixel Protocol (binary, length-prefixed, short header) |

This codebase began as a community fork of an existing Habbo emulator and has been progressively adapted to support the Pixel Protocol and the Nitro client. A significant portion of the inherited code still carries Habbo Air/Flash-era assumptions (encryption handshake, XML policy response, opcode values) that must be audited against the Pixel Protocol spec.

---

## 2. Technology Stack

### Core

| Layer | Library | Version | Notes |
|-------|---------|---------|-------|
| Networking | Netty | 4.1.68.Final | All I/O, pipeline, channel management |
| WebSocket | Java-WebSocket | 1.3.8 | Secondary WebSocket server (currently partially disabled) |
| Socket.IO | netty-socketio | 1.6.5 | Bundled but usage is unclear — likely legacy |
| HTTP / REST API | Spark Java + Jetty | 2.0.0 / 9.0.2 | Management REST API |
| Database | MySQL connector | 5.1.34 | Main persistence |
| Connection pool | HikariCP | 2.3.7 | Primary pool |
| Connection pool | BoneCP | 0.8.0.RELEASE | Bundled but appears unused — dead weight |
| Cache | Redis / Jedis | 2.8.1 | Optional distributed cache |
| Cache | EhCache | 2.10.0 | In-process cache |
| Logging | Logback + Log4j2 | 1.2.3 / 2.13.2 | Two logging frameworks present — should be consolidated |
| JSON | Gson | 2.7 | Serialization for API responses and config |
| Collections | Guava | 16.0.1 | Utilities, caches |
| Collections | Trove4j | 3.0.3 | Primitive maps for performance-sensitive paths |
| DI | Dagger | 2.13 | Present but inconsistently used |
| IPC | Coerce-API / Coerce-Messaging | 1.0-SNAPSHOT | Inter-server messaging (hotel cluster support) |
| Discord | Javacord | 3.1.0 | Discord bot integration |
| Security | jBCrypt | 0.3m | Password hashing |

### Build

- Maven 4.0.0 parent POM with child modules.
- Java source/target set to 1.8.
- No CI pipeline is currently active (Jenkinsfile present but not configured).

---

## 3. Module Map

The repository is a multi-module Maven project. Each module's role and production status is assessed below.

### Infrastructure Modules

| Module | Role | Status |
|--------|------|--------|
| `Comet-Launcher` | Main entry point, JVM bootstrap | Functional |
| `Comet-Server` | Core game server (rooms, players, game logic) | Functional, needs audit |
| `Comet-Server-Protocol` | Netty codec, encryption, opcode headers | Functional, requires Pixel Protocol audit |
| `Comet-Networking-API` | Interfaces: `IMessageEvent`, `IMessageComposer` | Functional |
| `Comet-Networking-Composers` | 600+ outgoing message composers | Functional, opcode values must be verified |
| `Comet-API` | SDK surface: `BaseModule`, events, command interfaces | Functional, thin |
| `Comet-API-Example` | Reference plugin | Minimal, serves as template only |
| `Comet-Common` | Shared utilities with no game/network coupling | Functional |
| `Comet-Storage-API` | Repository interfaces | Functional |
| `Comet-Storage-MySQL` | MySQL implementation of storage interfaces | Functional |
| `Comet-Manager` | WAR-based management console | Unknown/legacy |
| `Comet-ProcessManager` | External process management | Unclear scope |
| `Comet-WebApp` | Web application | Unknown/legacy |
| `Comet-Website` | Static website assets | Not relevant to server |
| `Comet-CatalogTool` | Catalog population utility | Dev tool only |
| `Comet-StressTest` | Load/stress testing client | Dev tool only |

### Game Modules (Pluggable JARs)

| Module | Role | Loaded via |
|--------|------|-----------|
| `Comet-Game-Groups` | Guild/group system | `modules.json` |
| `Comet-Game-Items` | Item definitions | `modules.json` |
| `Comet-Game-Rooms` | Extended room logic | `modules.json` |
| `Comet-Game-Catalog` | Catalog and purchasing | `modules.json` |
| `Comet-Game-Achievements` | Achievement tracking | `modules.json` |
| `Comet-GameCenter-FastFood` | FastFood mini-game | `modules.json` |
| `Comet-GameCenter-SnowStorm` | SnowStorm mini-game | `modules.json` |

---

## 4. Networking Layer

### TCP Server

- **Framework:** Netty 4.1 with `NioEventLoopGroup`
- **Default port:** `2096`
- **Entry:** `com.cometproject.server.network.NetworkManager` → `NettyNetworkingServer`
- **Threads:** 2 accept threads, 2 I/O threads, 4 channel-group threads (all configurable)
- **Backlog:** 1500 connections

**Netty Pipeline (inbound order):**

```
Raw TCP bytes
  → XmlPolicyDecoder        (Flash socket-policy response, legacy)
  → MessageFrameDecoder     (reads 4-byte length prefix, extracts body)
  → EncryptionDecoder       (RC4 decryption, applied after handshake)
  → MessageDecoder          (reads 2-byte short header → dispatches to handler)
  → [Game handler threads]
```

**Netty Pipeline (outbound order):**

```
IMessageComposer
  → MessageEncoder          (writes 4-byte length + encoded body)
  → EncryptionEncoder       (RC4 encryption, applied after handshake)
  → Raw TCP bytes
```

### WebSocket Server

- **Library:** `org.java-websocket:Java-WebSocket:1.3.8`
- **Default port:** `87`
- **Status:** Partially implemented. A `WebSocketManager` class exists and the WebSocket codec frame handlers are wired into the protocol module, but the WebSocket server is currently disabled in the main startup flow and must be re-enabled explicitly.
- **Client target:** Nitro (browser-based JS client)

The WebSocket path shares the same binary message format as TCP after the WebSocket frame layer is stripped. This is consistent with how the Pixel Protocol is designed to work over both transports.

### Socket.IO

`netty-socketio` is a declared dependency but no active usage was found in the main server path. It is likely a leftover from an earlier prototype or a feature branch. It should be audited and removed if unused, as it adds unnecessary classpath weight and a potential attack surface.

### RCON / Remote Console

- **Port:** `7777` (TCP)
- Used for inter-process management commands. Separate from the REST API.

---

## 5. Protocol Analysis

### Wire Format

The Pixel Protocol uses a simple binary framing:

```
┌─────────────────────────────────────────────────────┐
│  4 bytes  │  2 bytes  │  N bytes                    │
│  Length   │  Header   │  Body (type-encoded fields) │
└─────────────────────────────────────────────────────┘
```

- **Length** is the total byte count of `Header + Body` (not including the length field itself).
- **Header** is an unsigned short identifying the message type.
- **Body** fields are encoded as: int (4 bytes, big-endian), short (2 bytes), bool (1 byte), string (2-byte length prefix + UTF-8 bytes).

This matches what `MessageFrameDecoder`, `MessageDecoder`, and `MessageEncoder` implement. The implementation is broadly correct at the framing level.

### Encryption

Three-phase encryption handshake inherited from Habbo Air:

| Phase | Mechanism | Classes |
|-------|-----------|---------|
| 1 | RSA (asymmetric) for key material | `HabboRSACrypto` |
| 2 | Diffie-Hellman key agreement | `HabboDiffieHellman` |
| 3 | RC4 stream cipher for session data | `HabboRC4`, `EncryptionDecoder`, `EncryptionEncoder` |

**Assessment:** The Pixel Protocol specification must be consulted to confirm whether this three-phase handshake is required, optional, or replaced by a different mechanism (e.g., TLS at the transport layer). If the Pixel Protocol operates over WSS (WebSocket Secure), the application-layer encryption may be unnecessary and should be made optional.

### Opcode Inventory

Opcodes are centralised in two files:

- **Incoming:** `Comet-Server-Protocol/.../protocol/headers/Events.java` — 305+ named constants
- **Outgoing:** `Comet-Server-Protocol/.../protocol/headers/Composers.java` — 300+ named constants

**Critical finding:** These opcode values were defined for a specific Habbo Air client version. The Pixel Protocol specification at `https://momlesstomato.github.io/pixel-protocol/` defines its own opcode table. A full diff between `Events.java` / `Composers.java` and the Pixel Protocol spec is required before any new handlers are written or existing ones are shipped to production.

### Legacy Compatibility Code

| Code | Location | Purpose | Risk |
|------|----------|---------|------|
| `XmlPolicyDecoder` | Protocol codec | Responds to Flash socket-policy XML request | Safe to keep for now; remove when Flash clients are no longer supported |
| Habbo RSA/DH/RC4 | `protocol.crypto` | Flash-era encryption handshake | May conflict with Pixel Protocol's own handshake requirements |
| `netty-socketio` | Dependency | Socket.IO | Likely unused — should be removed |
| BoneCP | Dependency | Connection pool | Superseded by HikariCP — should be removed |

---

## 6. Game Subsystems

### Rooms

**Location:** `Comet-Server/.../game/rooms/` (357 Java files)

The room system is the largest and most complex subsystem. Key components:

| Component | Class | Role |
|-----------|-------|------|
| Room entity | `Room.java` | Root game object; holds all room state |
| Tile grid | `RoomTile`, `RoomMapping` | 2D tile grid with height data and pathfinding support |
| Room cycle | `RoomCycle` | Frame-by-frame update loop; drives item states, entity movement |
| Item processing | `ItemProcessComponent` | Per-tick furniture state updates (rollers, triggers, wired) |
| Rights | `RightsComponent` | Room ownership and doorbell/access control |
| Bots | `RoomBotComponent` | NPC entities with scripted behaviour |
| Trading | `TradeComponent` | Peer-to-peer item trading between players |
| Word filter | `FilterComponent` | Chat word replacement |
| Mini-game state | `GameComponent` | SnowStorm / FastFood mini-game attachment |
| Promotions | `RoomPromotion` | Room advertisement system |
| Serialisation | `RoomWriter` | Room state serialisation for client packets |
| Global lifecycle | `RoomManager` | In-memory room registry, loading queue, instance cache |

**Status:** Functionally complete for the Habbo Air client feature set. Pixel Protocol compliance of individual room packets (room data, user objects, item lists) must be verified opcode-by-opcode.

### Players

**Location:** `Comet-Server/.../game/players/` (49 Java files)

| Component | Role |
|-----------|------|
| `Player.java` | Session-bound player entity; root of all per-player state |
| `InventoryBotComponent` | Bots owned by this player |
| `WardrobeComponent` | Saved avatar figure strings |
| `NavigatorComponent` | Favourite rooms, search history |
| `RelationshipComponent` | Friendship and relationship state |
| `RentableComponent` | Room rental/ownership |
| `PermissionComponent` | Cached rank and perks for this session |
| `SubscriptionComponent` | VIP / club membership status |
| `PlayerAvatarActions` | Emotes, gestures, hand item state |
| `PlayerStatistics` | Play time, achievement points |
| `PlayerSettings` | User preferences (sound, chat bubble type, etc.) |

**Status:** Functional. Avatar figure handling (figure strings) is Habbo-specific and may need adjustment if the Pixel Protocol defines a different avatar format.

### Catalog & Purchasing

**Location:** `Comet-Server/.../game/catalog/` (16 files) + `Comet-Game-Catalog`

| Class | Role |
|-------|------|
| `CatalogPage` | A catalog section/tab |
| `CatalogOffer` | A purchasable bundle |
| `CatalogItem` | A single item within an offer |
| `CatalogFrontPageEntry` | Homepage featured items |
| `Voucher` | One-time redemption codes |
| `PurchaseManager` | Orchestrates purchase validation and fulfilment |
| Specific handlers | `BotPurchaseHandler`, `PetPurchaseHandler`, `TrophyPurchaseHandler`, `StickiesPurchaseHandler` |

**Status:** Functional for the Habbo Air feature set. Purchase packet structure must be validated against the Pixel Protocol spec.

### Navigator

**Location:** `Comet-Server/.../game/navigator/` (11 files)

| Class | Role |
|-------|------|
| `NavigatorManager` | Central navigator state (categories, public rooms) |
| `NavigatorSearchService` | Full-text search across room names and descriptions |
| `FeaturedRoom`, `BannerType` | Promotion / advertising slots |
| `PublicRoom` | Predefined public spaces |

**Status:** Functional. Navigator search response format (categories, result sets) must be verified against the Pixel Protocol spec.

### Items

**Location:** `Comet-Server/.../game/items/` (7 files) + `Comet-Game-Items`

| Class | Role |
|-------|------|
| `ItemDefinition` | Item metadata (name, type, dimensions, interaction class) |
| `ItemManager` | Global item definition registry |
| `MusicData`, `SongItemData` | Jukebox / trax music system |
| `CraftingMachine`, `CraftingRecipe` | Recipe-based crafting |

**Status:** Functional. The item interaction type system (`InteractionType`) is Habbo-specific; confirm Pixel Protocol item type identifiers match.

### Achievements

**Location:** `Comet-Server/.../game/achievements/` (9 files) + `Comet-Game-Achievements`

The achievement system tracks 939 predefined achievements across categories: Identity, Explore, Music, Social, Games, Room Builder, Tools, Commercial, Survival, Pets. Each achievement has up to 20 progression levels with point rewards.

**Status:** Functional. Achievement packet format must be verified against the Pixel Protocol spec.

### Pets

**Location:** `Comet-Server/.../game/pets/` (22 files)

Full pet lifecycle: ownership, breeding, level progression, interactions, care, and inventory. 

**Status:** Functional.

### Quests / Missions

**Location:** `Comet-Server/.../game/quests/` (3 files)

Daily and seasonal quests with reward tracking.

**Status:** Minimal implementation. Not a Pixel Protocol core feature — lower priority.

### Mini-Games

| Game | Module | Transport |
|------|--------|-----------|
| SnowStorm | `Comet-GameCenter-SnowStorm` | TCP (standard pipeline) |
| FastFood | `Comet-GameCenter-FastFood` | TCP (standard pipeline) |
| Battle Royale | Inline WebSocket handlers | WebSocket (custom packet types) |
| Duel | Inline WebSocket handlers | WebSocket |

**Note:** The Battle Royale and Duel systems use WebSocket-specific packet handlers that bypass the standard TCP codec pipeline. This creates two parallel handler paths and is a source of complexity. These must be audited if WebSocket support is to be made production-grade.

### Other Systems

| System | Location | Status |
|--------|----------|--------|
| Polls / Surveys | `game/polls` | Minimal |
| Guides (help system) | `game/guides` | Minimal |
| NUX (new user experience) | `game/nuxs` | Minimal |
| Battle Pass | Inline (from fork) | Present, untested |
| Discord integration | `Javacord` dependency | Present, scope unclear |

---

## 7. Command System

**Location:** `Comet-Server/.../game/commands/`

**Manager:** `CommandManager` — singleton registry + dispatcher.

**Architecture:**
- Commands are registered by string alias (e.g., `"kick"`, `"ban"`).
- Before dispatch, `PermissionsManager` is consulted for the required permission rank.
- Async commands run on a fixed 2-thread executor pool.
- Commands can declare `isLoggable()` to opt into the `LogManager` audit trail.

**Command categories:**

| Category | Location | Examples |
|----------|----------|---------|
| User | `commands/user/` | home, sit, lay, pickall, empty, position, colors |
| VIP | `commands/vip/` | transform, mimic, moonwalk, fastwalk, pull, push, follow |
| Staff | `commands/staff/` | mute, unmute, ban, kick, alert, reward, reload, snowing |
| Groups | `commands/user/group/` | deletegroup, assignrole, ejectall |
| Development | `commands/development/` | pos, roomgrid, instancestats, itemdata |
| Gimmicks | `commands/gimmicks/` | rps, dice, select_window, screenshot |

**Assessment:** The command framework is clean and extensible via the `ChatCommand` abstract base. The 2-thread async pool is a bottleneck for large hotels — consider making the pool size configurable.

---

## 8. Permission System

**Location:** `Comet-Server/.../game/permissions/` (8 files)

**Manager:** `PermissionsManager` — loads all permission data from the database at startup and caches it in memory.

**Permission types:**

| Type | Class | Description |
|------|-------|-------------|
| Rank | `Rank` | Named role (Member, Moderator, Admin, Host, Owner) |
| Perk | `Perk` | Special capability (e.g., VIP effects, bypass limits) |
| Command permission | `CommandPermission` | Which ranks may use which commands |
| Override permission | `OverrideCommandPermission` | Per-user command access exceptions |
| Effect permission | `EffectPermission` | Avatar effect restrictions by rank |

**Data flow:**
```
Database → PermissionsManager (startup load)
         → Player.PermissionComponent (per-session copy on login)
         → CommandManager.checkPermission() (pre-dispatch check)
```

**Assessment:** Solid design with separation between the global registry and the per-player cached component. The database-driven approach means permission changes require a server reload or explicit reload API call.

---

## 9. REST API

**Location:** `Comet-Server/.../api/` 

**Framework:** Spark Java 2.0.0 on Jetty 9.0.2  
**Default port:** `30003`  
**Default state:** **Disabled** (`comet.api.enabled=false`)  
**Authentication:** `authToken` request header checked before all routes

**Available routes:**

| Method | Path | Description |
|--------|------|-------------|
| `GET`  | `/system/status` | JSON server health metrics |
| `GET`  | `/system/shutdown` | Graceful server shutdown |
| `GET`  | `/system/reload/:type` | Hot-reload: rooms, items, permissions, etc. |
| `GET`  | `/player/:id/reload` | Force-reload player data from database |
| `GET`  | `/player/:id/disconnect` | Force-disconnect a player session |
| `POST` | `/player/:id/alert` | Send an in-game alert to a player |
| `GET`  | `/player/:id/badge/:badge` | Award a badge to a player |
| `GET`  | `/rooms/active/all` | JSON list of all active room instances |
| `GET`  | `/room/:id/:action` | Room control actions |
| `GET/POST` | `/camera/purchase` | Camera / photo purchase flow |

**Assessment:** The API provides useful operational hooks but its security model is a simple shared token with no rate limiting, IP allowlisting, or TLS enforcement. For production deployments this API should only be accessible from localhost or a private network.

---

## 10. Storage Layer

**Abstraction:** `Comet-Storage-API` defines repository interfaces with no SQL dependency.  
**Implementation:** `Comet-Storage-MySQL` provides MySQL implementations via HikariCP.

**Repository list:**

| Interface | Implementation |
|-----------|---------------|
| `IRoomRepository` | `MySQLRoomRepository` |
| `IRoomItemRepository` | `MySQLRoomItemRepository` |
| `IGroupRepository` | `MySQLGroupRepository` |
| `IGroupMemberRepository` | `MySQLGroupMemberRepository` |
| `IGroupForumRepository` | `MySQLGroupForumRepository` |
| `IInventoryRepository` | `MySQLInventoryRepository` |
| `IPhotoRepository` | `MySQLPhotoRepository` |
| `IRewardRepository` | `MySQLRewardRepository` |

**Queue system:**
- `MySQLStorageQueue` — batches writes asynchronously to reduce per-operation latency.
- `BlockingMySQLStorageQueue` — synchronous fallback for operations that must complete before proceeding.

**Assessment:** The interface/implementation split is correct and enables future database backends. HikariCP pool max (`comet.db.pool.max=100`) should be tuned to the deployment environment. BoneCP is declared as a dependency but appears unused — it should be removed.

---

## 11. Plugin & SDK Surface

**Entry point:** `Comet-API`  
**Example:** `Comet-API-Example`

**Available extension points:**

| Extension point | Interface / Class | Purpose |
|-----------------|-------------------|---------|
| Module lifecycle | `BaseModule` | `onEnable()`, `onDisable()` hooks for a plugin JAR |
| Chat commands | `ModuleChatCommand` | Register new `/commands` from a plugin |
| Game events | `EventHandler<T>` | Subscribe to `OnPlayerLoginEvent` and others |
| Composers | `IMessageComposer` | Write custom outgoing messages |
| Event handlers | `IMessageEvent` | Handle custom incoming opcodes |

**Module loading:**
- Modules are declared in `config/modules.json` with a JAR path and alias.
- `ModuleManager` loads each JAR via a custom classloader and calls `onEnable()`.
- Modules may declare configuration blocks in `modules.json` which are passed at load time.

**Assessment:** The plugin surface exists and is functional at a basic level. Key limitations:

1. The event catalogue (`OnPlayerLoginEvent` and a small set of others) is too sparse to support rich plugins. Most game events are not yet published to the event bus.
2. Modules cannot currently register custom storage repositories — there is no hook for extending the storage layer.
3. The API JAR version is `1.0-SNAPSHOT`, signalling it is not yet stabilised or versioned for external consumers.
4. No documentation exists for plugin authors beyond the example module.

---

## 12. Configuration System

| File | Purpose |
|------|---------|
| `config/comet.properties` | Main server settings (database, network, game, API, logging) |
| `config/modules.json` | Plugin module declarations |
| `config/cache.json` | EhCache region configuration |
| `config/locale.properties` | Localisation strings and command names |
| `config/ehcache.xml` | EhCache XML configuration |
| `config/figuredata.xml` | Avatar figure part definitions |
| `configuration/Coerce.json` | Inter-server messaging (Coerce IPC) settings |
| `configuration/MessagingServer.json` | Coerce messaging server address |
| `configuration/log4j2.xml` | Log4j2 appender and level configuration |

**Key configurable values:**

```
# Threading
comet.system.threads=16           Core thread pool
comet.system.taskRoomThreads=8    Room update threads
comet.network.acceptGroupThreads=2
comet.network.ioGroupThreads=2
comet.network.channelGroupThreads=4

# Room limits
comet.game.rooms.data.max=5000    Max cached room records

# Idle timeout (currently disabled by default)
comet.network.idleTimer.enabled=false
comet.network.idleTimer.readerIdleTime=60
```

**Assessment:** The configuration system is flat properties-based and functional. There is no validation of configuration values at startup — an invalid or missing key can cause a silent NullPointerException deep in initialisation. Configuration validation and schema documentation should be added.

---

## 13. Known Issues & Technical Debt

| Issue | Severity | Notes |
|-------|----------|-------|
| Two logging frameworks | Low | Both Logback and Log4j2 are on the classpath. One should be chosen and the other removed. |
| BoneCP unused dependency | Low | Dead code on classpath. Remove from pom.xml. |
| Socket.IO unused dependency | Medium | `netty-socketio` appears unused. Adds classpath weight and attack surface. Remove or document. |
| WebSocket server disabled | High | The WebSocket path is critical for Nitro clients but is currently not started in the main boot sequence. Must be re-enabled and tested end-to-end. |
| Opcode values not validated against Pixel Protocol | Critical | `Events.java` and `Composers.java` were written for a specific Habbo Air client version, not the Pixel Protocol spec. A full opcode audit is required. |
| Groups module has known bugs | High | Original fork documentation explicitly notes group-related bugs. The groups module must be audited before production use. |
| Async command thread pool is fixed at size 2 | Medium | Not configurable. Should be exposed in `comet.properties`. |
| REST API has no rate limiting or IP allowlist | Medium | Token auth alone is insufficient for production exposure. |
| Configuration not validated at startup | Medium | Silent NPEs possible on missing keys. |
| `Coerce` IPC snapshots not versioned | Low | `1.0-SNAPSHOT` dependencies are not stable. |
| No active CI pipeline | Medium | Jenkinsfile present but unconfigured. |
| Dagger DI present but inconsistently applied | Low | Some classes use DI, others use static singletons. Should be made consistent. |
| Flash XML policy decoder still active | Low | Legacy code. Harmless but adds pipeline complexity. Should be behind a feature flag. |

---

## 14. Pixel Protocol Compliance Gap

The following areas require explicit verification against `https://momlesstomato.github.io/pixel-protocol/` before the server can claim 1:1 compliance:

| Area | What to verify |
|------|---------------|
| **Opcode table** | All 305 incoming and 300 outgoing opcode values in `Events.java` and `Composers.java` |
| **Handshake sequence** | Whether the RSA/DH/RC4 handshake matches the Pixel Protocol login flow, or whether TLS replaces it |
| **Room data packet** | Field order, field types, and presence of all required fields in the room data composer |
| **User object packet** | Avatar figure format, position encoding, status flags |
| **Inventory packet** | Furniture list encoding, item type identifiers |
| **Navigator result set** | Category structure, room entry format, search filter semantics |
| **Catalog** | Page structure, offer format, purchase request/response |
| **Chat packets** | Say/shout/whisper encoding, emotion codes, bubble types |
| **Avatar figure format** | Whether the Habbo figure string format matches the Pixel Protocol avatar definition |
| **Item interaction types** | Whether the interaction class strings map to Pixel Protocol item type identifiers |
| **WebSocket frame handling** | Whether the WebSocket codec correctly adapts the binary protocol for browser clients |

---

## 15. Recommended Next Steps

These are listed in priority order for achieving 1:1 Pixel Protocol compliance and production stability.

1. **Opcode audit.** Compare every value in `Events.java` and `Composers.java` against the Pixel Protocol spec. Create a tracking issue for each mismatch. This is the highest-leverage action — a wrong opcode silently breaks every packet exchange.

2. **Re-enable and test WebSocket transport.** Nitro clients connect over WebSocket. The transport must be functional, tested with a real Nitro client, and covered by the stress test suite.

3. **Handshake compliance.** Determine whether the Pixel Protocol uses the inherited RSA/DH/RC4 handshake or a different mechanism (e.g., TLS + token). Adjust `HabboRSACrypto`, `HabboDiffieHellman`, and `HabboRC4` accordingly.

4. **Fix the groups module.** Known bugs in the guild system are a user-visible stability issue. Audit and fix before any public deployment.

5. **Remove dead dependencies.** BoneCP, Socket.IO (netty-socketio), and the dual logging framework should be removed to reduce classpath complexity and attack surface.

6. **Expand the plugin event catalogue.** The SDK value proposition depends on plugins being able to react to meaningful game events. Publish room join/leave, item place/remove, trade complete, and chat events to the event bus.

7. **Add startup configuration validation.** Fail fast with a clear error message when a required configuration key is absent or invalid.

8. **Stabilise the API module.** Remove `-SNAPSHOT` from `Comet-API` and `Comet-Networking-API` versioning. Define a public API contract and changelog.

9. **Wire up CI.** Configure the Jenkinsfile (or switch to GitHub Actions) to run `mvn clean verify` on every pull request.

10. **Document the REST API for operators.** Add authentication requirements, example requests, and security hardening notes (localhost-only binding, TLS reverse proxy).
