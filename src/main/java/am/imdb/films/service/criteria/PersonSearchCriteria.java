package am.imdb.films.service.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@AllArgsConstructor
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

    public PersonSearchCriteria() {
        this.imdbId = getValueOrDefault(this.imdbId);
        this.name = getValueOrDefault(this.name);
        this.birthName = getValueOrDefault(this.birthName);
        this.bio = getValueOrDefault(this.bio);
        this.placeOfBirth = getValueOrDefault(this.placeOfBirth);
        this.deathDetails = getValueOrDefault(this.deathDetails);
        this.placeOfDeath = getValueOrDefault(this.placeOfDeath);
    }
}
