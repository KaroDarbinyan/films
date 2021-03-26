package am.imdb.films.service.dto;


import am.imdb.films.persistence.entity.PersonEntity;
import am.imdb.films.service.model.validation.Create;
import am.imdb.films.service.model.validation.Update;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {

    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    private Long id;

    @NotNull(groups = {Create.class, Update.class})
    private String imdbId;

    private String name;
    private String birthName;
    private String height;
    private String bio;
    private String birthDetails;
    private String dateOfBirth;
    private String placeOfBirth;
    private String deathDetails;
    private String dateOfDeath;
    private String placeOfDeath;
    private String reasonOfDeath;
    private String spousesString;
    private String spouses;
    private String divorces;
    private String spousesWithChildren;
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

    //todo do not pass dto id as entity id
    public static PersonEntity toEntity(PersonDto dto, PersonEntity entity) {
        if (entity.getId() != null) entity.setId(dto.getId());
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
                .map(personDto -> PersonDto.toEntity(personDto, new PersonEntity()))
                .collect(Collectors.toList());
    }
}
