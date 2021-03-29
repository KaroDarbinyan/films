package am.imdb.films.persistence.repository;

import am.imdb.films.persistence.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

}
