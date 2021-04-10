package am.imdb.films.service.model.wrapper;

import am.imdb.films.service.validation.model.Create;
import am.imdb.films.service.validation.model.Update;
import lombok.Data;
import org.springframework.data.domain.Page;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class MovieSearchWrapper {

    private Long id;
    private String imdbId;
    private String title;
    private Integer year;
    private String datePublished;
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Map<String, String>> images;
    private String genres;
    private String languages;
    private String countries;
}
