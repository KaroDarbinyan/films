package am.imdb.films.persistence.repository;


import am.imdb.films.persistence.entity.MovieEntity;
import am.imdb.films.service.model.wrapper.MoviesWrapper;
import am.imdb.films.service.model.wrapper.PersonsWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    MovieEntity findByImdbId(String imdbId);

    List<MovieEntity> findByImdbIdIn(List<String> imdbIds);

    @Query(value = "SELECT id, imdb_id FROM movie", nativeQuery = true)
    Map<String, String> findAllMovieIdAndIds();

    @Query("SELECT new am.imdb.films.service.model.wrapper.MoviesWrapper(m.id," +
            "m.originalTitle," +
            "m.year," +
            "m.datePublished," +
            "m.productionCompany," +
            "m.budget) FROM MovieEntity m")
    Page<MoviesWrapper> findAllWithPagination(Pageable composePageRequest);

    @Query("SELECT new am.imdb.films.service.model.wrapper.PersonsWrapper(p.id,p.name,p.bio,p.birthDetails, p.dateOfBirth, p.dateOfDeath)" +
            " FROM PersonEntity p where p.id = :id")
    Page<PersonsWrapper> findAllPersonsWithPagination(Long id, Pageable composePageRequest);

    @Query(value = "SELECT imdb_id FROM movie", nativeQuery = true)
    Set<String> findAllMoviesImdbId();
}

