create table if not exists projects
(
    id           bigserial
        constraint projects_pk
            primary key,
    name         varchar not null,
    code         varchar not null,
    callback_url varchar,
    created_at   timestamp,
    updated_at   timestamp,
    created_by   bigint,
    updated_by   bigint
);



create unique index if not exists projects_name_code_uindex
    on projects (name, code);

create table if not exists roles
(
    id         bigserial
        constraint roles_pk
            primary key,
    project_id bigint,
    name       varchar,
    code       varchar,
    issystem   boolean default false,
    created_at timestamp,
    updated_at timestamp,
    created_by bigint,
    updated_by bigint
);



create table if not exists permissions
(
    id          bigserial
        constraint permissions_pk
            primary key,
    role        bigint
        constraint permissions_roles_id_fk
            references roles,
    "create"    varchar,
    read        varchar,
    update      varchar,
    comment     varchar,
    issystem    boolean default false,
    created_at  timestamp,
    updated_at  timestamp,
    created_by  bigint,
    modified_by bigint,
    resournce   varchar
);





create table if not exists users
(
    id           bigserial
        constraint users_pk
            primary key,
    username     varchar,
    password     varchar,
    firstname    varchar,
    lastname     varchar,
    status       varchar,
    date_created timestamp,
    date_update  timestamp
);




