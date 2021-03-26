drop table if exists "user";
create table "user"
(
    id         bigserial,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    email      varchar(255) not null,
    password   varchar(255) not null,
    status     varchar(255) not null,
    created_at timestamp    not null default current_timestamp,
    updated_at timestamp    not null default current_timestamp,
    primary key (id),
    unique (email)
);

drop table if exists movie;
create table movie
(
    id                     bigserial,
    imdb_id                varchar(255) unique not null,
    title                  varchar(255)                 default null,
    original_title         varchar(255)                 default null,
    year                   varchar(255)                 default null,
    date_published         varchar(255)                 default null,
    duration               varchar(255)                 default null,
    director               varchar(255)                 default null,
    writer                 varchar(255)                 default null,
    production_company     varchar(255)                 default null,
    actors                 text                         default null,
    description            text                         default null,
    avg_vote               varchar(255)                 default null,
    budget                 varchar(255)                 default null,
    usa_gross_income       varchar(255)                 default null,
    worldwide_gross_income varchar(255)                 default null,
    metas_core             varchar(255)                 default null,
    reviews_from_users     varchar(255)                 default null,
    reviews_from_critics   varchar(255)                 default null,
    created_at             timestamp           not null default current_timestamp,
    updated_at             timestamp           not null default current_timestamp,
    primary key (id)
);

drop table if exists person;
create table person
(
    id                    bigserial,
    imdb_id               varchar(239) unique not null,
    name                  varchar(240)                 default null,
    birth_name            varchar(241)                 default null,
    height                varchar(242)                 default null,
    bio                   text                         default null,
    birth_details         varchar(244)                 default null,
    date_of_birth         varchar(245)                 default null,
    place_of_birth        varchar(246)                 default null,
    death_details         varchar(247)                 default null,
    date_of_death         varchar(248)                 default null,
    place_of_death        varchar(249)                 default null,
    reason_of_death       varchar(250)                 default null,
    spouses_string        text                         default null,
    spouses               varchar(252)                 default null,
    divorces              varchar(253)                 default null,
    spouses_with_children varchar(254)                 default null,
    children              varchar(255)                 default null,
    created_at            timestamp           not null default current_timestamp,
    updated_at            timestamp           not null default current_timestamp,
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
    id   bigserial not null primary key,
    name varchar(255) unique not null
);

drop table if exists lanfuage;
create table language
(
    id   bigserial,
    name varchar(255) unique not null,
    primary key (id)
);

drop table if exists rating;
create table rating
(
    id             bigserial,
    movie_id       bigint unique not null,
    average_rating varchar(255) default null,
    num_votes      varchar(255) default null,
    foreign key (movie_id) references movie (id) on update cascade on delete cascade,
    primary key (id)
);

drop table if exists country;
create table country
(
    id   bigserial,
    name varchar(255) unique not null,
    primary key (id)
);

drop table if exists movie_genre;
create table movie_genre
(
    movie_id bigint not null,
    genre_id bigint not null,
    primary key (movie_id, genre_id),
    foreign key (movie_id) references movie (id) on update cascade on delete cascade,
    foreign key (genre_id) references genre (id) on update cascade on delete cascade
);

drop table if exists movie_language;
create table movie_language
(
    movie_id    bigint not null,
    language_id bigint not null,
    primary key (movie_id, language_id),
    foreign key (movie_id) references movie (id) on update cascade on delete cascade,
    foreign key (language_id) references language (id) on update cascade on delete cascade
);

drop table if exists movie_country;
create table movie_country
(
    movie_id   bigint not null,
    country_id bigint not null,
    primary key (movie_id, country_id),
    foreign key (movie_id) references movie (id) on update cascade on delete cascade,
    foreign key (country_id) references country (id) on update cascade on delete cascade
);

drop table if exists "storage";
create table "storage"
(
    id           bigserial,
    path         varchar(255),
    file_name    varchar(255),
    extension    varchar(255),
    content_type varchar(255),
    general      boolean            default false,
    user_id      bigint,
    person_id    bigint,
    movie_id     bigint,
    created_at   timestamp not null default current_timestamp,
    foreign key (user_id) references "user" (id) on update cascade on delete cascade,
    foreign key (person_id) references person (id) on update cascade on delete cascade,
    foreign key (movie_id) references movie (id) on update cascade on delete cascade,
    primary key (id)
)

