CREATE TABLE IF NOT EXISTS diary
(
    id
    UUID
    DEFAULT
    RANDOM_UUID
(
),
    user_id UUID NOT NULL,
    health_state VARCHAR
(
    50
),
    timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
                            user_notes VARCHAR (1000),
    PRIMARY KEY
(
    id
)
    );

ALTER TABLE diary
    ADD COLUMN IF NOT EXISTS ai_notes VARCHAR (2000);

CREATE INDEX idx_diary_user_timestamp ON diary (user_id, timestamp);

CREATE TABLE IF NOT EXISTS weather
(
    id
    UUID
    DEFAULT
    RANDOM_UUID
(
),
    weather_condition VARCHAR
(
    50
),
    temperature DOUBLE PRECISION,
    diary_page_id UUID NOT NULL,
    timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
                            CONSTRAINT fk_weather_diary FOREIGN KEY (diary_page_id) REFERENCES diary
(
    id
)
                        ON DELETE CASCADE,
    PRIMARY KEY
(
    id
)
    );

CREATE TABLE IF NOT EXISTS medicine
(
    id
    UUID
    DEFAULT
    gen_random_uuid
(
),
    medicine_name VARCHAR
(
    255
),
    medicine_description VARCHAR
(
    1000
),
    medicine_type VARCHAR
(
    50
),
    dose DOUBLE PRECISION NOT NULL,
    diary_page_id UUID NOT NULL,
    dose_measure_type VARCHAR
(
    50
),
    PRIMARY KEY
(
    id
),
    CONSTRAINT fk_medicine_diary FOREIGN KEY
(
    diary_page_id
) REFERENCES diary
(
    id
) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS user_symptom
(
    id
    UUID
    DEFAULT
    gen_random_uuid
(
),
    symptom_name VARCHAR
(
    255
) NOT NULL,
    diary_page_id UUID NOT NULL,
    timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
                            symptom_state VARCHAR (50),
    PRIMARY KEY
(
    id
),
    CONSTRAINT fk_symptom_diary FOREIGN KEY
(
    diary_page_id
) REFERENCES diary
(
    id
)
                        ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS allergy_trigger
(
    id
    UUID
    DEFAULT
    gen_random_uuid
(
),
    trigger_name VARCHAR
(
    255
) NOT NULL,
    PRIMARY KEY
(
    id
),
    trigger_state VARCHAR
(
    50
)
    );

CREATE INDEX idx_medicine_diary ON medicine (diary_page_id);
CREATE INDEX idx_symptom_diary ON user_symptom (diary_page_id);
CREATE INDEX idx_weather_diary ON weather (diary_page_id);
