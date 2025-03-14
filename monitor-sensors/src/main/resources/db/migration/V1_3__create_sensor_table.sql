CREATE SEQUENCE sensor_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE sensors
(
    id          BIGINT PRIMARY KEY DEFAULT NEXTVAL('sensor_id_seq'),
    name        VARCHAR(30) NOT NULL,
    model       VARCHAR(15) NOT NULL,
    range_from  INT         NOT NULL CHECK (range_from > 0),
    range_to    INT         NOT NULL CHECK (range_to > range_from),
    type_id     INT         NOT NULL,
    unit_id     INT,
    location    VARCHAR(40),
    description VARCHAR(200),
    CHECK (char_length(name) >= 3),
    CHECK (char_length(model) <= 15),
    CHECK (char_length(location) <= 40),
    CHECK (char_length(description) <= 200),
    CONSTRAINT fk_sensor_type FOREIGN KEY (type_id) REFERENCES types (id),
    CONSTRAINT fk_sensor_unit FOREIGN KEY (unit_id) REFERENCES units (id)
);