package am.imdb.films.service.dto;


import am.imdb.films.persistence.entity.PersonEntity;
import am.imdb.films.service.validation.model.Create;
import am.imdb.films.service.validation.model.Update;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {

    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    protected Long id;
    @NotNull(groups = {Create.class, Update.class})
    protected String imdbId;
    protected String name;
    protected Integer height;
    protected String bio;
    protected String birthDetails;
    protected Integer birthDate;
    protected String placeOfBirth;
    protected String deathDetails;
    protected Integer deathDate;
    protected String placeOfDeath;
    protected String reasonOfDeath;
    protected String spousesString;
    protected Integer spouses;
    protected Integer divorces;
    protected Integer spousesWithChildren;
    protected Integer children;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public static PersonDto toDto(PersonEntity entity) {
        return PersonDto
                .builder()
                .id(entity.getId())
                .imdbId(entity.getImdbId())
                .name(entity.getName())
                .height(entity.getHeight())
                .bio(entity.getBio())
                .birthDetails(entity.getBirthDetails())
                .birthDate(entity.getBirthDate())
                .placeOfBirth(entity.getPlaceOfBirth())
                .deathDetails(entity.getDeathDetails())
                .deathDate(entity.getDeathDate())
                .placeOfDeath(entity.getPlaceOfDeath())
                .reasonOfDeath(entity.getReasonOfDeath())
                .spousesString(entity.getSpousesString())
                .spouses(entity.getSpouses())
                .divorces(entity.getDivorces())
                .spousesWithChildren(entity.getSpousesWithChildren())
                .children(entity.getChildren())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static PersonEntity toEntity(PersonDto dto, PersonEntity entity) {
        if (Objects.isNull(entity.getId())) entity.setId(dto.getId());
        entity.setImdbId(dto.getImdbId());
        entity.setName(dto.getName());
        entity.setHeight(dto.getHeight());
        entity.setBio(dto.getBio());
        entity.setBirthDetails(dto.getBirthDetails());
        entity.setBirthDate(dto.getBirthDate());
        entity.setPlaceOfBirth(dto.getPlaceOfBirth());
        entity.setDeathDetails(dto.getDeathDetails());
        entity.setDeathDate(dto.getDeathDate());
        entity.setPlaceOfDeath(dto.getPlaceOfDeath());
        entity.setReasonOfDeath(dto.getReasonOfDeath());
        entity.setSpousesString(dto.getSpousesString());
        entity.setSpouses(dto.getSpouses());
        entity.setDivorces(dto.getDivorces());
        entity.setSpousesWithChildren(dto.getSpousesWithChildren());
        entity.setChildren(dto.getChildren());
        if (Objects.isNull(entity.getCreatedAt())) entity.setCreatedAt(dto.getCreatedAt());
        if (Objects.isNull(entity.getUpdatedAt())) entity.setUpdatedAt(dto.getUpdatedAt());

        return entity;
    }


    public static List<PersonDto> toDtoList(Collection<PersonEntity> entityCollection) {
        return entityCollection.stream()
                .map(PersonDto::toDto)
                .collect(Collectors.toList());
    }

    public static List<PersonEntity> toEntityList(Collection<PersonDto> dtoCollection) {
        return dtoCollection.stream()
                .map(personDto -> PersonDto.toEntity(personDto, new PersonEntity()))
                .collect(Collectors.toList());
    }
}
