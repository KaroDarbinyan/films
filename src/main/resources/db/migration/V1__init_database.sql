drop table if exists "user";
create table "user"
(
    id            bigserial,
    username      varchar   not null,
    first_name    varchar   not null,
    last_name     varchar   not null,
    password_hash varchar   not null,
    status        varchar            default null,
    created_at    timestamp not null default current_timestamp,
    updated_at    timestamp not null default current_timestamp,
    primary key (id),
    unique (username)
);

drop table if exists movie;
create table movie
(
    id                     bigserial,
    imdb_id                varchar   not null,
    title                  varchar            default null,
    release_date           int4               default null,
    duration               int                default 0,
    production_company     varchar            default null,
    description            text               default null,
    avg_vote               double precision   default 0.0,
    votes                  int                default 0,
    budget                 varchar            default null,
    usa_gross_income       varchar            default null,
    worldwide_gross_income varchar            default null,
    metas_core             double precision   default 0.0,
    reviews_from_users     double precision   default 0.0,
    reviews_from_critics   double precision   default 0.0,
    parse_error            boolean            default false,
    parse_success          boolean            default false,
    created_at             timestamp not null default current_timestamp,
    updated_at             timestamp not null default current_timestamp,
    primary key (id),
    unique (imdb_id)
);

drop table if exists person;
create table person
(
    id                    bigserial,
    imdb_id               varchar   not null,
    name                  varchar            default null,
    height                int                default 0,
    bio                   text               default null,
    birth_details         varchar            default null,
    birth_date            int4               default null,
    place_of_birth        varchar            default null,
    death_details         varchar            default null,
    death_date            int4               default null,
    place_of_death        varchar            default null,
    reason_of_death       varchar            default null,
    spouses_string        text               default null,
    spouses               int                default 0,
    divorces              int                default 0,
    spouses_with_children int                default 0,
    children              int                default 0,
    parse_error           boolean            default false,
    parse_success         boolean            default false,
    created_at            timestamp not null default current_timestamp,
    updated_at            timestamp not null default current_timestamp,
    primary key (id),
    unique (imdb_id)
);

drop table if exists movie_person;
create table movie_person
(
    id             bigserial,
    movie_imdb_id  varchar not null,
    ordering       int4    not null,
    person_imdb_id varchar not null,
    category       varchar default null,
    characters     varchar default null,
    primary key (id),
    foreign key (movie_imdb_id) references movie (imdb_id) on update cascade on delete cascade,
    foreign key (person_imdb_id) references person (imdb_id) on update cascade on delete cascade
);

drop table if exists genre;
create table genre
(
    id   bigserial,
    name varchar not null,
    primary key (id),
    unique (name)
);

drop table if exists language;
create table language
(
    id   bigserial,
    name varchar not null,
    primary key (id),
    unique (name)
);

drop table if exists movie_rating;
create table movie_rating
(
    id             bigserial,
    movie_imdb_id  varchar   not null,
    average_rating double precision            default null,
    num_votes      int            default null,
    created_at     timestamp not null default current_timestamp,
    updated_at     timestamp not null default current_timestamp,
    foreign key (movie_imdb_id) references movie (imdb_id) on update cascade on delete cascade,
    primary key (id),
    unique (movie_imdb_id)
);

drop table if exists country;
create table country
(
    id   bigserial,
    name varchar not null,
    primary key (id),
    unique (name)
);

drop table if exists movie_genre;
create table movie_genre
(
    id       bigserial,
    movie_id bigint not null,
    genre_id bigint not null,
    primary key (id),
    unique (movie_id, genre_id),
    foreign key (movie_id) references movie (id) on update cascade on delete cascade,
    foreign key (genre_id) references genre (id) on update cascade on delete cascade
);

drop table if exists movie_language;
create table movie_language
(
    id          bigserial,
    movie_id    bigint not null,
    language_id bigint not null,
    primary key (id),
    unique (movie_id, language_id),
    foreign key (movie_id) references movie (id) on update cascade on delete cascade,
    foreign key (language_id) references language (id) on update cascade on delete cascade
);

drop table if exists movie_country;
create table movie_country
(
    id         bigserial,
    movie_id   bigint not null,
    country_id bigint not null,
    primary key (id),
    unique (movie_id, country_id),
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
    primary key (id)
);

drop table if exists user_file;
create table user_file
(
    id      bigserial,
    user_id bigint not null,
    file_id bigint not null,
    general boolean default false,
    primary key (id),
    unique (user_id, file_id),
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
    primary key (id),
    unique (person_id, file_id),
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
    primary key (id),
    unique (movie_id, file_id),
    foreign key (movie_id) references movie (id) on update cascade on delete cascade,
    foreign key (file_id) references file (id) on update cascade on delete cascade
);

