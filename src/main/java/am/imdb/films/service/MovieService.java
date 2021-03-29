package am.imdb.films.service;


import am.imdb.films.exception.EntityNotFoundException;
import am.imdb.films.persistence.entity.FileEntity;
import am.imdb.films.persistence.entity.MovieEntity;
import am.imdb.films.persistence.entity.relation.MovieFileEntity;
import am.imdb.films.persistence.repository.MovieFileRepository;
import am.imdb.films.persistence.repository.MovieRepository;
import am.imdb.films.service.control.CsvControl;
import am.imdb.films.service.criteria.SearchCriteria;
import am.imdb.films.service.dto.MovieDto;
import am.imdb.films.service.dto.base.BaseFileDto;
import am.imdb.films.service.dto.base.BaseMovieDto;
import am.imdb.films.service.model.csv.Movie;
import am.imdb.films.service.model.wrapper.QueryResponseWrapper;
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
    private final FileService fileService;
    private final MovieFileRepository movieFileRepository;
    private final Logger logger = LoggerFactory.getLogger(PersonService.class);

    @Autowired
    public MovieService(MovieRepository movieRepository, CsvControl<Movie> csvControl, FileService fileService, MovieFileRepository movieFileRepository) {
        this.movieRepository = movieRepository;
        this.csvControl = csvControl;
        this.fileService = fileService;
        this.movieFileRepository = movieFileRepository;
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

    public QueryResponseWrapper<BaseMovieDto> getMovies(SearchCriteria criteria) {
        Page<BaseMovieDto> content = movieRepository.findAllWithPagination(criteria.composePageRequest());
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

    public BaseFileDto addFile(MultipartFile file, Long id) {
        MovieEntity movieEntity = movieRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        FileEntity fileEntity = new FileEntity();
        fileEntity.setPath(String.format("movie/%s", id));
        fileEntity = fileService.storeFile(file, fileEntity);
        MovieFileEntity movieFileEntity = new MovieFileEntity();
        movieFileEntity.setFile(fileEntity);
        movieFileEntity.setMovie(movieEntity);
        movieFileRepository.save(movieFileEntity);

        return BaseFileDto.toBaseDto(fileEntity);
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

}
