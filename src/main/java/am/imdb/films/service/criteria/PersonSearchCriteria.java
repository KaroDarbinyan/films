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
    private Integer spousesMin = 0;
    private Integer spousesMax = Integer.MAX_VALUE;
    private Integer divorcesMin = 0;
    private Integer divorcesMax = Integer.MAX_VALUE;
    private Integer spousesWithChildrenMin = 0;
    private Integer spousesWithChildrenMax = Integer.MAX_VALUE;
    private Integer childrenMin = 0;
    private Integer childrenMax = Integer.MAX_VALUE;
}
