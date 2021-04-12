package am.imdb.films.util.model.csv;


import am.imdb.films.persistence.entity.relation.MoviePersonEntity;
import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoviePerson {

    private Long id;

    @CsvBindByName(column = "movie_id")
    private String movieId;

    @CsvBindByName(column = "ordering")
    private String ordering;

    @CsvBindByName(column = "person_id")
    private String personId;

    @CsvBindByName(column = "category")
    private String category;

    @CsvBindByName(column = "job")
    private String job;

    @CsvBindByName(column = "characters")
    private String characters;


    public static MoviePersonEntity toEntity(MoviePerson moviePerson) {
        MoviePersonEntity entity = new MoviePersonEntity();

        if (Objects.nonNull(entity.getId())) entity.setId(moviePerson.getId());
        entity.setOrdering(moviePerson.getOrdering());
        entity.setCategory(moviePerson.getCategory());
        entity.setJob(moviePerson.getJob());
//        entity.setCharacters(moviePerson.getCharacters());
        return entity;
    }


    public static List<MoviePersonEntity> toEntity(Collection<MoviePerson> dtoCollection) {
        return dtoCollection.stream()
                .map(MoviePerson::toEntity)
                .collect(Collectors.toList());
    }


}
