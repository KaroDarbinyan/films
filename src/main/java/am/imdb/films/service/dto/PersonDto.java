package am.imdb.films.service.dto;


import am.imdb.films.persistence.entity.PersonEntity;
import am.imdb.films.persistence.entity.relation.PersonFileEntity;
import am.imdb.films.service.dto.base.BaseFileDto;
import am.imdb.films.service.dto.base.BasePersonDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PersonDto extends BasePersonDto {

    private List<BaseFileDto> files;

    public static PersonDto toDto(PersonEntity entity) {
        PersonDto personDto = (PersonDto) BasePersonDto.toBaseDto(entity);
        personDto.setFiles(entity.getListOfPersonFile()
                .stream()
                .map(PersonFileEntity::getFile)
                .map(BaseFileDto::toBaseDto)
                .collect(Collectors.toList())
        );
        return personDto;
    }


    public static List<PersonDto> toDtoList(Collection<PersonEntity> entityCollection) {
        return entityCollection.stream()
                .map(PersonDto::toDto)
                .collect(Collectors.toList());
    }

}
