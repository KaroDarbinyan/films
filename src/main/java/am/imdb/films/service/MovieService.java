package am.imdb.films.service;


import am.imdb.films.exception.EntityNotFoundException;
import am.imdb.films.persistence.entity.MovieEntity;
import am.imdb.films.persistence.repository.MovieRepository;
import am.imdb.films.service.control.CsvControl;
import am.imdb.films.service.criteria.SearchCriteria;
import am.imdb.films.service.dto.MovieDto;
import am.imdb.films.service.model.csv.Movie;
import am.imdb.films.service.model.wrapper.MoviesWrapper;
import am.imdb.films.service.model.wrapper.QueryResponseWrapper;
import am.imdb.films.util.parser.MovieParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final CsvControl<Movie> csvControl;
    private final GenreService genreService;
    private final CountryService countryService;
    private final LanguageService languageService;
    private final MovieParser movieParser;
    private final Logger logger = LoggerFactory.getLogger(PersonService.class);

    @Autowired
    public MovieService(MovieRepository movieRepository, CsvControl<Movie> csvControl, GenreService genreService, CountryService countryService, LanguageService languageService, MovieParser movieParser) {
        this.movieRepository = movieRepository;
        this.csvControl = csvControl;
        this.genreService = genreService;
        this.countryService = countryService;
        this.languageService = languageService;
        this.movieParser = movieParser;
    }

    public MovieDto createMovie(MovieDto movieDto) {
        MovieEntity movieEntity = MovieDto.toEntity(movieDto, new MovieEntity());
        movieEntity = movieRepository.save(movieEntity);
        return MovieDto.toDto(movieEntity);
    }

    public MovieDto getMovie(Long id) throws EntityNotFoundException {
        MovieEntity movie = movieRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return MovieDto.toDto(movie);
    }

    public MovieDto updateMovie(Long id, MovieDto movieDto) throws EntityNotFoundException {
        MovieEntity movieEntity = movieRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        MovieDto.toEntity(movieDto, movieEntity);
        return MovieDto.toDto(movieRepository.save(movieEntity));
    }

    public QueryResponseWrapper<MoviesWrapper> getMovies(SearchCriteria criteria) {
        Page<MoviesWrapper> content = movieRepository.findAllWithPagination(criteria.composePageRequest());
        return new QueryResponseWrapper<>(content);
    }

    public void deleteMovie(Long id) throws EntityNotFoundException {
        movieRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        movieRepository.deleteById(id);
    }

    public Map<String, Integer> parseCsv(MultipartFile csvFile) {
        Set<String> allMoviesImdbId = movieRepository.findAllMoviesImdbId();
        List<List<Movie>> movies = csvControl.getEntitiesFromCsv(csvFile, Movie.class);
        AtomicInteger existed = new AtomicInteger();

        List<List<MovieEntity>> movieEntitiesList = movies.stream()
                .map(movieList -> movieList.stream()
                        .filter(movie -> {
                            boolean contains = allMoviesImdbId.contains(movie.getImdbId());
                            if (contains) existed.getAndIncrement();
                            return !contains;
                        })
                        .map(Movie::toEntity)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        int saved = 0;
        for (List<MovieEntity> movieEntities : movieEntitiesList) {
            saved += movieRepository.saveAll(movieEntities).size();
        }

        return Map.of("saved", saved, "existed", existed.intValue());
    }


    public Map<String, Integer> batchInsert(List<MovieDto> movieDtoList) {
        int existed = (int) movieRepository.count();
        int size = movieDtoList.size();
        int notPreserved = 0;
        int counter = 0;
        int saved = 0;

        List<MovieEntity> tempMovies = new ArrayList<>();

        for (MovieDto movieDto : movieDtoList) {
            MovieEntity movieEntity = MovieDto.toEntity(movieDto, new MovieEntity());
//            movieEntity.setGenres(genreService.getByNameIn(normalizeList(movieDto.getGenreNames())));
//            movieEntity.setCountries(countryService.getByNameIn(normalizeList(movieDto.getCountryNames())));
//            movieEntity.setLanguages(languageService.getByNameIn(normalizeList(movieDto.getLanguageNames())));
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


    public List<MovieDto> getMovies() {
        List<MovieEntity> movies = movieRepository.findAll();
        return MovieDto.toDto(movies);
    }

    public MovieEntity findMovieByImdbId(String movieId) {
        return movieRepository.findByImdbId(movieId);
    }

//    public Map<String, Integer> parseCSV(MultipartFile csvFile) throws IOException {
//        List<MovieDto> movieDtoList = movieParser.csvParser(csvFile);
//        return batchInsert(movieDtoList);
//    }

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
