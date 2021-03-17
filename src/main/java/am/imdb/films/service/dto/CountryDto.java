package am.imdb.films.service.dto;


import am.imdb.films.persistence.entity.CountryEntity;
import am.imdb.films.persistence.entity.CountryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountryDto {

    private Long id;
    private String name;

    public static CountryDto toDto(CountryEntity entity) {
        return CountryDto
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public static CountryEntity toEntity(CountryDto dto) {
        return new CountryEntity(dto.getId(), dto.getName());
    }

    public static List<CountryDto> toDto(Collection<CountryEntity> entityCollection) {
        return entityCollection.stream()
                .map(CountryDto::toDto)
                .collect(Collectors.toList());
    }

    public static List<CountryEntity> toEntity(Collection<CountryDto> dtoCollection) {
        return dtoCollection.stream()
                .map(CountryDto::toEntity)
                .collect(Collectors.toList());
    }


}
