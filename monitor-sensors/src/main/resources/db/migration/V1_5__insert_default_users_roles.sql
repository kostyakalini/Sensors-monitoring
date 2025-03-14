INSERT INTO roles (id, name)
VALUES (1, 'Administrator'),
       (0, 'Viewer');

INSERT INTO users (username, password)
VALUES ('admin', '$2a$10$M0d99vjJlV9kKrG4mCevwO398SKJSC01/AEW3G0u9ywcp7MnaV.hO'),
       ('user', '$2a$10$03.xaoKfp9dMQysKqxRK/uneWjZYp4rR41XjetpjOGeOSB2jDalY.'),
       ('monitoring-statistics', '$2a$10$ACI9/QgcTGO0gjMKKKrKG.XFSqb/En4RMSBUcPeMLi88dy4LhRMZm');

INSERT INTO user_roles (user_id, role_id)
VALUES ((SELECT id FROM users WHERE username = 'admin'), 1),
       ((SELECT id FROM users WHERE username = 'user'), 0),
       ((SELECT id FROM users WHERE username = 'monitoring-statistics'), 0);