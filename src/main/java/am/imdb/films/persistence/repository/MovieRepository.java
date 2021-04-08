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

    @Query("select m from MovieEntity m where " +
            "((:imdbId is null) or (:imdbId like concat('%', :imdbId, '%'))) and " +
            "((:title is null) or (:title like concat('%', :title, '%'))) and " +
            "((m.year is null) or (m.year BETWEEN :yearMin AND :yearMax)) and " +
            "((m.duration is null) or (m.duration BETWEEN :durationMin AND :durationMax)) and " +
            "((:productionCompany is null) or (:productionCompany like concat('%', :productionCompany, '%'))) and " +
            "((:description is null) or (:description like concat('%', :description, '%'))) and " +
            "((m.avgVote is null) or (m.avgVote BETWEEN :avgVoteMin AND :avgVoteMax)) and " +
            "((m.votes is null) or (m.votes BETWEEN :votesMin AND :votesMax)) and " +
//            "((NULLIF(regexp_replace(m.budget, '\\D','','g'), '')::double precision) BETWEEN :budgetMin AND :budgetMax) and " +
//            "((NULLIF(regexp_replace(m.usaGrossIncome, '\\D','','g'), '')::double precision) BETWEEN :usaGrossIncomeMin AND :usaGrossIncomeMax) and " +
//            "((NULLIF(regexp_replace(m.worldWideGrossIncome, '\\D','','g'), '')::double precision) BETWEEN :worldWideGrossIncomeMin AND :worldWideGrossIncomeMax) and " +
            "((m.metasCore is null) or (m.metasCore BETWEEN :metasCoreMin AND :metasCoreMax)) and " +
            "((m.reviewsFromUsers is null) or (m.reviewsFromUsers BETWEEN :reviewsFromUsersMin AND :reviewsFromUsersMax)) and " +
            "((m.reviewsFromCritics is null) or (m.reviewsFromCritics BETWEEN :reviewsFromCriticsMin AND :reviewsFromCriticsMax))")
    Page<MovieEntity> findAllWithPagination(
            @Param("imdbId") String imdbId,
            @Param("title") String title,
            @Param("yearMin") Integer yearMin,
            @Param("yearMax") Integer yearMax,
            @Param("durationMin") Integer durationMin,
            @Param("durationMax") Integer durationMax,
            @Param("productionCompany") String productionCompany,
            @Param("description") String description,
            @Param("avgVoteMin") Double avgVoteMin,
            @Param("avgVoteMax") Double avgVoteMax,
            @Param("votesMin") Integer votesMin,
            @Param("votesMax") Integer votesMax,
//            @Param("budgetMin") Double budgetMin,
//            @Param("budgetMax") Double budgetMax,
//            @Param("usaGrossIncomeMin") Double usaGrossIncomeMin,
//            @Param("usaGrossIncomeMax") Double usaGrossIncomeMax,
//            @Param("worldWideGrossIncomeMin") Double worldWideGrossIncomeMin,
//            @Param("worldWideGrossIncomeMax") Double worldWideGrossIncomeMax,
            @Param("metasCoreMin") Double metasCoreMin,
            @Param("metasCoreMax") Double metasCoreMax,
            @Param("reviewsFromUsersMin") Double reviewsFromUsersMin,
            @Param("reviewsFromUsersMax") Double reviewsFromUsersMax,
            @Param("reviewsFromCriticsMin") Double reviewsFromCriticsMin,
            @Param("reviewsFromCriticsMax") Double reviewsFromCriticsMax,
            Pageable composePageRequest);


    @Query(value = "SELECT imdb_id FROM movie", nativeQuery = true)
    Set<String> findAllMoviesImdbId();

    @Query("select m from MovieEntity m left join m.listOfUserFavorite louf where louf.user.id = :userId")
    Page<MovieEntity> findUserFavorites(@Param("userId") Long userId, Pageable pageable);
}

