package am.imdb.films.service.dto;


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
public class FileDto {

    protected Long id;
    protected String path;
    protected String fileName;
    protected String extension;
    protected String contentType;
    protected LocalDateTime createdAt;


    public static FileDto toDto(FileEntity entity) {
        return FileDto
                .builder()
                .id(entity.getId())
                .path(entity.getPath())
                .fileName(entity.getFileName())
                .extension(entity.getExtension())
                .contentType(entity.getContentType())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public static FileEntity toEntity(FileDto dto, FileEntity entity) {
        if (Objects.isNull(entity.getId())) entity.setId(dto.getId());
        entity.setPath(dto.getPath());
        entity.setFileName(dto.getFileName());
        entity.setExtension(dto.getExtension());
        entity.setContentType(dto.getContentType());
        entity.setCreatedAt(dto.getCreatedAt());
        return entity;
    }

    public static List<FileDto> toDtoList(Collection<FileEntity> entityCollection) {
        return entityCollection.stream()
                .map(FileDto::toDto)
                .collect(Collectors.toList());
    }

    public static List<FileEntity> toEntityList(Collection<FileDto> dtoCollection) {
        return dtoCollection.stream()
                .map(languageDto -> FileDto.toEntity(languageDto, new FileEntity()))
                .collect(Collectors.toList());
    }

}
