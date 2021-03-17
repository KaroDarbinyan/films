package am.imdb.films.persistence.repository;


import am.imdb.films.persistence.entity.PersonEntity;
import am.imdb.films.persistence.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<RatingEntity, Long> {

    RatingEntity findRatingByMovieId(String movieId);

//    @Query("SELECT u FROM PersonEntity u LEFT JOIN u.listOfMovieCasts WHERE " +
//            ":name IS NULL OR u.name = :name")
//    List<PersonEntity> findAllPersons(String name);
}

