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
            "((:imdbId is null) or (:imdbId like concat('%', :imdbId, '%'))) and " +
            "((:name is null) or (:name like concat('%', :name, '%'))) and " +
            "((:birthName is null) or (:birthName like concat('%', :birthName, '%'))) and " +
            "((:bio is null) or (:bio like concat('%', :bio, '%'))) and " +
            "((:placeOfBirth is null) or (:placeOfBirth like concat('%', :placeOfBirth, '%'))) and " +
            "((:deathDetails is null) or (:deathDetails like concat('%', :deathDetails, '%'))) and " +
            "((:placeOfDeath is null) or (:placeOfDeath like concat('%', :placeOfDeath, '%'))) and " +
            "((p.spouses is null) or (p.spouses BETWEEN :spousesMin AND :spousesMax)) and " +
            "((p.divorces is null) or (p.divorces BETWEEN :divorcesMin AND :divorcesMax)) and " +
            "((p.spousesWithChildren is null) or (p.spousesWithChildren BETWEEN :spousesWithChildrenMin AND :spousesWithChildrenMax)) and " +
            "((p.children is null) or (p.children BETWEEN :childrenMin AND :childrenMax))")
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

