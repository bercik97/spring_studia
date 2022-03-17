create sequence workplace_id_seq;

create table workplace
(
    id           bigint primary key default nextval('workplace_id_seq'),
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    name         varchar   not null,
    address      varchar   not null,
    description  varchar   not null,
    latitude     float8    not null,
    longitude    float8    not null,
    company_name varchar   not null
);
