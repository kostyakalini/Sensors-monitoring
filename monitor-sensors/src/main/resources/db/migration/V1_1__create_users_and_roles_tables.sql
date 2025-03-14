CREATE SEQUENCE user_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE users
(
    id       BIGINT PRIMARY KEY DEFAULT NEXTVAL('user_id_seq'),
    username VARCHAR(50)  NOT NULL,
    password VARCHAR(100) NOT NULL,
    UNIQUE (username)
);

CREATE TABLE roles
(
    id   INT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    UNIQUE (name)
);

CREATE TABLE user_roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id),
    PRIMARY KEY (user_id, role_id)
);