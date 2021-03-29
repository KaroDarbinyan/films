package am.imdb.films.service.dto;


import am.imdb.films.persistence.entity.UserEntity;
import am.imdb.films.persistence.entity.relation.UserFileEntity;
import am.imdb.films.service.dto.base.BaseFileDto;
import am.imdb.films.service.dto.base.BaseUserDto;
import lombok.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserDto extends BaseUserDto {

    private List<BaseFileDto> files;

    public static UserDto toDto(UserEntity entity) {
        UserDto userDto = (UserDto) BaseUserDto.toBaseDto(entity);
        userDto.setFiles(entity.getListOfUserFile()
                .stream()
                .map(UserFileEntity::getFile)
                .map(BaseFileDto::toBaseDto)
                .collect(Collectors.toList())
        );
        return userDto;
    }


    public static List<UserDto> toDtoList(Collection<UserEntity> entityCollection) {
        return entityCollection.stream()
                .map(UserDto::toDto)
                .collect(Collectors.toList());
    }


}
