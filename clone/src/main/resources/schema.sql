create table products
(
    product_id     BINARY(16) primary key,
    product_name   varchar(20) not null,
    category      varchar(50) not null,
    price         bigint      not null,
    description   varchar(500) default null,
    created_at    datetime(6) not null,
    updated_at     datetime(6) not null
);