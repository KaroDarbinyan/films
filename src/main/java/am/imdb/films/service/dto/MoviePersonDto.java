package am.imdb.films.service.dto;


import am.imdb.films.persistence.entity.relation.MoviePersonEntity;
import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoviePersonDto {

    private Long id;

    private Long movieId;
    private String movieCSVId;
    private Integer ordering;
    private Long personId;
    private String personCSVId;
    private String category;
    private String characters;

    public static MoviePersonDto toDto(MoviePersonEntity entity) {
        return MoviePersonDto
                .builder()
                .id(entity.getId())
//                .movieId(entity.getMovieId())
                .ordering(entity.getOrdering())
//                .personId(entity.getPersonId())
                .category(entity.getCategory())
                .characters(entity.getCharacters())
                .build();
    }

    public static MoviePersonEntity toEntity(MoviePersonDto dto) {
        MoviePersonEntity entity = new MoviePersonEntity();
        entity.setId(dto.getId());
//        entity.setMovieId(dto.getMovieId());
        entity.setOrdering(dto.getOrdering());
//        entity.setPersonId(dto.getPersonId());
        entity.setCategory(dto.getCategory());
        entity.setCharacters(dto.getCharacters());
        return entity;
    }

    public static List<MoviePersonDto> toDto(Collection<MoviePersonEntity> entityCollection) {
        return entityCollection.stream()
                .map(MoviePersonDto::toDto)
                .collect(Collectors.toList());
    }

    public static List<MoviePersonEntity> toEntity(Collection<MoviePersonDto> dtoCollection) {
        return dtoCollection.stream()
                .map(MoviePersonDto::toEntity)
                .collect(Collectors.toList());
    }


}
