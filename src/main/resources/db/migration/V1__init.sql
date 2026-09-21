-- Flyway baseline migration: create basic tables used by the app

CREATE TABLE user (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(50)
);

CREATE TABLE book (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(1024),
  author VARCHAR(512),
  price DECIMAL(19,2),
  stock INT,
  version INT
);

CREATE TABLE cart (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT,
  CONSTRAINT fk_cart_user FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE cart_item (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  book_id BIGINT,
  quantity INT,
  cart_id BIGINT,
  CONSTRAINT fk_cart_item_book FOREIGN KEY (book_id) REFERENCES book(id),
  CONSTRAINT fk_cart_item_cart FOREIGN KEY (cart_id) REFERENCES cart(id)
);

CREATE TABLE orders (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT,
  total DECIMAL(19,2),
  created_at TIMESTAMP,
  CONSTRAINT fk_orders_user FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE order_item (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  book_title VARCHAR(1024),
  price DECIMAL(19,2),
  quantity INT,
  order_entity_id BIGINT,
  CONSTRAINT fk_order_item_order FOREIGN KEY (order_entity_id) REFERENCES orders(id)
);
