package am.imdb.films.service.dto;


import am.imdb.films.persistence.entity.MovieEntity;
import am.imdb.films.service.model.validation.Create;
import am.imdb.films.service.model.validation.Update;
import com.opencsv.bean.CsvBindAndSplitByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
    private String originalTitle;
    private String year;
    private String datePublished;
    private String duration;
    private String director;
    private String writer;
    private String productionCompany;
    private String actors;
    private String description;
    private String avgVote;
    private String budget;
    private String usaGrossIncome;
    private String worldWideGrossIncome;
    private String metasCore;
    private String reviewsFromUsers;
    private String reviewsFromCritics;

    @CsvBindAndSplitByName(elementType = String.class, splitOn = ",", column = "actors")
    private Set<String> personNames;

    @CsvBindAndSplitByName(elementType = String.class, splitOn = ",", column = "genre")
    private Set<String> genreNames;

    @CsvBindAndSplitByName(elementType = String.class, splitOn = ",", column = "country")
    private Set<String> countryNames;

    @CsvBindAndSplitByName(elementType = String.class, splitOn = ",", column = "language")
    private Set<String> languageNames;

    private List<GenreDto> genres;
    private List<CountryDto> countries;
    private List<LanguageDto> languages;


    public static MovieDto toDto(MovieEntity entity) {

        return MovieDto
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
//                .genres(GenreDto.toDto(entity.getGenres()))
//                .countries(CountryDto.toDto(entity.getCountries()))
//                .languages(LanguageDto.toDto(entity.getLanguages()))
                .build();
    }

    public static MovieEntity toEntity(MovieDto dto, MovieEntity entity) {
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
//        if (dto.getLanguages() != null)
//            entity.setLanguages(LanguageDto.toEntity(dto.getLanguages()));
//        if (dto.getCountries() != null)
//            entity.setCountries(CountryDto.toEntity(dto.getCountries()));
//        if (dto.getGenres() != null)
//            entity.setGenres(GenreDto.toEntity(dto.getGenres()));
        return entity;
    }


    public static List<MovieDto> toDto(Collection<MovieEntity> entityCollection) {
        return entityCollection.stream()
                .map(MovieDto::toDto)
                .collect(Collectors.toList());
    }

    public static List<MovieEntity> toEntity(Collection<MovieDto> dtoCollection) {
        return dtoCollection.stream()
                .map(movieDto -> MovieDto.toEntity(movieDto, new MovieEntity()))
                .collect(Collectors.toList());
    }


//    private static List<GenreDto> getGenreList(MovieEntity entity) {
//        List<MovieGenreEntity> movieGenreEntityList = entity.getMovieGenreEntityList();
//        return movieGenreEntityList.stream()
//                .map(MovieGenreEntity::getGenre)
//                .map(GenreDto::mapEntityToDto)
//                .collect(Collectors.toList());
//    }


}
