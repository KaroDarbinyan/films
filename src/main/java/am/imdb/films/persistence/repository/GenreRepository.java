package am.imdb.films.persistence.repository;


import am.imdb.films.persistence.entity.CountryEntity;
import am.imdb.films.persistence.entity.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Long> {


    GenreEntity findByName(String name);
    List<GenreEntity> findByNameIn(Set<String> names);
}

