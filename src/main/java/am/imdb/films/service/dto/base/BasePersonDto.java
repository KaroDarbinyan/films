package am.imdb.films.service.dto.base;


import am.imdb.films.persistence.entity.PersonEntity;
import am.imdb.films.service.model.validation.Create;
import am.imdb.films.service.model.validation.Update;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class BasePersonDto {

    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    protected Long id;
    @NotNull(groups = {Create.class, Update.class})
    protected String imdbId;
    protected String name;
    protected String birthName;
    protected String height;
    protected String bio;
    protected String birthDetails;
    protected String dateOfBirth;
    protected String placeOfBirth;
    protected String deathDetails;
    protected String dateOfDeath;
    protected String placeOfDeath;
    protected String reasonOfDeath;
    protected String spousesString;
    protected String spouses;
    protected String divorces;
    protected String spousesWithChildren;
    protected String children;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public BasePersonDto(Long id, String name, String bio, String birthDetails, String dateOfBirth, String dateOfDeath) {
        this.id = id;
        this.name = name;
        this.bio = bio;
        this.birthDetails = birthDetails;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
    }

    public static BasePersonDto toBaseDto(PersonEntity entity) {
        return BasePersonDto
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
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    //todo do not pass dto id as entity id
    public static PersonEntity toEntity(BasePersonDto dto, PersonEntity entity) {
        if (Objects.isNull(entity.getId())) entity.setId(dto.getId());
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
        if (Objects.isNull(entity.getCreatedAt())) entity.setCreatedAt(dto.getCreatedAt());
        if (Objects.isNull(entity.getUpdatedAt())) entity.setUpdatedAt(dto.getUpdatedAt());

        return entity;
    }


    public static List<BasePersonDto> toBaseDtoList(Collection<PersonEntity> entityCollection) {
        return entityCollection.stream()
                .map(BasePersonDto::toBaseDto)
                .collect(Collectors.toList());
    }

    public static List<PersonEntity> toEntityList(Collection<BasePersonDto> dtoCollection) {
        return dtoCollection.stream()
                .map(personDto -> BasePersonDto.toEntity(personDto, new PersonEntity()))
                .collect(Collectors.toList());
    }
}
