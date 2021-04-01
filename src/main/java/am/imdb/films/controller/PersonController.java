package am.imdb.films.controller;


import am.imdb.films.exception.EntityNotFoundException;
import am.imdb.films.service.MoviePersonService;
import am.imdb.films.service.PersonService;
import am.imdb.films.service.criteria.SearchCriteria;
import am.imdb.films.service.dto.FileDto;
import am.imdb.films.service.dto.PersonDto;
import am.imdb.films.service.model.validation.Create;
import am.imdb.films.service.model.validation.Update;
import am.imdb.films.service.model.wrapper.QueryResponseWrapper;
import am.imdb.films.service.model.wrapper.UploadFileResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Map;
import java.util.Objects;

//@Validated
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
    public QueryResponseWrapper<PersonDto> getPersons(SearchCriteria criteria) {
        return personService.getPersons(criteria);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deletePerson(@PathVariable(value = "id") Long id) throws EntityNotFoundException {
        personService.deletePerson(id);
    }

    @PostMapping("/upload-file")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public UploadFileResponseWrapper uploadFile(@RequestParam("file") MultipartFile file,
                                                @RequestParam("personId") Long personId) {

        FileDto fileDto = personService.addFile(file, personId);

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
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> uploadCSVFile(@RequestParam(name = "file") MultipartFile csvFile) throws Exception {


        if (csvFile.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Required request part 'file' is not present"));
        }
        if (!Objects.equals(csvFile.getContentType(), "text/csv")) {
            return ResponseEntity.badRequest().body(Map.of("message", "The file must be in csv format"));
        }

        Map<String, Integer> result = personService.parseCsv(csvFile);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/movies/import-from-csv-file")
    @PreAuthorize("hasAnyRole('ADMIN')")
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


//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//        return errors;
//    }

//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public Map<String, String> handleResourceNotFoundException(ResourceNotFoundException ex) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//        return Map.of("message", ex.getMessage());
//    }
}
