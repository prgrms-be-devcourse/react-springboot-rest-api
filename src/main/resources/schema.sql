CREATE TABLE IF NOT EXISTS products
(
    product_id  BINARY(16)   NOT NULL PRIMARY KEY,
    name        VARCHAR(20)  NOT NULL UNIQUE,
    category    VARCHAR(50)  NOT NULL,
    price       bigint       NOT NULL,
    description VARCHAR(500) NOT NULL,
    created_at  datetime(6)  NOT NULL,
    updated_at  datetime(6)  NOT NULL
);

CREATE TABLE IF NOT EXISTS orders
(
    order_id     binary(16)   NOT NULL PRIMARY KEY,
    email        varchar(50)  NOT NULL,
    address      varchar(200) NOT NULL,
    postcode     varchar(200) NOT NULL,
    order_status varchar(50)  NOT NULL,
    created_at   datetime(6)  NOT NULL,
    updated_at   datetime(6)  NOT NULL
);

CREATE TABLE IF NOT EXISTS order_items
(
    seq        bigint      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    order_id   binary(16)  NOT NULL,
    product_id binary(16)  NOT NULL,
    category   VARCHAR(50) NOT NULL,
    price      bigint      NOT NULL,
    count      int         NOT NULL,
    created_at datetime(6) NOT NULL,
    updated_at datetime(6) NOT NULL,
    INDEX (order_id),
    CONSTRAINT fk_order_items_to_order FOREIGN KEY (order_id) REFERENCES orders (order_id) ON DELETE CASCADE,
    CONSTRAINT fk_order_items_to_product FOREIGN KEY (product_id) REFERENCES products (product_id)
);

