package am.imdb.films.persistence.repository;


import am.imdb.films.persistence.entity.LanguageEntity;
import am.imdb.films.persistence.entity.MovieEntity;
import am.imdb.films.persistence.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    PersonEntity findPersonByImdbId(String imdbId);
    List<PersonEntity> findByImdbIdIn(List<String> imdbIds);

    @Query(value = "SELECT id, imdb_id FROM person", nativeQuery = true)
    List<Map<String, String>> findAllMovieIdAndIds();


//    @Query("SELECT u FROM PersonEntity u LEFT JOIN u.listOfMovieCasts WHERE " +
//            ":name IS NULL OR u.name = :name")
//    List<PersonEntity> findAllPersons(String name);
}

