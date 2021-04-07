package am.imdb.films.service.dto;


import am.imdb.films.persistence.entity.LanguageEntity;
import am.imdb.films.service.validation.model.Create;
import am.imdb.films.service.validation.model.Update;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LanguageDto {


    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    private Long id;
    private String name;

    public static LanguageDto toDto(LanguageEntity entity) {
        return LanguageDto
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public static LanguageEntity toEntity(LanguageDto dto, LanguageEntity entity) {
        if (Objects.isNull(entity.getId())) entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }

    public static List<LanguageDto> toDtoList(Collection<LanguageEntity> entityCollection) {
        return entityCollection.stream()
                .map(LanguageDto::toDto)
                .collect(Collectors.toList());
    }

    public static List<LanguageEntity> toEntityList(Collection<LanguageDto> dtoCollection) {
        return dtoCollection.stream()
                .map(languageDto -> LanguageDto.toEntity(languageDto, new LanguageEntity()))
                .collect(Collectors.toList());
    }

}
