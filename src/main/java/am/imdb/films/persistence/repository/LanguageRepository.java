package am.imdb.films.persistence.repository;


import am.imdb.films.persistence.entity.CountryEntity;
import am.imdb.films.persistence.entity.LanguageEntity;
import am.imdb.films.service.dto.LanguageDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface LanguageRepository extends JpaRepository<LanguageEntity, Long> {

    LanguageEntity findByName(String name);
    List<LanguageEntity> findByNameIn(Set<String> names);
}

