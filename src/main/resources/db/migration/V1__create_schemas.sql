DROP TABLE IF EXISTS `person`;
CREATE TABLE `person`
(
    `id`          int(11)                                                                                                        NOT NULL AUTO_INCREMENT,
    `k_p_id`      int(11)                                                                                                        NOT NULL UNIQUE, #personId
    `web_url`     varchar(255)                                                                                                   NOT NULL,
    `name_ru`     varchar(255)                                                                                                   NOT NULL,
    `name_en`     varchar(255)                                                                                                   NOT NULL,
    `sex`         enum ('MALE', 'FEMALE', 'UNKNOWN')                                                                             NOT NULL,
    `poster_url`  varchar(255) DEFAULT NULL,
    `growth`      int(11)      DEFAULT NULL,
    `birthday`    date                                                                                                           NOT NULL,
    `death`       varchar(255) DEFAULT NULL,
    `age`         int(11)                                                                                                        NOT NULL,
    `birth_place` varchar(255) DEFAULT NULL,
    `death_place` varchar(255) DEFAULT NULL,
    `has_awards`  int(11)      DEFAULT NULL,
    `profession`  enum ('DIRECTOR', 'ACTOR', 'PRODUCER', 'VOICE_DIRECTOR', 'WRITER', 'OPERATOR', 'COMPOSER', 'DESIGN', 'EDITOR') NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `profession`;
CREATE TABLE `profession`
(
    `id`      int(11)      NOT NULL AUTO_INCREMENT,
    `name_en` varchar(255) not null,
    `name_ru` varchar(255) not null,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `person_profession`;
CREATE TABLE `person_profession`
(
    `person_id`     int(11) NOT NULL,
    `profession_id` int(11) NOT NULL,
    FOREIGN KEY (person_id) REFERENCES person (id) ON DELETE CASCADE,
    FOREIGN KEY (profession_id) REFERENCES profession (id) ON DELETE CASCADE,
    PRIMARY KEY (person_id, profession_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `film`;
CREATE TABLE `film`
(
    `id`                     int(11)       NOT NULL AUTO_INCREMENT,
    `k_p_id`                 int(11)       NOT NULL UNIQUE, #filmId
    `name_ru`                varchar(255)  NOT NULL,
    `name_en`                varchar(255)  NOT NULL,
    `web_url`                varchar(255)  NOT NULL,
    `poster_url`             varchar(255)  NOT NULL,
    `poster_url_preview`     varchar(255)  NOT NULL,
    `year`                   varchar(255) DEFAULT NULL,
    `film_length`            varchar(255) DEFAULT NULL,
    `slogan`                 varchar(255) DEFAULT NULL,
    `description`            text         DEFAULT NULL,
    `type`                   ENUM ('FILM') NOT NULL,
    `rating_m_p_a_a`         varchar(255) DEFAULT NULL,
    `rating_age_limits`      int(11)      DEFAULT NULL,
    `premiere_ru`            DATE         DEFAULT NULL,
    `distributors`           varchar(255) DEFAULT NULL,
    `premiere_world`         DATE         DEFAULT NULL,
    `premiere_digital`       DATE         DEFAULT NULL,
    `premiere_world_country` varchar(255) DEFAULT NULL,
    `premiere_dvd`           varchar(255) DEFAULT NULL,
    `premiere_blu_ray`       varchar(255) DEFAULT NULL,
    `distributor_release`    varchar(255) DEFAULT NULL,
    `imdb_id`                int(11)      DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `genre`;
CREATE TABLE `genre`
(
    `id`   int(11)             NOT NULL AUTO_INCREMENT,
    `name` varchar(255) unique not null,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `film_genre`;
CREATE TABLE `film_genre`
(
    `film_id`  int(11) NOT NULL,
    `genre_id` int(11) NOT NULL,
    FOREIGN KEY (film_id) REFERENCES film (id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES genre (id) ON DELETE CASCADE,
    PRIMARY KEY (film_id, genre_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `fact`;
CREATE TABLE `fact`
(
    `id` int(11)      NOT NULL AUTO_INCREMENT,
    `text` varchar(255) not null,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `person_fact`;
CREATE TABLE `person_fact`
(
    `person_id` int(11) NOT NULL,
    `fact_id`   int(11) NOT NULL,
    FOREIGN KEY (person_id) REFERENCES person (id) ON DELETE CASCADE,
    FOREIGN KEY (fact_id) REFERENCES fact (id) ON DELETE CASCADE,
    PRIMARY KEY (person_id, fact_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `film_fact`;
CREATE TABLE `film_fact`
(
    `film_id` int(11) NOT NULL,
    `fact_id` int(11) NOT NULL,
    FOREIGN KEY (film_id) REFERENCES film (id) ON DELETE CASCADE,
    FOREIGN KEY (fact_id) REFERENCES fact (id) ON DELETE CASCADE,
    PRIMARY KEY (film_id, fact_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


/*DROP TABLE IF EXISTS `cities`;
CREATE TABLE `cities`
(
    `city_id`    int(11)    NOT NULL,
    `country_id` int(11)    NOT NULL,
    `important`  tinyint(1) NOT NULL,
    `region_id`  int(11)      DEFAULT NULL,
    `title_ru`   varchar(150) DEFAULT NULL,
    `area_ru`    varchar(150) DEFAULT NULL,
    `region_ru`  varchar(150) DEFAULT NULL,
    `title_ua`   varchar(150) DEFAULT NULL,
    `area_ua`    varchar(150) DEFAULT NULL,
    `region_ua`  varchar(150) DEFAULT NULL,
    `title_be`   varchar(150) DEFAULT NULL,
    `area_be`    varchar(150) DEFAULT NULL,
    `region_be`  varchar(150) DEFAULT NULL,
    `title_en`   varchar(150) DEFAULT NULL,
    `area_en`    varchar(150) DEFAULT NULL,
    `region_en`  varchar(150) DEFAULT NULL,
    `title_es`   varchar(150) DEFAULT NULL,
    `area_es`    varchar(150) DEFAULT NULL,
    `region_es`  varchar(150) DEFAULT NULL,
    `title_pt`   varchar(150) DEFAULT NULL,
    `area_pt`    varchar(150) DEFAULT NULL,
    `region_pt`  varchar(150) DEFAULT NULL,
    `title_de`   varchar(150) DEFAULT NULL,
    `area_de`    varchar(150) DEFAULT NULL,
    `region_de`  varchar(150) DEFAULT NULL,
    `title_fr`   varchar(150) DEFAULT NULL,
    `area_fr`    varchar(150) DEFAULT NULL,
    `region_fr`  varchar(150) DEFAULT NULL,
    `title_it`   varchar(150) DEFAULT NULL,
    `area_it`    varchar(150) DEFAULT NULL,
    `region_it`  varchar(150) DEFAULT NULL,
    `title_pl`   varchar(150) DEFAULT NULL,
    `area_pl`    varchar(150) DEFAULT NULL,
    `region_pl`  varchar(150) DEFAULT NULL,
    `title_ja`   varchar(150) DEFAULT NULL,
    `area_ja`    varchar(150) DEFAULT NULL,
    `region_ja`  varchar(150) DEFAULT NULL,
    `title_lt`   varchar(150) DEFAULT NULL,
    `area_lt`    varchar(150) DEFAULT NULL,
    `region_lt`  varchar(150) DEFAULT NULL,
    `title_lv`   varchar(150) DEFAULT NULL,
    `area_lv`    varchar(150) DEFAULT NULL,
    `region_lv`  varchar(150) DEFAULT NULL,
    `title_cz`   varchar(150) DEFAULT NULL,
    `area_cz`    varchar(150) DEFAULT NULL,
    `region_cz`  varchar(150) DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `regions`;
CREATE TABLE `regions`
(
    `region_id`  int(11) NOT NULL,
    `country_id` int(11) NOT NULL,
    `title_ru`   varchar(150) DEFAULT NULL,
    `title_ua`   varchar(150) DEFAULT NULL,
    `title_be`   varchar(150) DEFAULT NULL,
    `title_en`   varchar(150) DEFAULT NULL,
    `title_es`   varchar(150) DEFAULT NULL,
    `title_pt`   varchar(150) DEFAULT NULL,
    `title_de`   varchar(150) DEFAULT NULL,
    `title_fr`   varchar(150) DEFAULT NULL,
    `title_it`   varchar(150) DEFAULT NULL,
    `title_pl`   varchar(150) DEFAULT NULL,
    `title_ja`   varchar(150) DEFAULT NULL,
    `title_lt`   varchar(150) DEFAULT NULL,
    `title_lv`   varchar(150) DEFAULT NULL,
    `title_cz`   varchar(150) DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `countries`;
CREATE TABLE `countries`
(
    `country_id` int(11) NOT NULL,
    `title_ru`   varchar(60) DEFAULT NULL,
    `title_ua`   varchar(60) DEFAULT NULL,
    `title_be`   varchar(60) DEFAULT NULL,
    `title_en`   varchar(60) DEFAULT NULL,
    `title_es`   varchar(60) DEFAULT NULL,
    `title_pt`   varchar(60) DEFAULT NULL,
    `title_de`   varchar(60) DEFAULT NULL,
    `title_fr`   varchar(60) DEFAULT NULL,
    `title_it`   varchar(60) DEFAULT NULL,
    `title_pl`   varchar(60) DEFAULT NULL,
    `title_ja`   varchar(60) DEFAULT NULL,
    `title_lt`   varchar(60) DEFAULT NULL,
    `title_lv`   varchar(60) DEFAULT NULL,
    `title_cz`   varchar(60) DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;*/
