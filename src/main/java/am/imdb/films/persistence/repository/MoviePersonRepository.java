package am.imdb.films.persistence.repository;


import am.imdb.films.persistence.entity.MoviePersonEntity;
import am.imdb.films.persistence.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoviePersonRepository extends JpaRepository<MoviePersonEntity, Long> {

    MoviePersonEntity findMoviePersonByMovieIdAndPersonId(Long movieId, Long personId);

//    @Query("SELECT u FROM PersonEntity u LEFT JOIN u.listOfMovieCasts WHERE " +
//            ":name IS NULL OR u.name = :name")
//    List<PersonEntity> findAllPersons(String name);
}

