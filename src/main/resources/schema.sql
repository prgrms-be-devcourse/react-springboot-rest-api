CREATE TABLE contracts (
    contract_id BIGINT AUTO_INCREMENT ,
    email VARCHAR(50) NOT NULL ,
    name VARCHAR(20) NOT NULL ,
    contract_status VARCHAR(50) NOT NULL ,
    created_at DATETIME(6) NOT NULL ,
    updated_at DATETIME(6) NOT NULL ,
    PRIMARY KEY (contract_id)
);

CREATE TABLE clouds (
    cloud_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    price BIGINT NOT NULL ,
    users INT NOT NULL ,
    storage INT NOT NULL ,
    created_at DATETIME(6) NOT NULL ,
    updated_at DATETIME(6) NOT NULL
);

CREATE TABLE options (
    option_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    contract_id BIGINT NOT NULL ,
    cloud_id BIGINT NOT NULL,
    title VARCHAR(50) NOT NULL ,
    detail TEXT ,
    FOREIGN KEY (contract_id) REFERENCES contracts(contract_id)
                     ON DELETE CASCADE ,
    FOREIGN KEY (cloud_id) REFERENCES clouds(cloud_id)
                     ON DELETE CASCADE
);

ALTER TABLE clouds ADD category VARCHAR(50) AFTER cloud_id;

ALTER TABLE clouds ADD img VARCHAR(100) AFTER storage;

ALTER TABLE clouds ADD name VARCHAR(30) AFTER cloud_id;

ALTER TABLE clouds CHANGE name cloud_name VARCHAR(30);

ALTER TABLE contracts CHANGE name user_name VARCHAR(30);

DROP TABLE options;

ALTER TABLE contracts ADD cloud_id BIGINT AFTER contract_id;

ALTER TABLE contracts ADD FOREIGN KEY(cloud_id) REFERENCES clouds(cloud_id)
ON UPDATE CASCADE;