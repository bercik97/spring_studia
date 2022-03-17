create sequence role_id_seq;
create sequence user_id_seq;

create table role
(
    id           bigint primary key default nextval('role_id_seq'),
    created_date timestamp not null default CURRENT_TIMESTAMP,
    role         varchar   not null unique
);

create table "user"
(
    id           bigint primary key default nextval('user_id_seq'),
    created_date timestamp not null default CURRENT_TIMESTAMP,
    name         varchar   not null,
    surname      varchar   not null,
    email        varchar   not null unique,
    password     varchar   not null,
    company_name varchar   not null,
    is_enabled   bool      not null default false
);

create table user_roles
(
    user_entity_id bigint not null,
    roles_id       bigint not null,
    FOREIGN KEY (user_entity_id) REFERENCES "user" (id),
    FOREIGN KEY (roles_id) REFERENCES role (id)
);

INSERT INTO "role" (role)
VALUES ('ROLE_DIRECTOR'),
       ('ROLE_EMPLOYEE'),
       ('ROLE_ADMIN');
