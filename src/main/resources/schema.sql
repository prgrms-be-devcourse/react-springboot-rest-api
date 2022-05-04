CREATE TABLE products
(
    product_id  BINARY(16) PRIMARY KEY,
    name        VARCHAR(20)  NOT NULL UNIQUE,
    category    VARCHAR(50)  NOT NULL,
    price       bigint       NOT NULL,
    description VARCHAR(500) NOT NULL,
    created_at  datetime(6)  NOT NULL,
    updated_at  datetime(6)  NOT NULL
);
