package am.imdb.films.persistence.repository;


import am.imdb.films.persistence.entity.PersonEntity;
import am.imdb.films.service.dto.base.BasePersonDto;
import am.imdb.films.service.model.map.MapEntityKeys;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    @Query("SELECT new am.imdb.films.service.model.map.MapEntityKeys(p.id, p.imdbId) FROM PersonEntity p")
    List<MapEntityKeys<Long, String>> findAllPersonImdbIdsAndIds();

    @Query("SELECT p.imdbId FROM PersonEntity p")
    Set<String> findAllPersonsImdbId();

    @Query("SELECT new am.imdb.films.service.dto.base.BasePersonDto(p.id,p.name,p.bio,p.birthDetails, p.dateOfBirth, p.dateOfDeath) FROM PersonEntity p")
    Page<BasePersonDto> findAllWithPagination(Pageable composePageRequest);

}

