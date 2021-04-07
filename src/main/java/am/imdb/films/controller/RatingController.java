package am.imdb.films.controller;


import am.imdb.films.exception.EntityNotFoundException;
import am.imdb.films.service.RatingService;
import am.imdb.films.service.criteria.SearchCriteria;
import am.imdb.films.service.dto.RatingDto;
import am.imdb.films.service.model.wrapper.QueryResponseWrapper;
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

import static am.imdb.films.service.validation.model.FileExtension.CSV;

@RestController
@RequestMapping("ratings")
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    public ResponseEntity<RatingDto> addRating(@RequestBody @Validated(Create.class) RatingDto ratingDto) {
        RatingDto rating = ratingService.createRating(ratingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(rating);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingDto> getRating(@PathVariable("id") Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(ratingService.getRating(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RatingDto> updateRating(
            @PathVariable("id") Long id,
            @Validated(Update.class)
            @RequestBody RatingDto ratingDto) throws EntityNotFoundException {
        RatingDto rating = ratingService.updateRating(id, ratingDto);

        return ResponseEntity.ok(rating);
    }

    @GetMapping
    public QueryResponseWrapper<RatingDto> getRatings(SearchCriteria criteria) {
        return ratingService.getRatings(criteria);
    }

    @DeleteMapping("/{id}")
    public void deleteRating(@PathVariable(value = "id") Long id) throws EntityNotFoundException {
        ratingService.deleteRating(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/import-from-csv")
    public ResponseEntity<?> importPersonFromCsv(
            @RequestParam(value = "file")
            @NotNull(message = "Required request part 'file' is not present")
            @UploadFileExtension(extensions = CSV) MultipartFile file
    ) throws Exception {
        Map<String, Integer> result = ratingService.parseCsv(file);
        return ResponseEntity.ok().body(result);
    }


}
