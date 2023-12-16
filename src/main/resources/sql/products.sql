CREATE TABLE products (
    id VARCHAR(36) PRIMARY KEY NOT NULL,
    name VARCHAR(255),
    description TEXT,
    price NUMERIC(10, 4),
    available_stock INT
);