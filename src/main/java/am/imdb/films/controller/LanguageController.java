package am.imdb.films.controller;


import am.imdb.films.exception.EntityNotFoundException;
import am.imdb.films.service.LanguageService;
import am.imdb.films.service.criteria.SearchCriteria;
import am.imdb.films.service.dto.LanguageDto;
import am.imdb.films.service.model.validation.Create;
import am.imdb.films.service.model.validation.Update;
import am.imdb.films.service.model.wrapper.QueryResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("languages")
public class LanguageController {

    private final LanguageService languageService;

    @Autowired
    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @PostMapping
    public ResponseEntity<LanguageDto> addLanguage(@RequestBody @Validated(Create.class) LanguageDto languageDto) {
        LanguageDto language = languageService.createLanguage(languageDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(language);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LanguageDto> getLanguage(@PathVariable("id") Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(languageService.getLanguage(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LanguageDto> updateLanguage(
            @PathVariable("id") Long id,
            @Validated(Update.class)
            @RequestBody LanguageDto languageDto) throws EntityNotFoundException {
        LanguageDto language = languageService.updateLanguage(id, languageDto);

        return ResponseEntity.ok(language);
    }

    @GetMapping
    public QueryResponseWrapper<LanguageDto> getLanguages(SearchCriteria criteria) {
        return languageService.getLanguages(criteria);
    }

    @DeleteMapping("/{id}")
    public void deleteLanguage(@PathVariable(value = "id") Long id) throws EntityNotFoundException {
        languageService.deleteLanguage(id);
    }
}
