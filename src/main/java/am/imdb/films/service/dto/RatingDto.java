package am.imdb.films.service.dto;


import am.imdb.films.persistence.entity.RatingEntity;
import am.imdb.films.service.validation.model.Create;
import am.imdb.films.service.validation.model.Update;
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
public class RatingDto {


    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    private Long id;
    @CsvBindByName(column = "movie_id")
    private String movieId;
    @CsvBindByName(column = "average_rating")
    private String averageRating;
    @CsvBindByName(column = "num_votes")
    private String numVotes;

    public RatingDto(Long id, String averageRating, String numVotes) {
        this.id = id;
        this.averageRating = averageRating;
        this.numVotes = numVotes;
    }

    public static RatingDto toDto(RatingEntity entity) {
        return RatingDto
                .builder()
                .id(entity.getId())
                .averageRating(entity.getAverageRating())
                .numVotes(entity.getNumVotes())
                .build();
    }

    public static RatingEntity toEntity(RatingDto dto, RatingEntity entity) {
        if (Objects.isNull(entity.getId())) entity.setId(dto.getId());
        entity.setAverageRating(dto.getAverageRating());
        entity.setNumVotes(dto.getNumVotes());
        return entity;
    }

    public static List<RatingDto> toDto(Collection<RatingEntity> entityCollection) {
        return entityCollection.stream()
                .map(RatingDto::toDto)
                .collect(Collectors.toList());
    }

    public static List<RatingEntity> toEntity(Collection<RatingDto> dtoCollection) {
        return dtoCollection.stream()
                .map(ratingDto -> RatingDto.toEntity(ratingDto, new RatingEntity()))
                .collect(Collectors.toList());
    }

}

