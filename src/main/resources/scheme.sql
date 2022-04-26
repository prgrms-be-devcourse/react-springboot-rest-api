CREATE TABLE products
(
    product_Id    BINARY(16) PRIMARY KEY,
    product_name VARCHAR(20) NOT NULL,
    category     VARCHAR(50) NOT NULL,
    price        bigint      NOT NULL,
    description  varchar(500) default null,
    created_at   datetime(6) NOT NULL,
    updated_at   datetime(6)  default null

)