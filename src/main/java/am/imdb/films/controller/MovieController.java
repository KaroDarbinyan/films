package am.imdb.films.controller;


import am.imdb.films.exception.EntityNotFoundException;
import am.imdb.films.security.config.session.SessionUser;
import am.imdb.films.service.MoviePersonService;
import am.imdb.films.service.MovieService;
import am.imdb.films.service.criteria.MovieSearchCriteria;
import am.imdb.films.service.dto.MovieDto;
import am.imdb.films.service.model.wrapper.MovieWrapper;
import am.imdb.films.service.model.wrapper.QueryResponseWrapper;
import am.imdb.films.service.model.resultset.UploadFileResponseWrapper;
import am.imdb.films.service.validation.model.Create;
import am.imdb.films.service.validation.model.Update;
import am.imdb.films.service.validation.validator.fileextension.UploadFileExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.Map;

import static am.imdb.films.security.config.session.SessionUser.SESSION_USER_KEY;
import static am.imdb.films.service.validation.model.FileExtension.*;

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
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<MovieDto> addMovie(@RequestBody @Validated(Create.class) MovieDto movieDto) {
        MovieDto movie = movieService.createMovie(movieDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(movie);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovie(@PathVariable() Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(movieService.getMovie(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<MovieDto> updateMovie(
            @PathVariable("id") Long id,
            @Validated(Update.class) @RequestBody MovieDto movieDto) throws EntityNotFoundException {
        MovieDto movie = movieService.updateMovie(id, movieDto);

        return ResponseEntity.ok(movie);
    }

    @GetMapping
    public QueryResponseWrapper<MovieWrapper> getMovies(MovieSearchCriteria criteria) {
        return movieService.getMovies(criteria);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deleteMovie(@PathVariable(value = "id") Long id) throws EntityNotFoundException {
        movieService.deleteMovie(id);
    }

    @PostMapping("/{id}/images")
    public UploadFileResponseWrapper uploadImage(
            @RequestParam(value = "image")
            @UploadFileExtension(extensions = {JPEG, JPG, PNG, SVG, PNG}) MultipartFile image,
            @PathVariable("id") Long id) {

        return movieService.addFile(image, id);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/import-from-csv")
    public ResponseEntity<?> importPersonFromCsv(
            @RequestParam(value = "file")
            @NotNull(message = "Required request part 'file' is not present")
            @UploadFileExtension(extensions = CSV) MultipartFile file
    ) throws Exception {
        Map<String, Integer> result = movieService.parseCsv(file);
        return ResponseEntity.ok().body(result);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/persons/import-from-csv")
    public ResponseEntity<Map<String, Integer>> importMovieFromCsv(
            @RequestParam(value = "file")
            @NotNull(message = "Required request part 'file' is not present")
            @UploadFileExtension(extensions = CSV) MultipartFile file
    ) throws Exception {
        Map<String, Integer> result = moviePersonService.parseCsv(file);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/{id}/add-favorite")
    public void addFavorite(@ModelAttribute(SESSION_USER_KEY) SessionUser sessionUser, @PathVariable() Long id) throws EntityNotFoundException {
        movieService.addFavorite(sessionUser.getId(), id);
    }
}
