package am.imdb.films.service.dto;


import am.imdb.films.persistence.entity.MovieEntity;
import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {

    private Long id;

    @CsvBindByName(column = "imdb_title_id")
    private String imdbId;

    @CsvBindByName(column = "title")
    private String title;

    @CsvBindByName(column = "original_title")
    private String originalTitle;

    @CsvBindByName(column = "year")
    private String year;

    @CsvBindByName(column = "date_published")
    private String datePublished;

    @CsvBindByName(column = "duration")
    private String duration;

    @CsvBindByName(column = "director")
    private String director;

    @CsvBindByName(column = "writer")
    private String writer;

    @CsvBindByName(column = "production_company")
    private String productionCompany;

    @CsvBindByName(column = "actors")
    private String actors;

    @CsvBindByName(column = "description")
    private String description;

    @CsvBindByName(column = "avg_vote")
    private String avgVote;

    @CsvBindByName(column = "budget")
    private String budget;

    @CsvBindByName(column = "usa_gross_income")
    private String usaGrossIncome;

    @CsvBindByName(column = "worldwide_gross_income")
    private String worldWideGrossIncome;

    @CsvBindByName(column = "metas_core")
    private String metasCore;

    @CsvBindByName(column = "reviews_from_users")
    private String reviewsFromUsers;

    @CsvBindByName(column = "reviews_from_critics")
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
                .genres(GenreDto.toDto(entity.getGenres()))
                .countries(CountryDto.toDto(entity.getCountries()))
                .languages(LanguageDto.toDto(entity.getLanguages()))
                .build();
    }

    public static MovieEntity toEntity(MovieDto dto) {
        MovieEntity entity = new MovieEntity();
        entity.setId(dto.getId());
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
        if (dto.getLanguages() != null)
            entity.setLanguages(LanguageDto.toEntity(dto.getLanguages()));
        if (dto.getCountries() != null)
            entity.setCountries(CountryDto.toEntity(dto.getCountries()));
        if (dto.getGenres() != null)
            entity.setGenres(GenreDto.toEntity(dto.getGenres()));
        return entity;
    }


    public static List<MovieDto> toDto(Collection<MovieEntity> entityCollection) {
        return entityCollection.stream()
                .map(MovieDto::toDto)
                .collect(Collectors.toList());
    }

    public static List<MovieEntity> toEntity(Collection<MovieDto> dtoCollection) {
        return dtoCollection.stream()
                .map(MovieDto::toEntity)
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
