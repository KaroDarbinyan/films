package am.imdb.films.persistence.repository;


import am.imdb.films.persistence.entity.MovieEntity;
import am.imdb.films.service.dto.base.BaseMovieDto;
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
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    MovieEntity findByImdbId(String imdbId);

    List<MovieEntity> findByImdbIdIn(List<String> imdbIds);

    @Query("SELECT new am.imdb.films.service.model.map.MapEntityKeys(m.id, m.imdbId) FROM MovieEntity m")
    List<MapEntityKeys<Long, String>> findAllMovieImdbIdsAndIds();

    @Query("SELECT new am.imdb.films.service.dto.base.BaseMovieDto(m.id," +
            "m.originalTitle," +
            "m.year," +
            "m.datePublished," +
            "m.productionCompany," +
            "m.budget) FROM MovieEntity m")
    Page<BaseMovieDto> findAllWithPagination(Pageable composePageRequest);

    @Query("SELECT new am.imdb.films.service.dto.base.BaseMovieDto(p.id,p.name,p.bio,p.birthDetails, p.dateOfBirth, p.dateOfDeath)" +
            " FROM PersonEntity p where p.id = :id")
    Page<BasePersonDto> findAllPersonsWithPagination(Long id, Pageable composePageRequest);

    @Query(value = "SELECT imdb_id FROM movie", nativeQuery = true)
    Set<String> findAllMoviesImdbId();
}

