drop table if exists privilege;
create table privilege
(
    id   bigserial,
    name varchar not null,
    primary key (id),
    unique (name)
);

drop table if exists role_privilege;
create table role_privilege
(
    id           bigserial,
    role_id      bigint not null,
    privilege_id bigint not null,
    primary key (id),
    unique (privilege_id, role_id),
    foreign key (privilege_id) references privilege (id) on update cascade on delete cascade,
    foreign key (role_id) references role (id) on update cascade on delete cascade
)
