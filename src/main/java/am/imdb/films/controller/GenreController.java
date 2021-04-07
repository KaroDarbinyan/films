package am.imdb.films.controller;


import am.imdb.films.exception.EntityNotFoundException;
import am.imdb.films.service.GenreService;
import am.imdb.films.service.dto.GenreDto;
import am.imdb.films.service.validation.model.Create;
import am.imdb.films.service.validation.model.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("genres")
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<GenreDto> addGenre(@RequestBody @Validated(Create.class) GenreDto genreDto) {
        GenreDto genre = genreService.createGenre(genreDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(genre);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreDto> getGenre(@PathVariable("id") Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(genreService.getGenre(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<GenreDto> updateGenre(
            @PathVariable("id") Long id,
            @Validated(Update.class)
            @RequestBody GenreDto genreDto) throws EntityNotFoundException {
        GenreDto genre = genreService.updateGenre(id, genreDto);

        return ResponseEntity.ok(genre);
    }

    @GetMapping
    public List<GenreDto> getGenres() {
        return genreService.getGenres();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deleteGenre(@PathVariable(value = "id") Long id) throws EntityNotFoundException {
        genreService.deleteGenre(id);
    }
}
