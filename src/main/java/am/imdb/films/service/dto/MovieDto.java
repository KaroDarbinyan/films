package am.imdb.films.service.dto;


import am.imdb.films.persistence.entity.MovieEntity;
import am.imdb.films.service.validation.model.Create;
import am.imdb.films.service.validation.model.Update;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {

    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    private Long id;
    @NotNull(groups = {Create.class, Update.class})
    private String imdbId;
    private String title;
    private Integer year;
    private String datePublished;
    private Integer duration;
    private String productionCompany;
    private String description;
    private Double avgVote;
    private Integer votes;
    private String budget;
    private String usaGrossIncome;
    private String worldWideGrossIncome;
    private Double metasCore;
    private Double reviewsFromUsers;
    private Double reviewsFromCritics;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public static MovieDto toDto(MovieEntity entity) {

        return MovieDto
                .builder()
                .id(entity.getId())
                .imdbId(entity.getImdbId())
                .title(entity.getTitle())
                .year(entity.getYear())
                .datePublished(entity.getDatePublished())
                .duration(entity.getDuration())
                .productionCompany(entity.getProductionCompany())
                .description(entity.getDescription())
                .avgVote(entity.getAvgVote())
                .votes(entity.getVotes())
                .budget(entity.getBudget())
                .usaGrossIncome(entity.getUsaGrossIncome())
                .worldWideGrossIncome(entity.getWorldWideGrossIncome())
                .metasCore(entity.getMetasCore())
                .reviewsFromUsers(entity.getReviewsFromUsers())
                .reviewsFromCritics(entity.getReviewsFromCritics())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static MovieEntity toEntity(MovieDto dto, MovieEntity entity) {
        if (Objects.isNull(entity.getId())) entity.setId(dto.getId());
        entity.setImdbId(dto.getImdbId());
        entity.setTitle(dto.getTitle());
        entity.setYear(dto.getYear());
        entity.setDatePublished(dto.getDatePublished());
        entity.setDuration(dto.getDuration());
        entity.setProductionCompany(dto.getProductionCompany());
        entity.setDescription(dto.getDescription());
        entity.setAvgVote(dto.getAvgVote());
        entity.setVotes(dto.getVotes());
        entity.setBudget(dto.getBudget());
        entity.setUsaGrossIncome(dto.getUsaGrossIncome());
        entity.setWorldWideGrossIncome(dto.getWorldWideGrossIncome());
        entity.setMetasCore(dto.getMetasCore());
        entity.setReviewsFromUsers(dto.getReviewsFromUsers());
        entity.setReviewsFromCritics(dto.getReviewsFromCritics());
        if (Objects.isNull(entity.getCreatedAt())) entity.setCreatedAt(dto.getCreatedAt());
        if (Objects.isNull(entity.getUpdatedAt())) entity.setUpdatedAt(dto.getUpdatedAt());


        return entity;
    }


    public static List<MovieDto> toDtoList(Collection<MovieEntity> entityCollection) {
        return entityCollection.stream()
                .map(MovieDto::toDto)
                .collect(Collectors.toList());
    }

    public static List<MovieEntity> toEntityList(Collection<MovieDto> dtoCollection) {
        return dtoCollection.stream()
                .map(movieDto -> MovieDto.toEntity(movieDto, new MovieEntity()))
                .collect(Collectors.toList());
    }

}
