package am.imdb.films.service.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    private String genre;
    private String language;
    private String country;
    private Integer ratingMin;
    private Integer ratingMax;

    public MovieSearchCriteria() {
        this.imdbId = getValueOrDefault(this.imdbId);
        this.title = getValueOrDefault(this.title);
        this.productionCompany = getValueOrDefault(this.productionCompany);
    }

}
