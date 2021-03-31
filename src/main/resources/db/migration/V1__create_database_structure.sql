drop table if exists "user";
create table "user"
(
    id         bigserial,
    first_name varchar   not null,
    last_name  varchar   not null,
    email      varchar   not null,
    password   varchar   not null,
    status     varchar   not null,
    created_at timestamp not null default current_timestamp,
    updated_at timestamp not null default current_timestamp,
    primary key (id),
    unique (email)
);

drop table if exists movie;
create table movie
(
    id                     bigserial,
    imdb_id                varchar unique not null,
    title                  varchar                 default null,
    original_title         varchar                 default null,
    year                   varchar                 default null,
    date_published         varchar                 default null,
    duration               varchar                 default null,
    director               varchar                 default null,
    writer                 varchar                 default null,
    production_company     varchar                 default null,
    actors                 text                    default null,
    description            text                    default null,
    avg_vote               varchar                 default null,
    budget                 varchar                 default null,
    usa_gross_income       varchar                 default null,
    worldwide_gross_income varchar                 default null,
    metas_core             varchar                 default null,
    reviews_from_users     varchar                 default null,
    reviews_from_critics   varchar                 default null,
    created_at             timestamp      not null default current_timestamp,
    updated_at             timestamp      not null default current_timestamp,
    primary key (id)
);

drop table if exists person;
create table person
(
    id                    bigserial,
    imdb_id               varchar unique not null,
    name                  varchar                 default null,
    birth_name            varchar                 default null,
    height                varchar                 default null,
    bio                   text                    default null,
    birth_details         varchar                 default null,
    date_of_birth         varchar                 default null,
    place_of_birth        varchar                 default null,
    death_details         varchar                 default null,
    date_of_death         varchar                 default null,
    place_of_death        varchar                 default null,
    reason_of_death       varchar                 default null,
    spouses_string        text                    default null,
    spouses               varchar                 default null,
    divorces              varchar                 default null,
    spouses_with_children varchar                 default null,
    children              varchar                 default null,
    created_at            timestamp      not null default current_timestamp,
    updated_at            timestamp      not null default current_timestamp,
    primary key (id)
);

drop table if exists movie_person;
create table movie_person
(
    id         bigserial,
    movie_id   bigint not null,
    ordering   int    not null,
    person_id  bigint not null,
    category   varchar default null,
    job        varchar default null,
    characters varchar default null,
    primary key (id, movie_id, person_id),
    foreign key (movie_id) references movie (id) on update cascade on delete cascade,
    foreign key (person_id) references person (id) on update cascade on delete cascade
);

drop table if exists genre;
create table genre
(
    id         bigserial,
    name       varchar unique not null,
    created_at timestamp      not null default current_timestamp,
    updated_at timestamp      not null default current_timestamp,
    primary key (id)
);

drop table if exists lanfuage;
create table language
(
    id         bigserial,
    name       varchar unique not null,
    created_at timestamp      not null default current_timestamp,
    updated_at timestamp      not null default current_timestamp,
    primary key (id)
);

drop table if exists rating;
create table rating
(
    id             bigserial,
    movie_id       bigint unique not null,
    average_rating varchar                default null,
    num_votes      varchar                default null,
    created_at     timestamp     not null default current_timestamp,
    updated_at     timestamp     not null default current_timestamp,
    foreign key (movie_id) references movie (id) on update cascade on delete cascade,
    primary key (id)
);

drop table if exists country;
create table country
(
    id         bigserial,
    name       varchar unique not null,
    created_at timestamp      not null default current_timestamp,
    updated_at timestamp      not null default current_timestamp,
    primary key (id)
);

drop table if exists movie_genre;
create table movie_genre
(
    id       bigserial,
    movie_id bigint not null,
    genre_id bigint not null,
    primary key (id, movie_id, genre_id),
    foreign key (movie_id) references movie (id) on update cascade on delete cascade,
    foreign key (genre_id) references genre (id) on update cascade on delete cascade
);

drop table if exists movie_language;
create table movie_language
(
    id          bigserial,
    movie_id    bigint not null,
    language_id bigint not null,
    primary key (id, movie_id, language_id),
    foreign key (movie_id) references movie (id) on update cascade on delete cascade,
    foreign key (language_id) references language (id) on update cascade on delete cascade
);

drop table if exists movie_country;
create table movie_country
(
    id         bigserial,
    movie_id   bigint not null,
    country_id bigint not null,
    primary key (id, movie_id, country_id),
    foreign key (movie_id) references movie (id) on update cascade on delete cascade,
    foreign key (country_id) references country (id) on update cascade on delete cascade
);

drop table if exists filee;
create table file
(
    id           bigserial,
    path         varchar,
    file_name    varchar,
    extension    varchar,
    content_type varchar,
    created_at   timestamp not null default current_timestamp,
    updated_at   timestamp not null default current_timestamp,
    primary key (id)
);

drop table if exists user_file;
create table user_file
(
    id      bigserial,
    user_id bigint not null,
    file_id bigint not null,
    general boolean default false,
    primary key (id, user_id, file_id),
    foreign key (user_id) references "user" (id) on update cascade on delete cascade,
    foreign key (file_id) references file (id) on update cascade on delete cascade
);

drop table if exists person_file;
create table person_file
(
    id        bigserial,
    person_id bigint not null,
    file_id   bigint not null,
    general   boolean default false,
    primary key (id, person_id, file_id),
    foreign key (person_id) references person (id) on update cascade on delete cascade,
    foreign key (file_id) references file (id) on update cascade on delete cascade
);

drop table if exists movie_file;
create table movie_file
(
    id       bigserial,
    movie_id bigint not null,
    file_id  bigint not null,
    general  boolean default false,
    primary key (id, movie_id, file_id),
    foreign key (movie_id) references movie (id) on update cascade on delete cascade,
    foreign key (file_id) references file (id) on update cascade on delete cascade
);

