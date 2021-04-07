package am.imdb.films.persistence.repository;


import am.imdb.films.persistence.entity.CountryEntity;
import am.imdb.films.persistence.entity.GenreEntity;
import am.imdb.films.service.dto.CountryDto;
import am.imdb.films.service.dto.GenreDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Long> {

    Optional<CountryEntity> findByName(String name);

    List<CountryEntity> findByNameIn(Collection<String> names);
}

