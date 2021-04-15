package am.imdb.films.util.model.csv;


import am.imdb.films.persistence.entity.PersonEntity;
import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person{

    private Long id;

    @CsvBindByName(column = "imdb_id")
    private String imdbId;

    @CsvBindByName(column = "name")
    private String name;

    @CsvBindByName(column = "height")
    private Integer height;

    @CsvBindByName(column = "bio")
    private String bio;

    @CsvBindByName(column = "birth_details")
    private String birthDetails;

    @CsvBindByName(column = "birth_date")
    private Integer birthDate;

    @CsvBindByName(column = "place_of_birth")
    private String placeOfBirth;

    @CsvBindByName(column = "death_details")
    private String deathDetails;

    @CsvBindByName(column = "death_date")
    private Integer deathDate;

    @CsvBindByName(column = "place_of_death")
    private String placeOfDeath;

    @CsvBindByName(column = "reason_of_death")
    private String reasonOfDeath;

    @CsvBindByName(column = "spouses_string")
    private String spousesString;

    @CsvBindByName(column = "spouses")
    private Integer spouses;

    @CsvBindByName(column = "divorces")
    private Integer divorces;

    @CsvBindByName(column = "spouses_with_children")
    private Integer spousesWithChildren;

    @CsvBindByName(column = "children")
    private Integer children;


    public static PersonEntity toEntity(Person person) {
        PersonEntity entity = new PersonEntity();

        if (Objects.nonNull(person.getId())) entity.setId(person.getId());
        entity.setImdbId(person.getImdbId());
        entity.setName(person.getName());
        entity.setHeight(person.getHeight());
        entity.setBio(person.getBio());
        entity.setBirthDetails(person.getBirthDetails());
        entity.setBirthDate(person.getBirthDate());
        entity.setPlaceOfBirth(person.getPlaceOfBirth());
        entity.setDeathDetails(person.getDeathDetails());
        entity.setDeathDate(person.getDeathDate());
        entity.setPlaceOfDeath(person.getPlaceOfDeath());
        entity.setReasonOfDeath(person.getReasonOfDeath());
        entity.setSpousesString(person.getSpousesString());
        entity.setSpouses(person.getSpouses());
        entity.setDivorces(person.getDivorces());
        entity.setSpousesWithChildren(person.getSpousesWithChildren());
        entity.setChildren(person.getChildren());
        return entity;
    }


    public static List<PersonEntity> toEntityList(Collection<Person> personCollection) {
        return personCollection.stream()
                .map(Person::toEntity)
                .collect(Collectors.toList());
    }

}
