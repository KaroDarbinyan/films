package am.imdb.films.persistence.repository;


import am.imdb.films.persistence.entity.GenreEntity;
import am.imdb.films.service.dto.GenreDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Long> {

    GenreEntity findByName(String name);

    List<GenreEntity> findByNameIn(Set<String> names);

    @Query("SELECT new am.imdb.films.service.dto.GenreDto(g.id, g.name) FROM GenreEntity g")
    Page<GenreDto> findAllWithPagination(Pageable composePageRequest);
}

