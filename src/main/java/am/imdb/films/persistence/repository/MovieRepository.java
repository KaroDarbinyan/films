package am.imdb.films.persistence.repository;


import am.imdb.films.persistence.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    List<MovieEntity> findByImdbIdIn(List<String> imdbIds);

    @Query(value = "SELECT id, imdb_id FROM movie", nativeQuery = true)
    Map<String, String> findAllMovieIdAndIds();

//    @Query("select p.name, p.last_name as lastName from Person p", nativeQuery= true)
//    fun findAllNameAndLastNames(): List<NameSurnameProjection>



    MovieEntity findMovieByImdbId(String imdbId);


//    @Query("select new Map(o.imdbId, o.title, o.description) from MovieEntity o")
//    List<Map<String, Object>> findImdbIdTitleAndDescription();

}

