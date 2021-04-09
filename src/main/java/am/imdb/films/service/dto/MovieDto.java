package am.imdb.films.service.dto;


import am.imdb.films.persistence.entity.CountryEntity;
import am.imdb.films.persistence.entity.GenreEntity;
import am.imdb.films.persistence.entity.LanguageEntity;
import am.imdb.films.persistence.entity.MovieEntity;
import am.imdb.films.persistence.entity.relation.MovieCountryEntity;
import am.imdb.films.persistence.entity.relation.MovieGenreEntity;
import am.imdb.films.persistence.entity.relation.MovieLanguageEntity;
import am.imdb.films.service.validation.model.Create;
import am.imdb.films.service.validation.model.Update;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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
    private List<Map<String, String>> images;
    private String genres;
    private String languages;
    private String countries;


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
                .images(getImages(entity))
                .genres(getGenres(entity))
                .languages(getLanguages(entity))
                .countries(getCountries(entity))
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

    private static List<Map<String, String>> getImages(MovieEntity entity) {
        if (Objects.isNull(entity.getListOfMovieFile())) return List.of();

        return entity.getListOfMovieFile().stream()
                .map(movieFileEntity -> {
                    String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("/files/")
                            .path(movieFileEntity.getFile().getId().toString())
                            .toUriString();
                    return Map.of(
                            "general", Boolean.toString(movieFileEntity.getGeneral()),
                            "fileType", movieFileEntity.getFile().getContentType(),
                            "url", url,
                            "id", movieFileEntity.getFile().getId().toString()
                            );
                }).collect(Collectors.toList());
    }



    private static String getGenres(MovieEntity entity) {
        if (entity.getListOfMovieGenre().isEmpty()) return null;

        return entity.getListOfMovieGenre()
                .stream()
                .map(MovieGenreEntity::getGenre)
                .map(GenreEntity::getName)
                .collect(Collectors.joining(", "));
    }

    private static String getLanguages(MovieEntity entity) {
        if (entity.getListOfMovieLanguage().isEmpty()) return null;

        return entity.getListOfMovieLanguage()
                .stream()
                .map(MovieLanguageEntity::getLanguage)
                .map(LanguageEntity::getName)
                .collect(Collectors.joining(", "));
    }

    private static String getCountries(MovieEntity entity) {
        if (entity.getListOfMovieCountry().isEmpty()) return null;

        return entity.getListOfMovieCountry()
                .stream()
                .map(MovieCountryEntity::getCountry)
                .map(CountryEntity::getName)
                .collect(Collectors.joining(", "));
    }

}
