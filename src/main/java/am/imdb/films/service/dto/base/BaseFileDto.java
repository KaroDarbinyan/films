package am.imdb.films.service.dto.base;


import am.imdb.films.persistence.entity.FileEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseFileDto {

    protected Long id;
    protected String path;
    protected String fileName;
    protected String extension;
    protected String contentType;
    protected LocalDateTime createdAt;


    public static BaseFileDto toBaseDto(FileEntity entity) {
        return BaseFileDto
                .builder()
                .id(entity.getId())
                .path(entity.getPath())
                .fileName(entity.getFileName())
                .extension(entity.getExtension())
                .contentType(entity.getContentType())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public static FileEntity toEntity(BaseFileDto dto, FileEntity entity) {
        if (Objects.isNull(entity.getId())) entity.setId(dto.getId());
        entity.setPath(dto.getPath());
        entity.setFileName(dto.getFileName());
        entity.setExtension(dto.getExtension());
        entity.setContentType(dto.getContentType());
        entity.setCreatedAt(dto.getCreatedAt());
        return entity;
    }

    public static List<BaseFileDto> toBaseDtoList(Collection<FileEntity> entityCollection) {
        return entityCollection.stream()
                .map(BaseFileDto::toBaseDto)
                .collect(Collectors.toList());
    }

    public static List<FileEntity> toEntityList(Collection<BaseFileDto> dtoCollection) {
        return dtoCollection.stream()
                .map(languageDto -> BaseFileDto.toEntity(languageDto, new FileEntity()))
                .collect(Collectors.toList());
    }

}
