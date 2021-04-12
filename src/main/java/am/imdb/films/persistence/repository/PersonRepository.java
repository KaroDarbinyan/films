package am.imdb.films.persistence.repository;


import am.imdb.films.persistence.entity.PersonEntity;
import am.imdb.films.service.criteria.PersonSearchCriteria;
import am.imdb.films.service.model.resultset.MapEntityKeys;
import am.imdb.films.service.model.wrapper.PersonWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    @Query("SELECT new am.imdb.films.service.model.resultset.MapEntityKeys(p.id, p.imdbId) FROM PersonEntity p")
    List<MapEntityKeys<Long, String>> findAllPersonImdbIdsAndIds();

    @Query("SELECT p.imdbId FROM PersonEntity p")
    Set<String> findAllPersonsImdbId();

    @Query("select new am.imdb.films.service.model.wrapper.PersonWrapper(p.id, p.imdbId, p.name, p.birthName, p.height," +
            " p.bio, p.birthDetails, p.dateOfBirth, p.placeOfBirth, p.deathDetails, p.dateOfDeath, p.placeOfDeath, " +
            "p.reasonOfDeath, p.spousesString, p.spouses, p.divorces, p.spousesWithChildren, p.children) from PersonEntity p where " +
            "((:#{#criteria.imdbId} = '') or (p.imdbId like concat('%', :#{#criteria.imdbId}, '%'))) and " +
            "((:#{#criteria.name} = '') or (p.name like concat('%', :#{#criteria.name}, '%'))) and " +
            "((:#{#criteria.birthName} = '') or (p.birthName like concat('%', :#{#criteria.birthName}, '%'))) and " +
            "((:#{#criteria.bio} = '') or (p.bio like concat('%', :#{#criteria.bio}, '%'))) and " +
            "((:#{#criteria.placeOfBirth} = '') or (p.placeOfBirth like concat('%', :#{#criteria.placeOfBirth}, '%'))) and " +
            "((:#{#criteria.deathDetails} = '') or (p.deathDetails like concat('%', :#{#criteria.deathDetails}, '%'))) and " +
            "((:#{#criteria.placeOfDeath} = '') or (p.placeOfDeath like concat('%', :#{#criteria.placeOfDeath}, '%'))) and " +
            "((:#{#criteria.spousesMin} is null) or (p.spouses >= :#{#criteria.spousesMin})) and " +
            "((:#{#criteria.spousesMax} is null) or (p.spouses <= :#{#criteria.spousesMax})) and " +
            "((:#{#criteria.divorcesMin} is null) or (p.divorces >= :#{#criteria.divorcesMin})) and " +
            "((:#{#criteria.divorcesMax} is null) or (p.divorces >= :#{#criteria.divorcesMax})) and " +
            "((:#{#criteria.spousesWithChildrenMin} is null) or (p.spousesWithChildren  >= :#{#criteria.spousesWithChildrenMin})) and " +
            "((:#{#criteria.spousesWithChildrenMax} is null) or (p.spousesWithChildren  <= :#{#criteria.spousesWithChildrenMax})) and " +
            "((:#{#criteria.childrenMin} is null) or (p.children  >= :#{#criteria.childrenMin})) and " +
            "((:#{#criteria.childrenMax} is null) or (p.children <= :#{#criteria.childrenMax}))")
    Page<PersonWrapper> findAllWithPagination(@Param("criteria") PersonSearchCriteria criteria, Pageable pageable);
}

