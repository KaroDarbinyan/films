package am.imdb.films.persistence.repository;


import am.imdb.films.persistence.entity.MovieEntity;
import am.imdb.films.service.criteria.MovieSearchCriteria;
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
            "((:#{#criteria.imdbId} = '') or (m.imdbId like concat('%', :#{#criteria.imdbId}, '%'))) and " +
            "((:#{#criteria.title} = '') or (m.title like concat('%', :#{#criteria.title}, '%'))) and " +
            "((:#{#criteria.yearMin} is null) or (m.year  >= :#{#criteria.yearMin})) and " +
            "((:#{#criteria.yearMax} is null) or (m.year  >= :#{#criteria.yearMax})) and " +
            "((:#{#criteria.productionCompany} = '') or (m.productionCompany like concat('%', :#{#criteria.productionCompany}, '%'))) and " +
//            "((:budgetMin is null) or (m.budget >= :budgetMin)) and " +
//            "((:budgetMax is null) or (m.budget <= :budgetMax)) and " +
//            "((NULLIF(regexp_replace(m.budget, '\\D','','g'), '')::double precision) BETWEEN :budgetMin AND :budgetMax) and " +
            "((:#{#criteria.genre} is null) or (mg.genre.name = :#{#criteria.genre})) and " +
            "((:#{#criteria.language} is null) or (ml.language.name = :#{#criteria.language})) and " +
            "((:#{#criteria.country} is null) or (mc.country.name = :#{#criteria.country}))")
    Page<MovieEntity> findAllWithPagination(@Param("criteria") MovieSearchCriteria criteria, Pageable pageable);


    @Query(value = "SELECT imdb_id FROM movie", nativeQuery = true)
    Set<String> findAllMoviesImdbId();

    @Query("select m from MovieEntity m left join m.listOfUserFavorite louf where louf.user.id = :userId")
    Page<MovieEntity> findUserFavorites(@Param("userId") Long userId, Pageable pageable);
}

