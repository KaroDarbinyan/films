package am.imdb.films.util.parser;

import am.imdb.films.persistence.entity.MovieEntity;
import am.imdb.films.service.MovieService;
import am.imdb.films.service.PersonService;
import am.imdb.films.service.dto.MoviePersonDto;
import am.imdb.films.service.dto.RatingDto;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MoviePersonParser {

    private final MovieService movieService;
    private final PersonService personService;

    public MoviePersonParser(MovieService movieService, PersonService personService) {
        this.movieService = movieService;
        this.personService = personService;
    }

    public List<MoviePersonDto> csvParser(MultipartFile csvFile) throws IOException {

        try (Reader reader = new BufferedReader(new InputStreamReader(csvFile.getInputStream()))) {
            List<MoviePersonDto> parse = new CsvToBeanBuilder<MoviePersonDto>(reader)
                    .withType(MoviePersonDto.class)
                    .withIgnoreEmptyLine(true)
                    .withIgnoreQuotations(true)
                    .withThrowExceptions(false)
                    .build()
                    .parse();

//            List<MovieEntity> all = movieService.findAll();

//            Map<String, String> collect = all.stream().collect(Collectors.toMap(MovieEntity::getImdbId, MovieEntity::getDescription));
//            Map<String, String> map = collect;


//            parse.stream()
//                    .filter(moviePersonDto -> al)

            return parse;
        } catch (IOException e) {
            throw new FileNotFoundException("file not found");
        }
    }

}
