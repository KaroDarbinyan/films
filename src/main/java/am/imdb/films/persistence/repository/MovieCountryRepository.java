package am.imdb.films.persistence.repository;

import am.imdb.films.persistence.entity.relation.MovieCountryEntity;
import am.imdb.films.persistence.entity.relation.MovieGenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieCountryRepository extends JpaRepository<MovieCountryEntity, Long> {

}
