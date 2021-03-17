package am.imdb.films.service;


import am.imdb.films.persistence.entity.MovieEntity;
import am.imdb.films.persistence.entity.PersonEntity;
import am.imdb.films.persistence.repository.MovieRepository;
import am.imdb.films.service.dto.MovieDto;
import am.imdb.films.service.dto.PersonDto;
import am.imdb.films.util.parser.MovieParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieParser movieParser;
    private final Logger logger = LoggerFactory.getLogger(PersonService.class);

    @Autowired
    public MovieService(MovieRepository movieRepository, MovieParser movieParser) {
        this.movieRepository = movieRepository;
        this.movieParser = movieParser;
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

    public Map<String, Long> parseCSV(MultipartFile csvFile) throws IOException {
        List<MovieDto> movieDtoList = movieParser.csvParser(csvFile);
        long saved = 0;
        long existed = 0;
        long notPreserved = 0;
        long size = Integer.toUnsignedLong(movieDtoList.size());
        for (MovieDto movieDto : movieDtoList) {
            MovieEntity movieEntity = movieRepository.findMovieByImdbId(movieDto.getImdbId());
            try {
                if (movieEntity == null) {
                    movieEntity = movieRepository.save(MovieDto.toEntity(movieDto));
                    ++saved;
                } else {
                    ++existed;
                }
            } catch (Exception e) {
                logger.error(String.format("%s not preserved object -> %s", ++notPreserved, movieDto.getImdbId()));
            }
            logger.info(String.format("In progress, [saved -> %s], [existed -> %s], [not preserved -> %s] of %s", saved, existed, notPreserved, size));

        }

        Map<String, Long> result = new HashMap<>();
        result.put("size", size);
        result.put("saved", saved);
        result.put("existed", existed);
        result.put("not_preserved", notPreserved);
        return result;
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

    public MovieEntity findMovieByImdbId(String movieId) {
        return movieRepository.findMovieByImdbId(movieId);
    }
}
