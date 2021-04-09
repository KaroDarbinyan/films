package am.imdb.films.service.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;


@Data
@NoArgsConstructor
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



}
