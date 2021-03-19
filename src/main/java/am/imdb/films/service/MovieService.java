package am.imdb.films.service;


import am.imdb.films.persistence.entity.MovieEntity;
import am.imdb.films.persistence.entity.PersonEntity;
import am.imdb.films.persistence.repository.MovieRepository;
import am.imdb.films.service.dto.MovieDto;
import am.imdb.films.service.dto.PersonDto;
import am.imdb.films.util.parser.MovieParser;
import com.sun.xml.bind.v2.runtime.output.SAXOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final GenreService genreService;
    private final CountryService countryService;
    private final LanguageService languageService;
    private final MovieParser movieParser;
    private final Logger logger = LoggerFactory.getLogger(PersonService.class);

    @Autowired
    public MovieService(MovieRepository movieRepository, GenreService genreService, CountryService countryService, LanguageService languageService, MovieParser movieParser) {
        this.movieRepository = movieRepository;
        this.genreService = genreService;
        this.countryService = countryService;
        this.languageService = languageService;
        this.movieParser = movieParser;
    }

    public MovieDto createMovie(MovieDto movieDto) {
        MovieEntity movieEntity = MovieDto.toEntity(movieDto);
        movieEntity = movieRepository.save(movieEntity);
        return MovieDto.toDto(movieEntity);
    }

    public List<MovieDto> createMovies(List<MovieDto> dtoList) {
        List<MovieEntity> movieEntities = MovieDto.toEntity(dtoList);
        return MovieDto.toDto(movieRepository.saveAll(movieEntities));
    }

    public Map<String, Integer> batchInsert(List<MovieDto> movieDtoList) {
        int existed = (int) movieRepository.count();
        int size = movieDtoList.size();
        int notPreserved = 0;
        int counter = 0;
        int saved = 0;

        List<MovieEntity> tempMovies = new ArrayList<>();

        for (MovieDto movieDto : movieDtoList) {
            MovieEntity movieEntity = MovieDto.toEntity(movieDto);
            movieEntity.setGenres(genreService.getByNameIn(normalizeList(movieDto.getGenreNames())));
            movieEntity.setCountries(countryService.getByNameIn(normalizeList(movieDto.getCountryNames())));
            movieEntity.setLanguages(languageService.getByNameIn(normalizeList(movieDto.getLanguageNames())));
            tempMovies.add(movieEntity);

            if ((counter + 1) % 1000 == 0 || (counter + 1) == size) {
                try {
                    movieRepository.saveAll(tempMovies);
                    saved += tempMovies.size();
                } catch (Exception e) {
                    notPreserved += tempMovies.size();
                    logger.error(String.format("%s not preserved", notPreserved));
                }
                tempMovies.clear();
                logger.info(String.format("In progress, [saved -> %s], [existed -> %s], [not preserved -> %s] of %s",
                        saved, existed, notPreserved, size));
            }

            counter++;
        }

        return Map.of(
                "size", size,
                "saved", saved,
                "existed", existed,
                "not_preserved", notPreserved
        );
    }

    public MovieDto getMovie(Long id) throws Exception {
        MovieEntity movie = movieRepository.findById(id)
                .orElseThrow(() -> new Exception("movie not found"));
        return MovieDto.toDto(movie);
    }

    public List<MovieDto> getMovies() {
        List<MovieEntity> movies = movieRepository.findAll();
        return MovieDto.toDto(movies);
    }

    public MovieEntity findMovieByImdbId(String movieId) {
        return movieRepository.findMovieByImdbId(movieId);
    }

    public Map<String, Integer> parseCSV(MultipartFile csvFile) throws IOException {
        List<MovieDto> movieDtoList = movieParser.csvParser(csvFile);
        return batchInsert(movieDtoList);
    }

    private Set<String> normalizeList(Set<String> names) {
        return names.stream()
                .map(name -> name.trim().replaceAll("'", "''"))
                .filter(name -> !name.isEmpty() && !name.equals("None"))
                .collect(Collectors.toSet());
    }

    public List<MovieEntity> getByImdbIdIn(List<String> imdbIds) {
        return movieRepository.findByImdbIdIn(imdbIds);
    }


    public Map<String, String> getMovieIdAndImdbId() {
        return movieRepository.findAllMovieIdAndIds();
    }

    public List<MovieEntity> findAll() {
        return movieRepository.findAll();
    }
}
