drop table if exists role;
create table role
(
    id   bigserial,
    name varchar not null,
    primary key (id),
    unique (name)
);

drop table if exists user_role;
create table user_role
(
    id      bigserial,
    user_id bigint not null,
    role_id bigint not null,
    primary key (id),
    unique (user_id, role_id),
    foreign key (user_id) references "user" (id) on update cascade on delete cascade,
    foreign key (role_id) references role (id) on update cascade on delete cascade
)
