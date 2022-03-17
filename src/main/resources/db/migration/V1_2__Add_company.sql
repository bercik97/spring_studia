create sequence company_id_seq;

create table company
(
    id           bigint primary key default nextval('company_id_seq'),
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    name         varchar   not null unique
);
