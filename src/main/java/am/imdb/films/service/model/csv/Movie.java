package am.imdb.films.service.model.csv;


import am.imdb.films.persistence.entity.MovieEntity;
import am.imdb.films.persistence.entity.PersonEntity;
import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie{

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

    public static MovieEntity toEntity(Movie movie) {
        MovieEntity entity = new MovieEntity();

        if (Objects.nonNull(entity.getId())) entity.setId(movie.getId());
        entity.setImdbId(movie.getImdbId());
        entity.setTitle(movie.getTitle());
        entity.setOriginalTitle(movie.getOriginalTitle());
        entity.setYear(movie.getYear());
        entity.setDatePublished(movie.getDatePublished());
        entity.setDuration(movie.getDuration());
        entity.setDirector(movie.getDirector());
        entity.setWriter(movie.getWriter());
        entity.setProductionCompany(movie.getProductionCompany());
        entity.setDescription(movie.getDescription());
        entity.setAvgVote(movie.getAvgVote());
        entity.setBudget(movie.getBudget());
        entity.setUsaGrossIncome(movie.getUsaGrossIncome());
        entity.setWorldWideGrossIncome(movie.getWorldWideGrossIncome());
        entity.setMetasCore(movie.getMetasCore());
        entity.setReviewsFromUsers(movie.getReviewsFromUsers());
        entity.setReviewsFromCritics(movie.getReviewsFromCritics());

        return entity;
    }


    public static List<MovieEntity> toEntityList(Collection<Movie> movieCollection) {
        return movieCollection.stream()
                .map(Movie::toEntity)
                .collect(Collectors.toList());
    }

}
