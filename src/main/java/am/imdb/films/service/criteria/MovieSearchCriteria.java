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
    private Integer yearMin = 0;
    private Integer yearMax = Integer.MAX_VALUE;
    private Integer durationMin = 0;
    private Integer durationMax = Integer.MAX_VALUE;
    private String productionCompany;
    private String description;
    private Double avgVoteMin = 0D;
    private Double avgVoteMax = Double.MAX_VALUE;
    private Integer votesMin = 0;
    private Integer votesMax = Integer.MAX_VALUE;
    private Double budgetMin = 0D;
    private Double budgetMax = Double.MAX_VALUE;
    private Double usaGrossIncomeMin = 0D;
    private Double usaGrossIncomeMax = Double.MAX_VALUE;
    private Double worldWideGrossIncomeMin = 0D;
    private Double worldWideGrossIncomeMax = Double.MAX_VALUE;
    private Double metasCoreMin = 0D;
    private Double metasCoreMax = Double.MAX_VALUE;
    private Double reviewsFromUsersMin = 0D;
    private Double reviewsFromUsersMax = Double.MAX_VALUE;
    private Double reviewsFromCriticsMin = 0D;
    private Double reviewsFromCriticsMax = Double.MAX_VALUE;

}
