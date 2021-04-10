package am.imdb.films.service;


import am.imdb.films.exception.EntityNotFoundException;
import am.imdb.films.persistence.entity.*;
import am.imdb.films.persistence.entity.relation.*;
import am.imdb.films.persistence.repository.*;
import am.imdb.films.service.control.CsvControl;
import am.imdb.films.service.criteria.MovieSearchCriteria;
import am.imdb.films.service.dto.MovieDto;
import am.imdb.films.util.model.csv.Movie;
import am.imdb.films.service.model.resultset.MapEntityKeys;
import am.imdb.films.service.model.wrapper.QueryResponseWrapper;
import am.imdb.films.service.model.wrapper.UploadFileResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MovieService {

    @Value("${file.upload-dir}")
    String uploadDir;

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
    private final UserFavoriteRepository userFavoriteRepository;
    private final UserRepository userRepository;

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
                        MovieCountryRepository movieCountryRepository, UserFavoriteRepository userFavoriteRepository, UserRepository userRepository) {
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
        this.userFavoriteRepository = userFavoriteRepository;
        this.userRepository = userRepository;
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

    public QueryResponseWrapper<MovieDto> getMovies(MovieSearchCriteria criteria) {
        Page<MovieEntity> content = movieRepository.findAllWithPagination(criteria, criteria.composePageRequest());

        return new QueryResponseWrapper<>(content.map(MovieDto::toDto));
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

    public UploadFileResponseWrapper addFile(MultipartFile file, Long id) {
        MovieEntity movieEntity = movieRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        FileEntity entity = new FileEntity();

        entity.setPath(String.format("movie/%s", id));
        entity.setContentType(file.getContentType());
        entity.setExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
        entity.setFileName(String.join(".", String.valueOf(System.currentTimeMillis()), entity.getExtension()));

        String uploadPath = Paths.get(String.join(File.separator, uploadDir, entity.getPath(), entity.getFileName()))
                .normalize().toString();

        fileService.storeFile(file, uploadPath);
        FileEntity fileEntity = fileService.save(entity);

        MovieFileEntity movieFileEntity = new MovieFileEntity();
        movieFileEntity.setFile(fileEntity);
        movieFileEntity.setMovie(movieEntity);
        movieFileRepository.save(movieFileEntity);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/")
                .path(fileEntity.getId().toString())
                .toUriString();

        return UploadFileResponseWrapper.builder()
                .fileName(fileEntity.getFileName())
                .fileDownloadUri(fileDownloadUri)
                .fileType(fileEntity.getContentType())
                .size(file.getSize())
                .build();
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

    public void addFavorite(Long userId, Long movieId) {
        MovieEntity movieEntity = movieRepository.findById(movieId).orElseThrow(EntityNotFoundException::new);
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        UserFavoriteEntity userFavoriteEntity = new UserFavoriteEntity();
        userFavoriteEntity.setUser(userEntity);
        userFavoriteEntity.setMovie(movieEntity);

        userFavoriteRepository.save(userFavoriteEntity);
    }
}
