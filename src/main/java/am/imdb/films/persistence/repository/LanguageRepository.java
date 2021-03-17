package am.imdb.films.persistence.repository;


import am.imdb.films.persistence.entity.LanguageEntity;
import am.imdb.films.service.dto.LanguageDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<LanguageEntity, Long> {

    LanguageEntity findLanguageByName(String name);
}

