package am.imdb.films.persistence.repository;


import am.imdb.films.persistence.entity.CountryEntity;
import am.imdb.films.persistence.entity.GenreEntity;
import am.imdb.films.service.dto.CountryDto;
import am.imdb.films.service.dto.GenreDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Long> {

    CountryEntity findByName(String name);

    List<CountryEntity> findByNameIn(Set<String> names);

    @Query("SELECT new am.imdb.films.service.dto.CountryDto(c.id, c.name) FROM CountryEntity c")
    Page<CountryDto> findAllWithPagination(Pageable composePageRequest);
}

