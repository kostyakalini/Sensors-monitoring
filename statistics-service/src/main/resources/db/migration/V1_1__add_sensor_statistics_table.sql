CREATE SEQUENCE statistics_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE sensors_statistics
(
    id                 BIGINT PRIMARY KEY DEFAULT NEXTVAL('statistics_id_seq'),
    total_sensors      INT  NOT NULL,
    sensor_type_counts VARCHAR NOT NULL,
    date               DATE NOT NULL
);