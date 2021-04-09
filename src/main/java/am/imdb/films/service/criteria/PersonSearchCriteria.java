package am.imdb.films.service.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.aspectj.lang.annotation.Before;

import java.util.Objects;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PersonSearchCriteria extends SearchCriteria {

    private String imdbId;
    private String name;
    private String birthName;
    private String bio;
    private String placeOfBirth;
    private String deathDetails;
    private String placeOfDeath;
    private Integer spousesMin;
    private Integer spousesMax;
    private Integer divorcesMin;
    private Integer divorcesMax;
    private Integer spousesWithChildrenMin;
    private Integer spousesWithChildrenMax;
    private Integer childrenMin;
    private Integer childrenMax;
}
