package am.imdb.films.persistence.repository;

import am.imdb.films.persistence.entity.relation.MovieFileEntity;
import am.imdb.films.persistence.entity.relation.MovieGenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieGenreRepository extends JpaRepository<MovieGenreEntity, Long> {

}
