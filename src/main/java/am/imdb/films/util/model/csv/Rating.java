package am.imdb.films.util.model.csv;


import am.imdb.films.persistence.entity.RatingEntity;
import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rating {

    private Long id;
    @CsvBindByName(column = "movie_imdb_id")
    private String movieImdbId;
    @CsvBindByName(column = "average_rating")
    private Double averageRating;
    @CsvBindByName(column = "num_votes")
    private Integer numVotes;


    public static RatingEntity toEntity(Rating rating) {
        RatingEntity entity = new RatingEntity();

        if (Objects.isNull(entity.getId())) entity.setId(rating.getId());
        entity.setAverageRating(rating.getAverageRating());
        entity.setNumVotes(rating.getNumVotes());

        return entity;
    }

    public static List<RatingEntity> toEntityList(Collection<Rating> ratingCollection) {
        return ratingCollection.stream()
                .map(Rating::toEntity)
                .collect(Collectors.toList());
    }

}

