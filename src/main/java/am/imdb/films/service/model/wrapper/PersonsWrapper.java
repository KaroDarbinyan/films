package am.imdb.films.service.model.wrapper;


import am.imdb.films.persistence.entity.PersonEntity;
import am.imdb.films.persistence.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PersonsWrapper {

    private Long id;
    private String name;
    private String bio;
    private String birthDetails;
    private String dateOfBirth;
    private String dateOfDeath;

    public PersonsWrapper(PersonEntity personEntity) {
        this.id = personEntity.getId();
        this.name = personEntity.getName();
        this.bio = personEntity.getBio();
        this.birthDetails = personEntity.getBirthDetails();
        this.dateOfBirth = personEntity.getDateOfBirth();
        this.dateOfDeath = personEntity.getReasonOfDeath();
    }

}
