CREATE TABLE IF NOT EXISTS users
(
    id
    UUID
    PRIMARY
    KEY,
    name     VARCHAR(255) NOT NULL,
    age      INT          NOT NULL CHECK (age BETWEEN 12 AND 99),
    gender   VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(50)  NOT NULL,
    diary_id UUID
);

