package am.imdb.films.service.model.csv;


import am.imdb.films.persistence.entity.RatingEntity;
import am.imdb.films.service.model.validation.Create;
import am.imdb.films.service.model.validation.Update;
import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
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
    @CsvBindByName(column = "movie_id")
    private String movieId;
    @CsvBindByName(column = "average_rating")
    private String averageRating;
    @CsvBindByName(column = "num_votes")
    private String numVotes;


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

