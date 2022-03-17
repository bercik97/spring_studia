create sequence notification_id_seq;

create table notification
(
    id           bigint primary key default nextval('notification_id_seq'),
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    recipient    varchar   not null,
    subject      varchar   not null,
    type         varchar   not null,
    error        varchar,
    success      bool      not null default false
);
