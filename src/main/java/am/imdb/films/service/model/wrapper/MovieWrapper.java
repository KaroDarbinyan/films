package am.imdb.films.service.model.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieWrapper {

    private Long id;
    private String imdbId;
    private String title;
    private Integer releaseDate;
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

}
