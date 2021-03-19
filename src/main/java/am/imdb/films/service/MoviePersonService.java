package am.imdb.films.service;

import am.imdb.films.persistence.entity.MovieEntity;
import am.imdb.films.persistence.entity.PersonEntity;
import am.imdb.films.persistence.repository.MoviePersonRepository;
import am.imdb.films.service.dto.MoviePersonDto;
import am.imdb.films.service.dto.RatingDto;
import am.imdb.films.util.parser.MoviePersonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MoviePersonService {

    private final MoviePersonRepository moviePersonRepository;
    private final PersonService personService;
    private final MovieService movieService;
    private final JdbcTemplate jdbcTemplate;
    private final MoviePersonParser moviePersonParser;
    private final Logger logger = LoggerFactory.getLogger(MoviePersonService.class);

    @Autowired
    public MoviePersonService(MoviePersonRepository moviePersonRepository, PersonService personService, MovieService movieService, JdbcTemplate jdbcTemplate, MoviePersonParser moviePersonParser) {
        this.moviePersonRepository = moviePersonRepository;
        this.personService = personService;
        this.movieService = movieService;
        this.jdbcTemplate = jdbcTemplate;
        this.moviePersonParser = moviePersonParser;
    }


    public Map<String, Integer> parseCSV(MultipartFile csvFile) throws IOException {
        List<MoviePersonDto> moviePersonDtoList = moviePersonParser.csvParser(csvFile);
        return batchInsert(moviePersonDtoList);
    }

    private Map<String, Integer> batchInsert(List<MoviePersonDto> moviePersonDtoList) {
        String insertQuery = "INSERT INTO movie_person (movie_id, ordering, person_id, category, job, characters) VALUES %s;";
        int existed = (int) moviePersonRepository.count();
        int size = moviePersonDtoList.size();
        int counter = 0;
        int error = 0;
        int saved = 0;

        List<MoviePersonDto> list = new ArrayList<>();
        List<String> values = new ArrayList<>();
        Map<String, MoviePersonDto> tempMovies = new HashMap<>();
        Map<String, MoviePersonDto> tempPersons = new HashMap<>();

        for (MoviePersonDto moviePersonDto : moviePersonDtoList) {
            list.add(moviePersonDto);
            tempMovies.put(moviePersonDto.getMovieCSVId(), moviePersonDto);
            tempPersons.put(moviePersonDto.getPersonCSVId(), moviePersonDto);

            if ((counter + 1) % 1000 == 0 || (counter + 1) == size) {

                try {
                    Map<String, Long> movieMap = movieService.getByImdbIdIn(new ArrayList<>(tempMovies.keySet()))
                            .stream().collect(Collectors.toMap(MovieEntity::getImdbId, MovieEntity::getId));
                    Map<String, Long> personMap = personService.getByImdbIdIn(new ArrayList<>(tempPersons.keySet()))
                            .stream().collect(Collectors.toMap(PersonEntity::getImdbId, PersonEntity::getId));

                    for (MoviePersonDto dto : list) {
                        if (movieMap.containsKey(dto.getMovieCSVId()) && personMap.containsKey(dto.getPersonCSVId())) {
                            values.add(String.format("(%s, %s, %s, '%s', '%s', '%s')",
                                    movieMap.get(dto.getMovieCSVId()),
                                    dto.getOrdering(),
                                    personMap.get(dto.getPersonCSVId()),
                                    dto.getCategory(),
                                    dto.getJob(),
                                    dto.getCharacters().replaceAll("'", "''")));
                        }
                    }
                    jdbcTemplate.execute(String.format(insertQuery, String.join(",", values)));

                    saved += values.size();
                } catch (Exception e) {
                    error += values.size();
                    logger.error(String.format("%s not preserved", error));
                }
                values.clear();
                list.clear();
                tempMovies.clear();
                tempPersons.clear();
                logger.info(String.format("In progress, [saved -> %s], [existed -> %s], [not preserved -> %s] of %s",
                        saved, existed, error, size));
            }

            counter++;
        }

        return Map.of(
                "size", size,
                "saved", saved,
                "error", error,
                "existed", existed,
                "not_preserved", size - saved
        );

    }
}
