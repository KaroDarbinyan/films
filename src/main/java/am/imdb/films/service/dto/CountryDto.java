package am.imdb.films.service.dto;


import am.imdb.films.persistence.entity.CountryEntity;
import am.imdb.films.service.model.validation.Create;
import am.imdb.films.service.model.validation.Update;
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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountryDto {

    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    private Long id;
    private String name;

    public static CountryDto toDto(CountryEntity entity) {
        return CountryDto
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public static CountryEntity toEntity(CountryDto dto, CountryEntity entity) {
        if (Objects.isNull(entity.getId())) entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }

    public static List<CountryDto> toDto(Collection<CountryEntity> entityCollection) {
        return entityCollection.stream()
                .map(CountryDto::toDto)
                .collect(Collectors.toList());
    }

    public static List<CountryEntity> toEntity(Collection<CountryDto> dtoCollection) {
        return dtoCollection.stream()
                .map(countryDto -> CountryDto.toEntity(countryDto, new CountryEntity()))
                .collect(Collectors.toList());
    }

}
