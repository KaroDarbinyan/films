package am.imdb.films.controller;


import am.imdb.films.exception.EntityNotFoundException;
import am.imdb.films.service.GenreService;
import am.imdb.films.service.criteria.SearchCriteria;
import am.imdb.films.service.dto.GenreDto;
import am.imdb.films.service.model.validation.Create;
import am.imdb.films.service.model.validation.Update;
import am.imdb.films.service.model.wrapper.QueryResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("genres")
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @PostMapping
    public ResponseEntity<GenreDto> addGenre(@RequestBody @Validated(Create.class) GenreDto genreDto) {
        GenreDto genre = genreService.createGenre(genreDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(genre);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreDto> getGenre(@PathVariable("id") Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(genreService.getGenre(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenreDto> updateGenre(
            @PathVariable("id") Long id,
            @Validated(Update.class)
            @RequestBody GenreDto genreDto) throws EntityNotFoundException {
        GenreDto genre = genreService.updateGenre(id, genreDto);

        return ResponseEntity.ok(genre);
    }

    @GetMapping
    public QueryResponseWrapper<GenreDto> getGenres(SearchCriteria criteria) {
        return genreService.getGenres(criteria);
    }

    @DeleteMapping("/{id}")
    public void deleteGenre(@PathVariable(value = "id") Long id) throws EntityNotFoundException {
        genreService.deleteGenre(id);
    }
}
