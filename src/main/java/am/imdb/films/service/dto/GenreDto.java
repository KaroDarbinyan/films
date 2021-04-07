package am.imdb.films.service.dto;


import am.imdb.films.persistence.entity.GenreEntity;
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
public class GenreDto {


    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    private Long id;
    private String name;

    public static GenreDto toDto(GenreEntity entity) {
        return GenreDto
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public static GenreEntity toEntity(GenreDto dto, GenreEntity entity) {
        if (Objects.isNull(entity.getId())) entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }

    public static List<GenreDto> toDtoList(Collection<GenreEntity> entityCollection) {
        return entityCollection.stream()
                .map(GenreDto::toDto)
                .collect(Collectors.toList());
    }

    public static List<GenreEntity> toEntityList(Collection<GenreDto> dtoCollection) {
        return dtoCollection.stream()
                .map(genreDto -> GenreDto.toEntity(genreDto, new GenreEntity()))
                .collect(Collectors.toList());
    }
}
