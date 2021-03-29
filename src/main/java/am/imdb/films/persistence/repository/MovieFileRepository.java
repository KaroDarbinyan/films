package am.imdb.films.persistence.repository;

import am.imdb.films.persistence.entity.relation.MovieFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieFileRepository extends JpaRepository<MovieFileEntity, Long> {

}
