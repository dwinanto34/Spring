
CREATE TABLE orders (
    id VARCHAR(36) PRIMARY KEY NOT NULL,
    order_id VARCHAR(20) UNIQUE,
    amount NUMERIC(10, 4)
);

CREATE TABLE order_items (
    id VARCHAR(36) PRIMARY KEY NOT NULL,
    order_id VARCHAR(20),
    product_id VARCHAR(36),
    order_item_id VARCHAR(20) UNIQUE,
    price_per_quantity NUMERIC(10, 4),
    quantity INT,
    final_amount NUMERIC(10, 4),
    CONSTRAINT fk_order_items_orders FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT fk_order_items_products FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

