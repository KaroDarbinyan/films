package am.imdb.films.service;


import am.imdb.films.exception.EntityNotFoundException;
import am.imdb.films.persistence.entity.*;
import am.imdb.films.persistence.entity.relation.MovieCountryEntity;
import am.imdb.films.persistence.entity.relation.MovieFileEntity;
import am.imdb.films.persistence.entity.relation.MovieGenreEntity;
import am.imdb.films.persistence.entity.relation.MovieLanguageEntity;
import am.imdb.films.persistence.repository.*;
import am.imdb.films.service.control.CsvControl;
import am.imdb.films.service.criteria.SearchCriteria;
import am.imdb.films.service.dto.base.BaseFileDto;
import am.imdb.films.service.dto.base.BaseMovieDto;
import am.imdb.films.service.model.csv.Movie;
import am.imdb.films.service.model.map.MapEntityKeys;
import am.imdb.films.service.model.wrapper.QueryResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final CsvControl<Movie> csvControl;
    private final FileService fileService;
    private final MovieFileRepository movieFileRepository;
    private final LanguageRepository languageRepository;
    private final CountryRepository countryRepository;
    private final GenreRepository genreRepository;
    private final MovieGenreRepository movieGenreRepository;
    private final MovieLanguageRepository movieLanguageRepository;
    private final MovieCountryRepository movieCountryRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository,
                        CsvControl<Movie> csvControl,
                        FileService fileService,
                        MovieFileRepository movieFileRepository,
                        LanguageRepository languageRepository,
                        CountryRepository countryRepository,
                        GenreRepository genreRepository,
                        MovieGenreRepository movieGenreRepository,
                        MovieLanguageRepository movieLanguageRepository,
                        MovieCountryRepository movieCountryRepository) {
        this.movieRepository = movieRepository;
        this.csvControl = csvControl;
        this.fileService = fileService;
        this.movieFileRepository = movieFileRepository;
        this.languageRepository = languageRepository;
        this.countryRepository = countryRepository;
        this.genreRepository = genreRepository;
        this.movieGenreRepository = movieGenreRepository;
        this.movieLanguageRepository = movieLanguageRepository;
        this.movieCountryRepository = movieCountryRepository;
    }


    public BaseMovieDto createMovie(BaseMovieDto baseMovieDto) {
        MovieEntity movieEntity = BaseMovieDto.toEntity(baseMovieDto, new MovieEntity());
        movieEntity = movieRepository.save(movieEntity);
        return BaseMovieDto.toBaseDto(movieEntity);
    }

    public BaseMovieDto getMovie(Long id) throws EntityNotFoundException {
        MovieEntity movie = movieRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return BaseMovieDto.toBaseDto(movie);
    }

    public BaseMovieDto updateMovie(Long id, BaseMovieDto baseMovieDto) throws EntityNotFoundException {
        MovieEntity movieEntity = movieRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        BaseMovieDto.toEntity(baseMovieDto, movieEntity);
        return BaseMovieDto.toBaseDto(movieRepository.save(movieEntity));
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
        AtomicInteger existed = new AtomicInteger();
        Set<String> allMoviesImdbId = movieRepository.findAllMoviesImdbId();
        List<List<Movie>> movies = csvControl.getEntitiesFromCsv(csvFile, Movie.class);
        Set<LanguageEntity> languages = new HashSet<>(languageRepository.findAll());
        Set<GenreEntity> genres = new HashSet<>(genreRepository.findAll());
        Set<CountryEntity> countries = new HashSet<>(countryRepository.findAll());

        movies.forEach(movieList -> movieList.forEach(movie -> {
            movie.setLanguageNames(normalizeList(movie.getLanguageNames()));
            movie.setGenreNames(normalizeList(movie.getGenreNames()));
            movie.setCountryNames(normalizeList(movie.getCountryNames()));
            languages.addAll(movie.getLanguageNames().stream().map(LanguageEntity::new).collect(Collectors.toSet()));
            genres.addAll(movie.getGenreNames().stream().map(GenreEntity::new).collect(Collectors.toSet()));
            countries.addAll(movie.getCountryNames().stream().map(CountryEntity::new).collect(Collectors.toSet()));
        }));

        List<LanguageEntity> languageEntities = languageRepository.saveAll(languages);
        List<GenreEntity> genreEntities = genreRepository.saveAll(genres);
        List<CountryEntity> countryEntities = countryRepository.saveAll(countries);

        List<List<MovieEntity>> movieEntitiesList = movies.stream()
                .map(movieList -> movieList.stream()
                        .filter(movie -> {
                            boolean contains = allMoviesImdbId.contains(movie.getImdbId());
                            if (contains) existed.getAndIncrement();
                            return !contains;
                        })
                        .map(movie -> {
                            MovieEntity movieEntity = Movie.toEntity(movie);
                            List<MovieLanguageEntity> movieLanguageEntityList = languageEntities.stream().filter(entity -> movie.getLanguageNames().contains(entity.getName()))
                                    .map(languageEntity -> new MovieLanguageEntity(movieEntity, languageEntity)).collect(Collectors.toList());
                            List<MovieGenreEntity> movieGenreEntityList = genreEntities.stream().filter(entity -> movie.getGenreNames().contains(entity.getName()))
                                    .map(genreEntity -> new MovieGenreEntity(movieEntity, genreEntity)).collect(Collectors.toList());
                            List<MovieCountryEntity> movieCountryEntityList = countryEntities.stream().filter(entity -> movie.getCountryNames().contains(entity.getName()))
                                    .map(countryEntity -> new MovieCountryEntity(movieEntity, countryEntity)).collect(Collectors.toList());

                            movieEntity.setListOfMovieLanguage(movieLanguageEntityList);
                            movieEntity.setListOfMovieGenre(movieGenreEntityList);
                            movieEntity.setListOfMovieCountry(movieCountryEntityList);

                            return movieEntity;
                        })
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        int saved = 0;

        for (List<MovieEntity> movieEntities : movieEntitiesList) {
            List<MovieEntity> movieEntityList = movieRepository.saveAll(movieEntities);
            saveRelations(movieEntityList);
            saved += movieEntityList.size();
        }

        return Map.of("saved", saved, "existed", existed.intValue());
    }

    private void saveRelations(List<MovieEntity> movieEntities) {
        List<MovieGenreEntity> movieGenreEntityList = new ArrayList<>();
        List<MovieLanguageEntity> movieLanguageEntityList = new ArrayList<>();
        List<MovieCountryEntity> movieCountryEntityList = new ArrayList<>();
        movieEntities.forEach(entity -> {
            movieGenreEntityList.addAll(entity.getListOfMovieGenre().stream().peek(movieGenreEntity -> movieGenreEntity.getMovie().setId(entity.getId())).collect(Collectors.toList()));
            movieLanguageEntityList.addAll(entity.getListOfMovieLanguage().stream().peek(movieLanguageEntity -> movieLanguageEntity.getMovie().setId(entity.getId())).collect(Collectors.toList()));
            movieCountryEntityList.addAll(entity.getListOfMovieCountry().stream().peek(movieCountryEntity -> movieCountryEntity.getMovie().setId(entity.getId())).collect(Collectors.toList()));
        });

        movieGenreRepository.saveAll(movieGenreEntityList);
        movieCountryRepository.saveAll(movieCountryEntityList);
        movieLanguageRepository.saveAll(movieLanguageEntityList);

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

    public Map<String, Long> getMoviesImdbIdsAndIds() {
        List<MapEntityKeys<Long, String>> list = movieRepository.findAllMovieImdbIdsAndIds();

        return new MapEntityKeys<Long, String>().toReverseMap(list);
    }
}
