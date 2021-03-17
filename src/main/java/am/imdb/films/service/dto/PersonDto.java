package am.imdb.films.service.dto;


import am.imdb.films.persistence.entity.PersonEntity;
import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {

    private Long id;

    @CsvBindByName(column = "imdb_name_id")
    private String imdbId;

    @CsvBindByName(column = "name")
    private String name;

    @CsvBindByName(column = "birth_name")
    private String birthName;

    @CsvBindByName(column = "height")
    private String height;

    @CsvBindByName(column = "bio")
    private String bio;

    @CsvBindByName(column = "birth_details")
    private String birthDetails;

    @CsvBindByName(column = "date_of_birth")
    private String dateOfBirth;

    @CsvBindByName(column = "place_of_birth")
    private String placeOfBirth;

    @CsvBindByName(column = "death_details")
    private String deathDetails;

    @CsvBindByName(column = "date_of_death")
    private String dateOfDeath;

    @CsvBindByName(column = "place_of_death")
    private String placeOfDeath;

    @CsvBindByName(column = "reason_of_death")
    private String reasonOfDeath;

    @CsvBindByName(column = "spouses_string")
    private String spousesString;

    @CsvBindByName(column = "spouses")
    private String spouses;

    @CsvBindByName(column = "divorces")
    private String divorces;

    @CsvBindByName(column = "spouses_with_children")
    private String spousesWithChildren;

    @CsvBindByName(column = "children")
    private String children;


    public static PersonDto toDto(PersonEntity entity) {

        return PersonDto
                .builder()
                .id(entity.getId())
                .imdbId(entity.getImdbId())
                .name(entity.getName())
                .birthName(entity.getBirthName())
                .height(entity.getHeight())
                .bio(entity.getBio())
                .birthDetails(entity.getBirthDetails())
                .dateOfBirth(entity.getDateOfBirth())
                .placeOfBirth(entity.getPlaceOfBirth())
                .deathDetails(entity.getDeathDetails())
                .dateOfDeath(entity.getDateOfDeath())
                .placeOfDeath(entity.getPlaceOfDeath())
                .reasonOfDeath(entity.getReasonOfDeath())
                .spousesString(entity.getSpousesString())
                .spouses(entity.getSpouses())
                .divorces(entity.getDivorces())
                .spousesWithChildren(entity.getSpousesWithChildren())
                .children(entity.getChildren())
                .build();
    }

    public static PersonEntity toEntity(PersonDto dto) {
        PersonEntity entity = new PersonEntity();
        entity.setId(dto.getId());
        entity.setImdbId(dto.getImdbId());
        entity.setName(dto.getName());
        entity.setBirthName(dto.getBirthName());
        entity.setHeight(dto.getHeight());
        entity.setBio(dto.getBio());
        entity.setBirthDetails(dto.getBirthDetails());
        entity.setDateOfBirth(dto.getDateOfBirth());
        entity.setPlaceOfBirth(dto.getPlaceOfBirth());
        entity.setDeathDetails(dto.getDeathDetails());
        entity.setDateOfDeath(dto.getDateOfDeath());
        entity.setPlaceOfDeath(dto.getPlaceOfDeath());
        entity.setReasonOfDeath(dto.getReasonOfDeath());
        entity.setSpousesString(dto.getSpousesString());
        entity.setSpouses(dto.getSpouses());
        entity.setDivorces(dto.getDivorces());
        entity.setSpousesWithChildren(dto.getSpousesWithChildren());
        entity.setChildren(dto.getChildren());
        return entity;
    }


    public static List<PersonDto> toDto(Collection<PersonEntity> entityCollection) {
        return entityCollection.stream()
                .map(PersonDto::toDto)
                .collect(Collectors.toList());
    }

    public static List<PersonEntity> toEntity(Collection<PersonDto> dtoCollection) {
        return dtoCollection.stream()
                .map(PersonDto::toEntity)
                .collect(Collectors.toList());
    }

}
