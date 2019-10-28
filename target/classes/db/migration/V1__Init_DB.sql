create sequence hibernate_sequence start 1 increment 1;

create table user_role
(
    user_id int8 not null,
    roles   varchar(255)
);

create table usr
(
    id              int8    not null,
    activation_code varchar(255),
    active          boolean not null,
    email           varchar(255),
    password        varchar(255) not null,
    username        varchar(255) not null,
    primary key (id)
);

create table monitoring_history
(
    id          int8 not null,
    date        varchar(255) not null,
    pressure    varchar(255) not null,
    temperature varchar(255) not null,
    wind_angle  varchar(255) not null,
    wind_speed  varchar(255) not null,
    user_id     int8,
    primary key (id)
);

alter table if exists user_role
    add constraint user_role_user_fk foreign key (user_id) references usr;

alter table if exists monitoring_history
    add constraint monitoring_history_user_fk foreign key (user_id) references usr;