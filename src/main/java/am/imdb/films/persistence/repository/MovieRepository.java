package am.imdb.films.persistence.repository;


import am.imdb.films.persistence.entity.MovieEntity;
import am.imdb.films.service.criteria.MovieSearchCriteria;
import am.imdb.films.service.model.resultset.MapEntityKeys;
import am.imdb.films.service.model.wrapper.MovieWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Query("select new am.imdb.films.service.model.wrapper.MovieWrapper(m.id,m.imdbId,m.title,m.releaseDate," +
            "m.duration,m.productionCompany,m.description,m.avgVote,m.votes,m.budget,m.usaGrossIncome,m.worldWideGrossIncome," +
            "m.metasCore,m.reviewsFromUsers,m.reviewsFromCritics) from MovieEntity m " +
            "left join m.listOfMoviePerson lomp left join lomp.person lp " +
            "left join m.listOfMovieLanguage loml left join loml.language ll " +
            "left join m.listOfMovieGenre lomg left join lomg.genre lg where " +
            "((:#{#criteria.imdbId} = '') or (m.imdbId like concat('%', :#{#criteria.imdbId}, '%'))) and " +
            "((:#{#criteria.title} = '') or (m.title like concat('%', :#{#criteria.title}, '%'))) and " +
//            "((:#{#criteria.yearMin} is null) or (m.year  >= :#{#criteria.yearMin})) and " +
//            "((:#{#criteria.yearMax} is null) or (m.year  >= :#{#criteria.yearMax})) and " +
            "((:#{#criteria.productionCompany} = '') or (m.productionCompany like concat('%', :#{#criteria.productionCompany}, '%'))) and " +
            "((:#{#criteria.genres.size()} = 0) or (lg.name in (:#{#criteria.genres}))) and " +
            "((:#{#criteria.actors.size()} = 0) or ((lomp.category in ('actor', 'actress')) and (lp.name in (:#{#criteria.actors})))) and " +
            "((:#{#criteria.producers.size()} = 0) or ((lomp.category = 'producer') and (lp.name in (:#{#criteria.producers})))) and " +
            "(cast(coalesce(nullif(regexp_replace(m.budget, '[^0-9]', '', 'g'), ''), '0') double) between :#{#criteria.budgetMin} and :#{#criteria.budgetMax}) group by m.id")
    Page<MovieWrapper> findAllWithPagination(@Param("criteria") MovieSearchCriteria criteria, Pageable pageable);

    @Query("SELECT new am.imdb.films.service.model.resultset.MapEntityKeys(m.id, m.imdbId) FROM MovieEntity m")
    List<MapEntityKeys<Long, String>> findAllMovieImdbIdsAndIds();

    @Query("SELECT m.imdbId FROM MovieEntity m")
    Set<String> findAllMoviesImdbIds();

    @Query("select m from MovieEntity m left join m.listOfUserFavorite louf where louf.user.id = :userId")
    Page<MovieEntity> findUserFavorites(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT m FROM MovieEntity m where m.parseError = :error and m.parsSuccess = :success")
    Page<MovieEntity> findPersonByParseErrorAndParseSuccess(boolean error, boolean success, Pageable pageable);
}

