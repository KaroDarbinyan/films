package am.imdb.films.service.dto;


import am.imdb.films.persistence.entity.GenreEntity;
import am.imdb.films.persistence.entity.GenreEntity;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
public class GenreDto {


    private Long id;
    private String name;
    private List<String> movies;

    public static GenreDto toDto(GenreEntity entity) {
        return GenreDto
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public static GenreEntity toEntity(GenreDto dto) {
        return new GenreEntity(dto.getId(), dto.getName());
    }

    public static List<GenreDto> toDto(Collection<GenreEntity> entityCollection) {
        return entityCollection.stream()
                .map(GenreDto::toDto)
                .collect(Collectors.toList());
    }

    public static List<GenreEntity> toEntity(Collection<GenreDto> dtoCollection) {
        return dtoCollection.stream()
                .map(GenreDto::toEntity)
                .collect(Collectors.toList());
    }


}
