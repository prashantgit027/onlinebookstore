# Functional Specification — Online Bookstore

## 1. Purpose
A RESTful online bookstore API that lets customers browse a book catalog,
manage a shopping cart, and check out to create an order. Administrators can
manage the book catalog (create/update/delete).

## 2. Actors
- **Guest / Customer (`ROLE_USER`)**: browse books, register, log in, manage
  their own cart, checkout.
- **Administrator (`ROLE_ADMIN`)**: everything a customer can do, plus create,
  update, and delete books.

## 3. Functional Requirements

### 3.1 Catalog
- FR-1: Any user (authenticated or not) can list books, with pagination and
  sorting (`GET /api/books?page=&size=&sort=`).
- FR-2: Any user can fetch a single book by id (`GET /api/books/{id}`).
- FR-3: Only `ROLE_ADMIN` can create a book (`POST /api/books`).
- FR-4: Only `ROLE_ADMIN` can update a book (`PUT /api/books/{id}`).
- FR-5: Only `ROLE_ADMIN` can delete a book (`DELETE /api/books/{id}`).
- FR-6: Book payloads are validated: title/author required, price and stock
  must be present and non-negative.

### 3.2 Authentication
- FR-7: A new user can register with a username and password
  (`POST /api/auth/register`). Username must be unique; password must be at
  least 6 characters.
- FR-8: A registered user can log in (`POST /api/auth/login`) and receive a
  signed JWT to use as a Bearer token on subsequent requests.
- FR-9: New users are assigned `ROLE_USER` by default. An `ADMIN` account is
  seeded via a database migration for administrative operations.

### 3.3 Cart
- FR-10: An authenticated user has exactly one cart, created on first use.
- FR-11: A user can add a book to their cart with a positive quantity
  (`POST /api/cart/add`). Adding a book already in the cart increases its
  quantity.
- FR-12: A user can update the quantity of an existing cart item
  (`POST /api/cart/update`); quantity must be positive.
- FR-13: A user can remove an item from their cart (`POST /api/cart/remove`).
- FR-14: A user can view their current cart (`GET /api/cart`).

### 3.4 Checkout / Orders
- FR-15: A user can check out their cart (`POST /api/cart/checkout`), which:
  - Fails if the cart is empty.
  - Fails if any book has insufficient stock (no partial orders).
  - Decrements stock for each purchased book (optimistic locking via
    `@Version` prevents lost updates under concurrent checkouts).
  - Creates an order recording book title, unit price, and quantity at the
    time of purchase (order line items are immutable snapshots).
  - Clears the cart on success.

## 4. Non-Functional Requirements
- NFR-1: Stateless JWT-based authentication (no server-side sessions).
- NFR-2: Schema changes are version-controlled via Flyway migrations, not
  runtime DDL auto-generation.
- NFR-3: Health and metrics are exposed via Spring Boot Actuator
  (`/actuator/health`, `/actuator/metrics`, `/actuator/prometheus`).
- NFR-4: API documentation is available via springdoc OpenAPI/Swagger UI
  (`/swagger-ui.html`).
- NFR-5: Business rule violations (insufficient stock, invalid quantity,
  duplicate username, empty cart checkout) return `4xx` responses with a
  descriptive message, not raw stack traces.
- NFR-6: Unit and slice tests cover domain invariants, service orchestration,
  and controller security rules; CI runs the build and tests on every push/PR.

## 5. Out of Scope (documented, not implemented)
- Payment processing / external payment gateway integration.
- Multi-currency pricing.
- Full OAuth2/OIDC provider (e.g. Keycloak) integration — a disabled
  integration-test placeholder and realm-import fixture are provided as a
  starting point, but live IdP integration requires container
  infrastructure (Docker) not available in all build environments.
