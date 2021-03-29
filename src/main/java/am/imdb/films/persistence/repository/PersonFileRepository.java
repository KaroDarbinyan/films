package am.imdb.films.persistence.repository;

import am.imdb.films.persistence.entity.relation.PersonFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonFileRepository extends JpaRepository<PersonFileEntity, Long> {

}
