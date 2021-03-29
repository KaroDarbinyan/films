package am.imdb.films.service.dto;


import am.imdb.films.persistence.entity.FileEntity;
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
public class FileDto extends BaseFileDto {

    private List<BaseUserDto> users;

    public static FileDto toDto(FileEntity entity) {
        FileDto fileDto = (FileDto) BaseFileDto.toBaseDto(entity);

        fileDto.setUsers(entity.getListOfUserFile()
                .stream()
                .map(UserFileEntity::getUser)
                .map(BaseUserDto::toBaseDto)
                .collect(Collectors.toList())
        );

        return fileDto;
    }

    public static List<FileDto> toDtoList(Collection<FileEntity> entityCollection) {
        return entityCollection.stream()
                .map(FileDto::toDto)
                .collect(Collectors.toList());
    }
}
