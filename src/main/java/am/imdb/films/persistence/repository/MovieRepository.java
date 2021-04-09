package am.imdb.films.persistence.repository;


import am.imdb.films.persistence.entity.MovieEntity;
import am.imdb.films.service.model.resultset.MapEntityKeys;
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
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    Optional<MovieEntity> findByImdbId(String imdbId);

    List<MovieEntity> findByImdbIdIn(Collection<String> imdbIds);

    @Query("SELECT new am.imdb.films.service.model.resultset.MapEntityKeys(m.id, m.imdbId) FROM MovieEntity m")
    List<MapEntityKeys<Long, String>> findAllMovieImdbIdsAndIds();

    @Query("select m from MovieEntity m " +
            "left join m.listOfMovieGenre mg " +
            "left join m.listOfMovieLanguage ml " +
            "left join m.listOfMovieCountry mc " +
//            "left join m.rating mr" +
            " where " +
            "((:imdbId is null) or (m.imdbId like concat('%', :imdbId, '%'))) and " +
            "((:title is null) or (m.title like concat('%', :title, '%'))) and " +
            "((:yearMin is null) or (m.year  >= :yearMin)) and " +
            "((:yearMax is null) or (m.year  >= :yearMax)) and " +
            "((:productionCompany is null) or (m.productionCompany like concat('%', :productionCompany, '%'))) and " +
//            "((:budgetMin is null) or (m.budget >= :budgetMin)) and " +
//            "((:budgetMax is null) or (m.budget <= :budgetMax)) and " +
//            "((NULLIF(regexp_replace(m.budget, '\\D','','g'), '')::double precision) BETWEEN :budgetMin AND :budgetMax) and " +
            "((:genre is null) or (mg.genre.name = :genre)) and " +
            "((:language is null) or (ml.language.name = :language)) and " +
            "((:country is null) or (mc.country.name = :country))")
    Page<MovieEntity> findAllWithPagination(
            @Param("imdbId") String imdbId,
            @Param("title") String title,
            @Param("yearMin") Integer yearMin,
            @Param("yearMax") Integer yearMax,
            @Param("productionCompany") String productionCompany,
//            @Param("budgetMin") Double budgetMin,
//            @Param("budgetMax") Double budgetMax,
            @Param("genre") String genre,
            @Param("language") String language,
            @Param("country") String country,
            Pageable composePageRequest);


    @Query(value = "SELECT imdb_id FROM movie", nativeQuery = true)
    Set<String> findAllMoviesImdbId();

    @Query("select m from MovieEntity m left join m.listOfUserFavorite louf where louf.user.id = :userId")
    Page<MovieEntity> findUserFavorites(@Param("userId") Long userId, Pageable pageable);
}

