USE `coffee_bean`;

CREATE TABLE IF NOT EXISTS `coffee_bean`.`products`
(
    `product_id`   BINARY(16)   NOT NULL,
    `product_name` VARCHAR(40)  NOT NULL,
    `category`     VARCHAR(45)  NOT NULL,
    `price`        BIGINT       NOT NULL,
    `quantity`     BIGINT       NOT NULL,
    `description`  VARCHAR(500) NOT NULL,
    `created_at`   DATETIME(6)  NOT NULL,
    `updated_at`   DATETIME(6)  NOT NULL,
    PRIMARY KEY (`product_id`)
)
    ENGINE = InnoDB;



CREATE TABLE IF NOT EXISTS `coffee_bean`.`orders`
(
    `order_id`     BINARY(16)   NOT NULL,
    `email`        VARCHAR(50)  NOT NULL,
    `address`      VARCHAR(100) NOT NULL,
    `postcode`     VARCHAR(100) NOT NULL,
    `order_status` VARCHAR(50)  NOT NULL,
    `created_at`   DATETIME(6)  NOT NULL,
    `updated_at`   DATETIME(6)  NOT NULL,
    PRIMARY KEY (`order_id`)
)
    ENGINE = InnoDB;



CREATE TABLE IF NOT EXISTS `coffee_bean`.`order_items`
(
    `order_item_id` BIGINT auto_increment NOT NULL,
    `order_id`      BINARY(16)                NOT NULL,
    `product_id`    BINARY(16)                NOT NULL,
    `category`      VARCHAR(45)           NOT NULL,
    `quantity`      BIGINT                NOT NULL,
    `created_at`    DATETIME(6)           NOT NULL,
    `updated_at`    DATETIME(6)           NOT NULL,
    PRIMARY KEY (`order_item_id`),
    INDEX `fk_order_items_products_idx` (`product_id` ASC) VISIBLE,
    INDEX `fk_order_items_orders1_idx` (`order_id` ASC) VISIBLE,
    CONSTRAINT `fk_order_items_products`
        FOREIGN KEY (`product_id`)
            REFERENCES `coffee_bean`.`products` (`product_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_order_items_orders1`
        FOREIGN KEY (`order_id`)
            REFERENCES `coffee_bean`.`orders` (`order_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;