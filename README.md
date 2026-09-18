# Online Bookstore

Simple Spring Boot REST API for an online bookstore with cart and orders.

Run (Gradle):

```bash
# using the Gradle wrapper (recommended if present)
./gradlew run

# run the tests
./gradlew test

# or with a system-installed Gradle
gradle run
```

The app starts on `http://localhost:8080`.

A ready-to-use Postman collection is included at
[`onlinebookstore.postman_collection.json`](onlinebookstore.postman_collection.json).
Import it into Postman and run the requests in order (Register → Login → Get
Books → Get Cart → Add/Update/Remove → Checkout) — the JWT `token`, `bookId`,
and `cartItemId` are captured automatically via test scripts and reused across
requests.

Endpoints:
- `GET /api/books` list books
- `GET /api/books/{id}` get book
- `POST /api/auth/register` register {username,password}
- `POST /api/auth/login` login {username,password} (returns JWT token)
- `GET /api/cart` view cart (requires `Authorization: Bearer <token>`)
- `POST /api/cart/add` add item {bookId,qty}
- `POST /api/cart/update` update {cartItemId,qty}
- `POST /api/cart/remove` remove {cartItemId}
- `POST /api/cart/checkout` checkout

H2 console available at `/h2-console`.

Authentication:
- Register with `POST /api/auth/register`.
- Login with `POST /api/auth/login` to receive a JSON `{ "token": "..." }`.
- For protected endpoints, send header `Authorization: Bearer <token>`.

## Example curl requests

List all books:
```bash
curl -s http://localhost:8080/api/books
```

Get a single book by id:
```bash
curl -s http://localhost:8080/api/books/1
```

Register a new user:
```bash
curl -s -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"alice","password":"secret123"}'
```

Login and capture the JWT into a shell variable:
```bash
TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"alice","password":"secret123"}' \
  | python3 -c "import sys,json;print(json.load(sys.stdin)['token'])")
```

View the current user's cart:
```bash
curl -s http://localhost:8080/api/cart \
  -H "Authorization: Bearer $TOKEN"
```

Add a book to the cart:
```bash
curl -s -X POST http://localhost:8080/api/cart/add \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"bookId":"1","qty":"2"}'
```

Update quantity of a cart item:
```bash
curl -s -X POST http://localhost:8080/api/cart/update \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"cartItemId":"1","qty":"3"}'
```

Remove an item from the cart:
```bash
curl -s -X POST http://localhost:8080/api/cart/remove \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"cartItemId":"1"}'
```

Checkout (creates an order from the current cart):
```bash
curl -s -X POST http://localhost:8080/api/cart/checkout \
  -H "Authorization: Bearer $TOKEN"
```

# onlinebookstore
We will create a Simple Online Bookstore. We will display a list of books and users will have the possibility to add books to their cart, display the cart and modify the quantity of items and remove items from the cart. RESTful API using Spring Boot and Java 8 
----
Notes:
- This project uses Gradle. If you don't have the Gradle wrapper (`gradlew`) present, you can install Gradle or generate the wrapper with `gradle wrapper`.
