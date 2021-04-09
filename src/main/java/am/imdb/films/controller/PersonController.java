package am.imdb.films.controller;


import am.imdb.films.exception.EntityNotFoundException;
import am.imdb.films.service.MoviePersonService;
import am.imdb.films.service.PersonService;
import am.imdb.films.service.criteria.PersonSearchCriteria;
import am.imdb.films.service.criteria.SearchCriteria;
import am.imdb.films.service.dto.PersonDto;
import am.imdb.films.service.model.wrapper.QueryResponseWrapper;
import am.imdb.films.service.model.wrapper.UploadFileResponseWrapper;
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

import static am.imdb.films.service.validation.model.FileExtension.*;

@Validated
@RestController
@RequestMapping("persons")
public class PersonController {

    private final PersonService personService;
    private final MoviePersonService moviePersonService;

    @Autowired
    public PersonController(PersonService personService, MoviePersonService moviePersonService) {
        this.personService = personService;
        this.moviePersonService = moviePersonService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<PersonDto> addPerson(@RequestBody @Validated(Create.class) PersonDto personDto) {
        PersonDto person = personService.createPerson(personDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(person);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> getPerson(@PathVariable("id") Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(personService.getPerson(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<PersonDto> updatePerson(
            @PathVariable("id") Long id,
            @Validated(Update.class)
            @RequestBody PersonDto personDto) throws EntityNotFoundException {
        PersonDto person = personService.updatePerson(id, personDto);

        return ResponseEntity.ok(person);
    }

    @GetMapping
    public QueryResponseWrapper<PersonDto> getPersons(PersonSearchCriteria criteria) {
        return personService.getPersons(criteria);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deletePerson(@PathVariable(value = "id") Long id) throws EntityNotFoundException {
        personService.deletePerson(id);
    }

    @PostMapping("/{id}/images")
    public UploadFileResponseWrapper uploadImage(
            @RequestParam(value = "image")
            @UploadFileExtension(extensions = {JPEG, JPG, PNG, SVG, PNG}) MultipartFile image,
            @PathVariable("id") Long id) {

        return personService.addFile(image, id);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/import-from-csv")
    public ResponseEntity<?> importPersonFromCsv(
            @RequestParam(value = "file")
            @NotNull(message = "Required request part 'file' is not present")
            @UploadFileExtension(extensions = CSV) MultipartFile file
    ) throws Exception {
        Map<String, Integer> result = personService.parseCsv(file);
        return ResponseEntity.ok().body(result);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/movies/import-from-csv")
    public ResponseEntity<Map<String, Integer>> importMovieFromCsv(
            @RequestParam(value = "file")
            @NotNull(message = "Required request part 'file' is not present")
            @UploadFileExtension(extensions = CSV) MultipartFile file
    ) throws Exception {
        Map<String, Integer> result = moviePersonService.parseCsv(file);
        return ResponseEntity.ok().body(result);
    }
}
