package am.imdb.films.service.dto;


import am.imdb.films.persistence.entity.MovieEntity;
import am.imdb.films.persistence.entity.UserEntity;
import am.imdb.films.persistence.entity.relation.MovieFileEntity;
import am.imdb.films.persistence.entity.relation.UserFileEntity;
import am.imdb.films.service.dto.base.BaseFileDto;
import am.imdb.films.service.dto.base.BaseMovieDto;
import am.imdb.films.service.dto.base.BaseMovieDto;
import am.imdb.films.service.model.validation.Create;
import am.imdb.films.service.model.validation.Update;
import com.opencsv.bean.CsvBindAndSplitByName;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MovieDto extends BaseMovieDto {

    private List<BaseFileDto> files;

    public static MovieDto toDto(MovieEntity entity) {
        MovieDto movieDto = (MovieDto) BaseMovieDto.toBaseDto(entity);
        movieDto.setFiles(entity.getListOfMovieFile()
                .stream()
                .map(MovieFileEntity::getFile)
                .map(BaseFileDto::toBaseDto)
                .collect(Collectors.toList())
        );
        return movieDto;
    }


    public static List<MovieDto> toDtoList(Collection<MovieEntity> entityCollection) {
        return entityCollection.stream()
                .map(MovieDto::toDto)
                .collect(Collectors.toList());
    }

}
