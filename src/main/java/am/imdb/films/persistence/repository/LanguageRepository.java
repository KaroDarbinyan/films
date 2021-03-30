package am.imdb.films.persistence.repository;


import am.imdb.films.persistence.entity.LanguageEntity;
import am.imdb.films.service.dto.LanguageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface LanguageRepository extends JpaRepository<LanguageEntity, Long> {

    @Query("SELECT new am.imdb.films.service.dto.LanguageDto(l.id, l.name) FROM LanguageEntity l")
    Page<LanguageDto> findAllWithPagination(Pageable composePageRequest);
}

