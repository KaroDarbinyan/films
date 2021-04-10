package am.imdb.films.service;

import am.imdb.films.service.control.CsvControl;
import am.imdb.films.util.model.csv.MoviePerson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MoviePersonService {

    private final PersonService personService;
    private final MovieService movieService;
    private final JdbcTemplate jdbcTemplate;
    private final CsvControl<MoviePerson> csvControl;


    @Autowired
    public MoviePersonService(PersonService personService, MovieService movieService, JdbcTemplate jdbcTemplate, CsvControl<MoviePerson> csvControl) {
        this.personService = personService;
        this.movieService = movieService;
        this.jdbcTemplate = jdbcTemplate;
        this.csvControl = csvControl;
    }

    public Map<String, Integer> parseCsv(MultipartFile csvFile) {

        String insertQuery = "INSERT INTO movie_person (movie_id, ordering, person_id, category, job, characters) VALUES %s;";
        List<List<MoviePerson>> ratings = csvControl.getEntitiesFromCsv(csvFile, MoviePerson.class);
        Map<String, Long> moviesImdbIdsAndIds = movieService.getMoviesImdbIdsAndIds();
        Map<String, Long> personsImdbIdsAndIds = personService.getPersonsImdbIdsAndIds();
        AtomicInteger size = new AtomicInteger();
        AtomicInteger notPersonOrMovie = new AtomicInteger();


        List<List<String>> valuesList = ratings.stream()
                .map(moviePersonList -> moviePersonList.stream()
                        .filter(moviePerson -> {
                            size.getAndIncrement();
                            boolean contains = Objects.nonNull(moviePerson.getMovieId()) && Objects.nonNull(moviePerson.getPersonId())
                                    && moviesImdbIdsAndIds.containsKey(moviePerson.getMovieId())
                                    && personsImdbIdsAndIds.containsKey(moviePerson.getPersonId());
                            if (!contains) notPersonOrMovie.getAndIncrement();
                            return contains;
                        })
                        .map(moviePerson -> String.format("(%s, %s, %s, '%s', '%s', '%s')",
                                moviesImdbIdsAndIds.get(moviePerson.getMovieId()),
                                moviePerson.getOrdering(),
                                personsImdbIdsAndIds.get(moviePerson.getPersonId()),
                                moviePerson.getCategory(),
                                moviePerson.getJob().replaceAll("'", "''"),
                                moviePerson.getCharacters().replaceAll("'", "''")
                        ))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        int saved = 0;
        for (List<String> values : valuesList) {
            String sql = String.format(insertQuery, String.join(",", values));
            jdbcTemplate.execute(sql);
            saved += values.size();
        }

        return Map.of(
                "size", size.intValue(),
                "saved", saved,
                "not_person_or_movie", notPersonOrMovie.intValue()
        );
    }
}
