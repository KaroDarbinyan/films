package am.imdb.films.persistence.repository;


import am.imdb.films.persistence.entity.relation.MoviePersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoviePersonRepository extends JpaRepository<MoviePersonEntity, Long> {

    MoviePersonEntity findByMovieIdAndPersonId(Long movieId, Long personId);

}

