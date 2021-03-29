package am.imdb.films.service.dto.base;


import am.imdb.films.persistence.entity.MovieEntity;
import am.imdb.films.service.dto.CountryDto;
import am.imdb.films.service.dto.GenreDto;
import am.imdb.films.service.dto.LanguageDto;
import am.imdb.films.service.model.validation.Create;
import am.imdb.films.service.model.validation.Update;
import com.opencsv.bean.CsvBindAndSplitByName;
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
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseMovieDto {

    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    protected Long id;
    @NotNull(groups = {Create.class, Update.class})
    protected String imdbId;
    protected String title;
    protected String originalTitle;
    protected String year;
    protected String datePublished;
    protected String duration;
    protected String director;
    protected String writer;
    protected String productionCompany;
    protected String actors;
    protected String description;
    protected String avgVote;
    protected String budget;
    protected String usaGrossIncome;
    protected String worldWideGrossIncome;
    protected String metasCore;
    protected String reviewsFromUsers;
    protected String reviewsFromCritics;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

    public BaseMovieDto(Long id, String originalTitle, String year, String datePublished, String productionCompany, String budget) {
        this.id = id;
        this.originalTitle = originalTitle;
        this.year = year;
        this.datePublished = datePublished;
        this.productionCompany = productionCompany;
        this.budget = budget;
    }

    public static BaseMovieDto toBaseDto(MovieEntity entity) {

        return BaseMovieDto
                .builder()
                .id(entity.getId())
                .imdbId(entity.getImdbId())
                .title(entity.getTitle())
                .originalTitle(entity.getOriginalTitle())
                .year(entity.getYear())
                .datePublished(entity.getDatePublished())
                .duration(entity.getDuration())
                .director(entity.getDirector())
                .writer(entity.getWriter())
                .productionCompany(entity.getProductionCompany())
                .description(entity.getDescription())
                .avgVote(entity.getAvgVote())
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

    public static MovieEntity toEntity(BaseMovieDto dto, MovieEntity entity) {
        if (Objects.isNull(entity.getId())) entity.setId(dto.getId());
        entity.setImdbId(dto.getImdbId());
        entity.setTitle(dto.getTitle());
        entity.setOriginalTitle(dto.getOriginalTitle());
        entity.setYear(dto.getYear());
        entity.setDatePublished(dto.getDatePublished());
        entity.setDuration(dto.getDuration());
        entity.setDirector(dto.getDirector());
        entity.setWriter(dto.getWriter());
        entity.setProductionCompany(dto.getProductionCompany());
        entity.setDescription(dto.getDescription());
        entity.setAvgVote(dto.getAvgVote());
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


    public static List<BaseMovieDto> toBaseDtoList(Collection<MovieEntity> entityCollection) {
        return entityCollection.stream()
                .map(BaseMovieDto::toBaseDto)
                .collect(Collectors.toList());
    }

    public static List<MovieEntity> toEntityList(Collection<BaseMovieDto> dtoCollection) {
        return dtoCollection.stream()
                .map(movieDto -> BaseMovieDto.toEntity(movieDto, new MovieEntity()))
                .collect(Collectors.toList());
    }

}
