package am.imdb.films.controller;


import am.imdb.films.persistence.entity.MovieEntity;
import am.imdb.films.persistence.repository.GenreRepository;
import am.imdb.films.service.MovieService;
import am.imdb.films.service.dto.MovieDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RestController
@RequestMapping("movies")
public class MovieController {

    private final MovieService movieService;
    private final GenreRepository genreRepository;

    @Autowired
    public MovieController(MovieService movieService, GenreRepository genreRepository) {
        this.movieService = movieService;
        this.genreRepository = genreRepository;
    }


    @GetMapping()
    public List<MovieDto> getMovies() {
        return movieService.getMovies();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovie(@PathVariable() Long id) throws Exception {
        return ResponseEntity.ok(movieService.getMovie(id));
    }


    @PostMapping("/import-from-csv-file")
    public ResponseEntity<Map<String, Integer>> uploadCSVFile(@RequestParam(name = "file") MultipartFile csvFile) throws Exception {

        if (csvFile.isEmpty()) {
            ResponseEntity.badRequest().body(Map.of("message", "Required request part 'file' is not present"));
        }
        if (!Objects.equals(csvFile.getContentType(), "text/csv")) {
            ResponseEntity.badRequest().body(Map.of("message", "The file must be in csv format"));
        }

        Map<String, Integer> result = movieService.parseCSV(csvFile);
        return ResponseEntity.ok().body(result);
    }


}
