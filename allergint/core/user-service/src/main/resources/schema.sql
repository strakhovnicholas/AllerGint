CREATE TABLE IF NOT EXISTS users
(
    id UUID PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    age      INT          NOT NULL CHECK (age BETWEEN 12 AND 99),
    gender   VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(50)  NOT NULL,
    diary_id UUID
);

CREATE TABLE IF NOT EXISTS allergens
(
    id       UUID PRIMARY KEY,
    user_id  UUID         NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    name     VARCHAR(255) NOT NULL,
    severity VARCHAR(10)  NOT NULL
);

CREATE TABLE IF NOT EXISTS allergen_months
(
    allergen_id UUID       NOT NULL REFERENCES allergens (id) ON DELETE CASCADE,
    month       VARCHAR(3) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_symptom_preferences
(
    id           UUID PRIMARY KEY,
    user_id      UUID         NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    symptom_name VARCHAR(255) NOT NULL,
    frequency    VARCHAR(10)
);
