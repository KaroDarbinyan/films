package am.imdb.films.persistence.repository;


import am.imdb.films.persistence.entity.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface LanguageRepository extends JpaRepository<LanguageEntity, Long> {

    Optional<LanguageEntity> findByName(String name);

    List<LanguageEntity> findByNameIn(Collection<String> names);
}

