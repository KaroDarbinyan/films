package am.imdb.films.persistence.repository;


import am.imdb.films.persistence.entity.RatingEntity;
import am.imdb.films.service.dto.RatingDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<RatingEntity, Long> {

    RatingEntity findRatingByMovieId(String movieId);


    @Query("SELECT new am.imdb.films.service.dto.RatingDto(r.id, r.averageRating, r.numVotes) FROM RatingEntity r")
    Page<RatingDto> findAllWithPagination(Pageable pageable);
}

