package am.imdb.films.controller;


import am.imdb.films.exception.EntityNotFoundException;
import am.imdb.films.service.MoviePersonService;
import am.imdb.films.service.MovieService;
import am.imdb.films.service.criteria.SearchCriteria;
import am.imdb.films.service.dto.MovieDto;
import am.imdb.films.service.dto.base.BaseFileDto;
import am.imdb.films.service.dto.base.BaseMovieDto;
import am.imdb.films.service.model.validation.Create;
import am.imdb.films.service.model.validation.Update;
import am.imdb.films.service.model.wrapper.QueryResponseWrapper;
import am.imdb.films.service.model.wrapper.UploadFileResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("movies")
public class MovieController {

    private final MovieService movieService;
    private final MoviePersonService moviePersonService;

    @Autowired
    public MovieController(MovieService movieService, MoviePersonService moviePersonService) {
        this.movieService = movieService;
        this.moviePersonService = moviePersonService;
    }

    @PostMapping
    public ResponseEntity<BaseMovieDto> addMovie(@RequestBody @Validated(Create.class) BaseMovieDto baseMovieDto) {
        BaseMovieDto movie = movieService.createMovie(baseMovieDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(movie);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseMovieDto> getMovie(@PathVariable() Long id) throws EntityNotFoundException {
        Map<String, Long> moviesImdbIdsAndIds = movieService.getMoviesImdbIdsAndIds();
        return ResponseEntity.ok(movieService.getMovie(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseMovieDto> updateMovie(
            @PathVariable("id") Long id,
            @Validated(Update.class)
            @RequestBody BaseMovieDto baseMovieDto) throws EntityNotFoundException {
        BaseMovieDto movie = movieService.updateMovie(id, baseMovieDto);

        return ResponseEntity.ok(movie);
    }

    @GetMapping
    public QueryResponseWrapper<BaseMovieDto> getMovies(SearchCriteria criteria) {
        return movieService.getMovies(criteria);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable(value = "id") Long id) throws EntityNotFoundException {
        movieService.deleteMovie(id);
    }

    @PostMapping("/upload-file")
    public UploadFileResponseWrapper uploadFile(@RequestParam("file") MultipartFile file,
                                                @RequestParam("movieId") Long movieId) {

        BaseFileDto fileDto = movieService.addFile(file, movieId);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/")
                .path(fileDto.getId().toString())
                .toUriString();

        return UploadFileResponseWrapper.builder()
                .fileName(fileDto.getFileName())
                .fileDownloadUri(fileDownloadUri)
                .fileType(file.getContentType())
                .size(file.getSize())
                .build();
    }


    @PostMapping("/import-from-csv-file")
    public ResponseEntity<Map<String, Integer>> uploadCSVFile(@RequestParam(name = "file") MultipartFile csvFile) throws Exception {

        if (csvFile.isEmpty()) {
            ResponseEntity.badRequest().body(Map.of("message", "Required request part 'file' is not present"));
        }
        if (!Objects.equals(csvFile.getContentType(), "text/csv")) {
            ResponseEntity.badRequest().body(Map.of("message", "The file must be in csv format"));
        }

        Map<String, Integer> result = movieService.parseCsv(csvFile);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/persons/import-from-csv-file")
    public ResponseEntity<Map<String, Integer>> uploadCSVFiles(@RequestParam(name = "file") MultipartFile csvFile) throws Exception {

        if (csvFile.isEmpty()) {
            ResponseEntity.badRequest().body(Map.of("message", "Required request part 'file' is not present"));
        }
        if (!Objects.equals(csvFile.getContentType(), "text/csv")) {
            ResponseEntity.badRequest().body(Map.of("message", "The file must be in csv format"));
        }

        Map<String, Integer> result = moviePersonService.parseCsv(csvFile);
        return ResponseEntity.ok().body(result);
    }


}
