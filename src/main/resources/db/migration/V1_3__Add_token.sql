create sequence token_id_seq;

create table token
(
    id           bigint primary key default nextval('token_id_seq'),
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    code         varchar   not null unique,
    role_type    varchar   not null,
    company_name varchar   not null
);
