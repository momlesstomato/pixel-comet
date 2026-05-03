# AGENTS.md — Contribution & Code Standards

This document defines the mandatory standards for all code contributed to **pixel-comet**.
These rules apply to every new class, method, and module — no exceptions.

---

## 1. SOLID Principles

All new code **must** adhere to the SOLID object-oriented design principles:

| Principle | Rule |
|-----------|------|
| **Single Responsibility** | Each class has one reason to change. A handler handles; a composer composes; a service coordinates. Split concerns aggressively. |
| **Open/Closed** | Classes are open for extension (via interfaces or abstract bases), closed for modification. Prefer adding new implementations over editing existing ones. |
| **Liskov Substitution** | Subtypes must be substitutable for their supertypes without breaking behaviour. Do not override methods in ways that violate the contract of the parent. |
| **Interface Segregation** | Prefer many narrow interfaces over one fat one. A class should not be forced to implement methods it does not use. |
| **Dependency Inversion** | High-level modules must not depend on low-level modules. Depend on abstractions (interfaces/abstract classes), not concrete implementations. Inject dependencies; do not instantiate collaborators inline. |

---

## 2. Javadoc

Every public class, interface, enum, and public/protected method **must** have a Javadoc comment.

**Required elements:**

```java
/**
 * Short one-line summary ending with a period.
 *
 * <p>Optional extended description explaining intent, invariants,
 * or non-obvious constraints.
 *
 * @param paramName  Description of the parameter.
 * @return           Description of the return value.
 * @throws SomeException  When and why this is thrown.
 */
```

**Rules:**
- Do not restate what the method name already says ("Gets the id" for `getId()` is redundant — write *why* it is needed or what invariant it upholds instead).
- Document thread-safety guarantees where relevant.
- Document protocol-level semantics where a method maps to a Pixel Protocol concept.
- Private helpers do not require Javadoc but may have a brief `//` comment for non-obvious logic.

---

## 3. Zero Compiler Warnings

All new code **must compile without warnings**. Before opening a pull request:

- Run `mvn clean package -Werror` (or the equivalent goal for the target module).
- Fix every unchecked cast, raw type, unused import, unused variable, and deprecation warning.
- Do not suppress warnings with `@SuppressWarnings` unless you include a comment explaining why suppression is unavoidable (and link to the JDK or library issue that causes it).

---

## 4. Pixel Protocol Compliance

All packet handlers and composers **must** implement the Pixel Protocol specification exactly.

Reference: **https://momlesstomato.github.io/pixel-protocol/**

**Rules:**
- Incoming message opcode IDs must match the values defined in the protocol spec.
- Outgoing composer opcode IDs must match the protocol spec.
- Field order, field types (int, short, bool, string), and encoding rules must follow the spec byte-for-byte.
- If the spec is ambiguous or silent on a topic, document your interpretation in a Javadoc `@implNote` and open a tracking issue.
- Do not invent opcodes or extend the wire format without a corresponding spec change or an explicit `@NonStandard` annotation and issue reference.
- All Pixel-Protocol-related code lives under the `com.cometproject.server.protocol` package hierarchy.

---

## 5. Modularity, Reusability, and SDK/Plugin Readiness

The codebase is designed for future extraction as a public SDK and plugin platform. Every design decision must account for this.

**Rules:**

- **No static coupling across modules.** Game modules (`Comet-Game-*`) must interact with the core only through the interfaces defined in `Comet-API` and `Comet-Networking-API`. Direct instantiation of core classes from a module is forbidden.
- **Program to interfaces.** Every service, repository, manager, and handler must be backed by an interface. Concrete classes are injected, not looked up.
- **Events over direct calls.** Cross-module communication must go through the event bus. Do not call into another module's internals.
- **Stateless handlers.** Message event handlers must be stateless. Session-bound state lives on the `Player` or `Session` object, never on the handler.
- **Configurable, not hardcoded.** Thresholds, limits, and feature flags must come from the configuration system (`comet.properties` or a module-specific config block). Do not hardcode magic numbers.
- **No singleton abuse.** New singletons require explicit justification. Prefer dependency injection via constructor parameters.
- **Package visibility.** Use the most restrictive access modifier possible. Package-private is preferred over public for implementation classes.

---

## 6. Summary Checklist

Before every pull request, verify:

- [ ] Every public type and member has a Javadoc comment.
- [ ] `mvn clean package -Werror` passes with no warnings.
- [ ] All new packet opcodes match the Pixel Protocol spec.
- [ ] No direct dependency from a `Comet-Game-*` module onto `Comet-Server` internals.
- [ ] New functionality is exposed via an interface in `Comet-API` or `Comet-Networking-API`.
- [ ] No magic numbers — constants are named and documented.
- [ ] SOLID principles are followed (if in doubt, ask in the PR description and explain your design).
