CREATE TABLE IF NOT EXISTS Client
(
    id         INT IDENTITY (1,1) PRIMARY KEY,
    first_name VARCHAR(30) NOT NULL,
    last_name  VARCHAR(30) NOT NULL
    );


CREATE TABLE IF NOT EXISTS Account
(
    id        INT IDENTITY (1,1) PRIMARY KEY,
    number    VARCHAR(20) NOT NULL UNIQUE,
    balance   DECIMAL,
    client_id INT   NOT NULL UNIQUE,
    FOREIGN KEY (client_id) REFERENCES Client (id)
    );
-- CREATE UNIQUE index account_number_UINDEX ON Account (number);


CREATE TABLE IF NOT EXISTS Card
(
    id         INT IDENTITY (1,1) PRIMARY KEY,
    number     VARCHAR(20) NOT NULL UNIQUE,
    month      VARCHAR(2)  NOT NULL, -- VARCHAR(2)
    year       VARCHAR(4)  NOT NULL, -- VARCHAR(4)
    code       VARCHAR(3),
    balance    DECIMAL,
    status     INT DEFAULT 0,
    account_id INT         NOT NULL,
    FOREIGN KEY (account_id) REFERENCES Account (id)
    );
-- CREATE UNIQUE index card_number_UINDEX ON Card (number);