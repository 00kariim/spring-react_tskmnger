-- Flyway migration: create test admin user if not exists
-- Inserts a user with email admin@admin.com and password 123456 (password stored as plain text to match current login logic)

INSERT INTO users (email, password_hash)
SELECT 'admin@admin.com', '123456'
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE email = 'admin@admin.com'
);
