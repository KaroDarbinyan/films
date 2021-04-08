package am.imdb.films.persistence.repository;

import am.imdb.films.persistence.entity.UserEntity;
import am.imdb.films.persistence.entity.relation.UserFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserFileRepository extends JpaRepository<UserFileEntity, Long> {

//    List<UserFileEntity> findAllByUser(UserEntity userEntity);
}
