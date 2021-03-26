package am.imdb.films.service.model.wrapper;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MoviesWrapper {

    private Long id;
    private String originalTitle;
    private String year;
    private String datePublished;
    private String productionCompany;
    private String budget;

}
