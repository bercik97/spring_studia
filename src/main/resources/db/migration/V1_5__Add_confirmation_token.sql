create sequence confirmation_token_id_seq;

create table confirmation_token
(
    id             bigint primary key      default nextval('confirmation_token_id_seq'),
    created_date   TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    uuid           varchar unique not null,
    user_id        bigint         not null,
    token_type     varchar        not null,
    confirmed_date timestamp
);
