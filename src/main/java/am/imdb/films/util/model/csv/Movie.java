package am.imdb.films.util.model.csv;


import am.imdb.films.persistence.entity.MovieEntity;
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
public class Movie {

    private Long id;

    @CsvBindByName(column = "imdb_id")
    private String imdbId;

    @CsvBindByName(column = "title")
    private String title;

    @CsvBindByName(column = "release_date")
    private Integer releaseDate;

    @CsvBindByName(column = "duration")
    private Integer duration;

    @CsvBindByName(column = "production_company")
    private String productionCompany;

    @CsvBindByName(column = "description")
    private String description;

    @CsvBindByName(column = "avg_vote")
    private  Double avgVote;

    @CsvBindByName(column = "votes")
    private  Integer votes;

    @CsvBindByName(column = "budget")
    private String budget;

    @CsvBindByName(column = "usa_gross_income")
    private String usaGrossIncome;

    @CsvBindByName(column = "worldwide_gross_income")
    private String worldWideGrossIncome;

    @CsvBindByName(column = "metas_core")
    private Double metasCore;

    @CsvBindByName(column = "reviews_from_users")
    private Double reviewsFromUsers;

    @CsvBindByName(column = "reviews_from_critics")
    private Double reviewsFromCritics;

    @CsvBindAndSplitByName(elementType = String.class, splitOn = ",", column = "genres")
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
        entity.setReleaseDate(movie.getReleaseDate());
        entity.setDuration(movie.getDuration());
        entity.setProductionCompany(movie.getProductionCompany());
        entity.setDescription(movie.getDescription());
        entity.setAvgVote(movie.getAvgVote());
        entity.setVotes(movie.getVotes());
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
