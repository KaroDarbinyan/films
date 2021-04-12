package am.imdb.films.service.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MovieSearchCriteria extends SearchCriteria {

    private String imdbId;
    private String title;
    private Integer yearMin;
    private Integer yearMax;
    private String productionCompany;
    private Double budgetMin;
    private Double budgetMax;
    private Integer ratingMin;
    private Integer ratingMax;
    private List<String> actors;
    private List<String> actorsDoNotPlay;
    private List<String> genres;
    private List<String> notOfTheseGenres;
    private List<String> producers;
    private List<String> producersNotParticipate;

    public MovieSearchCriteria() {
        imdbId = getValueOrDefault(imdbId);
        title = getValueOrDefault(title);
        productionCompany = getValueOrDefault(productionCompany);
        budgetMin = Optional.ofNullable(budgetMin).orElse(0d);
        budgetMax = Optional.ofNullable(budgetMax).orElse(Double.MAX_VALUE);
        actors = Optional.ofNullable(actors).orElse(new ArrayList<>());
        producers = Optional.ofNullable(producers).orElse(new ArrayList<>());
        genres = Optional.ofNullable(genres).orElse(new ArrayList<>());
    }

    public String joinGenres() {
        return String.join(", ", this.genres);
    }

}
