create sequence comment_id_seq;

create table comment
(
    id           bigint primary key default nextval('comment_id_seq'),
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date         date      not null,
    message      varchar   not null,
    author       varchar   not null,
    type         varchar   not null,
    relation_id  bigint    not null,
    company_name varchar   not null
);
