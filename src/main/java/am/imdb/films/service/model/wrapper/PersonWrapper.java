package am.imdb.films.service.model.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;

@Data
@AllArgsConstructor
public class PersonWrapper {

    private Long id;
    private String imdbId;
    private String name;
    private String birthName;
    private Integer height;
    private String bio;
    private String birthDetails;
    private String dateOfBirth;
    private String placeOfBirth;
    private String deathDetails;
    private String dateOfDeath;
    private String placeOfDeath;
    private String reasonOfDeath;
    private String spousesString;
    private Integer spouses;
    private Integer divorces;
    private Integer spousesWithChildren;
    private Integer children;


}
