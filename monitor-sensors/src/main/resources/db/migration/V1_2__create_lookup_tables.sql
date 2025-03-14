CREATE TABLE units
(
    id   INT PRIMARY KEY,
    name VARCHAR(10) NOT NULL,
    UNIQUE (name)
);

CREATE TABLE types
(
    id   INT PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    UNIQUE (name)
);