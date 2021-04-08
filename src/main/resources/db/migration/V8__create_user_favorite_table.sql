drop table if exists user_favorite;
create table user_favorite
(
    id       bigserial,
    user_id  bigint not null,
    movie_id bigint not null,
    primary key (id),
    unique (user_id, movie_id),
    foreign key (user_id) references "user" (id) on update cascade on delete cascade,
    foreign key (movie_id) references movie (id) on update cascade on delete cascade
)
