package am.imdb.films.service.dto;


import am.imdb.films.persistence.entity.RatingEntity;
import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingDto {


    private Long id;
    @CsvBindByName(column = "movie_id")
    private String movieId;
    @CsvBindByName(column = "average_rating")
    private String averageRating;
    @CsvBindByName(column = "num_votes")
    private String numVotes;

    public static RatingDto toDto(RatingEntity entity) {
        return RatingDto
                .builder()
                .id(entity.getId())
                .movieId(entity.getMovieId())
                .averageRating(entity.getAverageRating())
                .numVotes(entity.getNumVotes())
                .build();
    }

    public static RatingEntity toEntity(RatingDto dto) {
        return new RatingEntity(dto.getId(), dto.getMovieId(), dto.getAverageRating(), dto.getNumVotes());
    }

    public static List<RatingDto> toDto(Collection<RatingEntity> entityCollection) {
        return entityCollection.stream()
                .map(RatingDto::toDto)
                .collect(Collectors.toList());
    }

    public static List<RatingEntity> toEntity(Collection<RatingDto> dtoCollection) {
        return dtoCollection.stream()
                .map(RatingDto::toEntity)
                .collect(Collectors.toList());
    }


}

