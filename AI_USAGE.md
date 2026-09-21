# AI-Assisted Development Notes

This document records how AI assistance was used while building and
hardening this project.

## Tooling
- GitHub Copilot (agent mode, Claude Sonnet model) was used as a pair
  programmer for implementing plans,features, refactoring, diagnosing build/test
  failures, and writing this documentation.

## How AI was used
- **Feature implementation**: domain modelling (encapsulation of stock/quantity
  invariants), DTO + Bean Validation wiring, service-layer interfaces
  (`BookServicePort`, `CartServicePort`, `UserServicePort`), and design-pattern
  introductions (Builder for `Book`, Factory for order creation, Strategy for
  pricing).
- **Build/dependency troubleshooting**: root-caused transitive dependency
  conflicts (e.g. `springdoc-openapi-ui` and `flyway-core` pulling in a newer
  `spring-boot-autoconfigure` than the pinned Spring Boot version), duplicate
  Spring bean definitions (`GlobalExceptionHandler`, `passwordEncoder`), and
  incorrect JPA `@OneToMany` mappings that didn't match the Flyway-managed
  schema.
- **Test authoring**: unit tests (Mockito-based) for services, `@WebMvcTest`
  slice tests for controller security rules, and verification of ORM/entity
  mapping correctness.
- **Verification**: every change in this session was validated by running
  `./gradlew clean test jacocoTestReport`, then starting the application
  (`./gradlew run`) and exercising the REST API with `curl` (registration,
  login, catalog browsing with pagination, cart add/update/remove, checkout,
  and admin-only book CRUD authorization checks).

## Human review
- All AI-proposed changes were self reviewed for correctness against the existing
  test suite and manual `curl` smoke tests before being considered complete.
- No changes were auto-committed to git; the developer/self reviewer controls
  when and what gets committed.

## Prompts (representative, paraphrased)
1. "Incorporate production-readiness improvements: domain encapsulation,
   SOLID, DTO validation, Flyway, optimistic locking, Actuator, Lombok,
   tests, CI."
2. "Make the Gradle build actually compile and pass tests in this sandbox."
3. "Run the app, check the curls and test cases."
4. "Verify which of the failure findings have actually been addressed in
   the codebase."
5. "Fix everything to comply with the failure points; run the app and check
   curls/tests; don't commit anything."
