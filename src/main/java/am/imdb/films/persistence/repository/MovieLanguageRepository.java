package am.imdb.films.persistence.repository;

import am.imdb.films.persistence.entity.relation.MovieGenreEntity;
import am.imdb.films.persistence.entity.relation.MovieLanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieLanguageRepository extends JpaRepository<MovieLanguageEntity, Long> {

}
