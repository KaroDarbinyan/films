package am.imdb.films.persistence.repository;

import am.imdb.films.persistence.entity.relation.UserFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFileRepository extends JpaRepository<UserFileEntity, Long> {

}
