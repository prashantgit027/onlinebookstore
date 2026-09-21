-- Seed an admin user for development. Password is BCrypt-hashed value for 'admin'.
-- Generated with: new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("admin")
INSERT INTO user (username, password, role) VALUES ('admin', '$2a$10$lfgDtHd9lnkz/nCyvwJHEelpKRN/zZ/zrvpYoPFXR06qfhkewhoQu', 'ADMIN');
