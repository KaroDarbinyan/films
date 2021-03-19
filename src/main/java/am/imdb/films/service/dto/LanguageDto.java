package am.imdb.films.service.dto;


import am.imdb.films.persistence.entity.GenreEntity;
import am.imdb.films.persistence.entity.LanguageEntity;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
public class LanguageDto {


    private Long id;
    private String name;

    public static LanguageDto toDto(LanguageEntity entity) {
        return LanguageDto
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public static LanguageEntity toEntity(LanguageDto dto) {
        LanguageEntity languageEntity = new LanguageEntity();
        languageEntity.setId(dto.getId());
        languageEntity.setName(dto.getName());
        return languageEntity;
    }

    public static List<LanguageDto> toDto(Collection<LanguageEntity> entityCollection) {
        return entityCollection.stream()
                .map(LanguageDto::toDto)
                .collect(Collectors.toList());
    }

    public static List<LanguageEntity> toEntity(Collection<LanguageDto> dtoCollection) {
        return dtoCollection.stream()
                .map(LanguageDto::toEntity)
                .collect(Collectors.toList());
    }


}
