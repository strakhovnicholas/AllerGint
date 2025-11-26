CREATE TABLE IF NOT EXISTS diary
(
    id           UUID PRIMARY KEY,
    user_id      UUID NOT NULL,
    health_state VARCHAR(50),
    timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
    user_notes   VARCHAR(1000),
    ai_notes     VARCHAR(2000)
);

CREATE TABLE IF NOT EXISTS medicine
(
    id                   UUID PRIMARY KEY,
    medicine_name        VARCHAR(255) NOT NULL,
    dose                 VARCHAR(255),
    dose_measure_type    VARCHAR(255),
    medicine_description VARCHAR(255),
    medicine_type        VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS user_symptom
(
    id            UUID PRIMARY KEY,
    symptom_name  VARCHAR(255)             NOT NULL,
    symptom_state VARCHAR(50),
    timestamp TIMESTAMP WITH TIME ZONE
);

CREATE TABLE IF NOT EXISTS weather
(
    id                UUID PRIMARY KEY,
    weather_condition VARCHAR(50),
    temperature       DOUBLE PRECISION,
    timestamp         TIMESTAMP WITH TIME ZONE
);

CREATE TABLE IF NOT EXISTS diary_medicine
(
    diary_id    UUID NOT NULL,
    medicine_id UUID NOT NULL,
    PRIMARY KEY (diary_id, medicine_id),
    CONSTRAINT fk_diary_medicine_diary FOREIGN KEY (diary_id) REFERENCES diary (id) ON DELETE CASCADE,
    CONSTRAINT fk_diary_medicine_medicine FOREIGN KEY (medicine_id) REFERENCES medicine (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS diary_symptom
(
    diary_id   UUID NOT NULL,
    symptom_id UUID NOT NULL,
    PRIMARY KEY (diary_id, symptom_id),
    CONSTRAINT fk_diary_symptom_diary FOREIGN KEY (diary_id) REFERENCES diary (id) ON DELETE CASCADE,
    CONSTRAINT fk_diary_symptom_symptom FOREIGN KEY (symptom_id) REFERENCES user_symptom (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS diary_weather
(
    diary_id   UUID,
    weather_id UUID,
    PRIMARY KEY (diary_id, weather_id),
    CONSTRAINT fk_diary_weather_diary FOREIGN KEY (diary_id) REFERENCES diary (id) ON DELETE CASCADE,
    CONSTRAINT fk_diary_weather_weather FOREIGN KEY (weather_id) REFERENCES weather (id) ON DELETE CASCADE
);
