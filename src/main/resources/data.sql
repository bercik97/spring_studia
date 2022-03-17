INSERT INTO role (role)
VALUES ('ROLE_DIRECTOR'),
       ('ROLE_EMPLOYEE'),
       ('ROLE_ADMIN');

INSERT INTO "user" (name, surname, email, password, company_name, is_enabled)
VALUES ('John', 'Doe', 'john.doe@mail.com', '$2a$12$mVgJk7LICl009AwmGKcSceNg4sLCGqhR9.aiD40af5Z39vjOYbHMu', 'Jobbed', true);

INSERT INTO user_roles (user_entity_id, roles_id)
VALUES (1, 1);

INSERT INTO company (name)
VALUES ('Jobbed');

INSERT INTO token (code, role_type, company_name)
VALUES ('111111', 'ROLE_DIRECTOR', 'Jobbed'),
       ('222222', 'ROLE_EMPLOYEE', 'Jobbed');
