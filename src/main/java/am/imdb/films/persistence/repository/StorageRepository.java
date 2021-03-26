package am.imdb.films.persistence.repository;

import am.imdb.films.persistence.entity.StorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageRepository extends JpaRepository<StorageEntity, Long> {

}
