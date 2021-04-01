drop table if exists role;
create table role
(
    id         bigserial,
    name       varchar   not null,
    created_at timestamp not null default current_timestamp,
    updated_at timestamp not null default current_timestamp,
    primary key (id),
    unique (name)
);

insert into role (name)
values ('ADMIN'),
       ('USER');

drop table if exists user_role;
create table user_role
(
    id      bigserial,
    user_id bigint not null,
    role_id bigint not null,
    primary key (id, user_id, role_id),
    foreign key (user_id) references "user" (id) on update cascade on delete cascade,
    foreign key (role_id) references role (id) on update cascade on delete cascade
)
