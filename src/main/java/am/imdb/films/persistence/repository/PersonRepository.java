package am.imdb.films.persistence.repository;


import am.imdb.films.persistence.entity.PersonEntity;
import am.imdb.films.service.model.resultset.MapEntityKeys;
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

    @Query("select p from PersonEntity p where " +
            "((:imdbId is null) or (p.imdbId like concat('%', :imdbId, '%'))) and " +
            "((:name is null) or (p.name like concat('%', :name, '%'))) and " +
            "((:birthName is null) or (p.birthName like concat('%', :birthName, '%'))) and " +
            "((:bio is null) or (p.bio like concat('%', :bio, '%'))) and " +
            "((:placeOfBirth is null) or (p.placeOfBirth like concat('%', :placeOfBirth, '%'))) and " +
            "((:deathDetails is null) or (p.deathDetails like concat('%', :deathDetails, '%'))) and " +
            "((:placeOfDeath is null) or (p.placeOfDeath like concat('%', :placeOfDeath, '%'))) and " +
            "((:spousesMin is null) or (p.spouses >= :spousesMin)) and " +
            "((:spousesMax is null) or (p.spouses <= :spousesMax)) and " +
            "((:divorcesMin is null) or (p.divorces >= :divorcesMin)) and " +
            "((:divorcesMax is null) or (p.divorces >= :divorcesMax)) and " +
            "((:spousesWithChildrenMin is null) or (p.spousesWithChildren  >= :spousesWithChildrenMin)) and " +
            "((:spousesWithChildrenMax is null) or (p.spousesWithChildren  <= :spousesWithChildrenMax)) and " +
            "((:childrenMin is null) or (p.children  >= :childrenMin)) and " +
            "((:childrenMax is null) or (p.children <= :childrenMax))")
    Page<PersonEntity> findAllWithPagination(
            @Param("imdbId") String imdbId,
            @Param("name") String name,
            @Param("birthName") String birthName,
            @Param("bio") String bio,
            @Param("placeOfBirth") String placeOfBirth,
            @Param("deathDetails") String deathDetails,
            @Param("placeOfDeath") String placeOfDeath,
            @Param("spousesMin") Integer spousesMin,
            @Param("spousesMax") Integer spousesMax,
            @Param("divorcesMin") Integer divorcesMin,
            @Param("divorcesMax") Integer divorcesMax,
            @Param("spousesWithChildrenMin") Integer spousesWithChildrenMin,
            @Param("spousesWithChildrenMax") Integer spousesWithChildrenMax,
            @Param("childrenMin") Integer childrenMin,
            @Param("childrenMax") Integer childrenMax,
            Pageable composePageRequest);
}

