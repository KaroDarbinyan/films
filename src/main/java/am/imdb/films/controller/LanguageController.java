package am.imdb.films.controller;


import am.imdb.films.exception.EntityNotFoundException;
import am.imdb.films.service.LanguageService;
import am.imdb.films.service.dto.LanguageDto;
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
@RequestMapping("languages")
public class LanguageController {

    private final LanguageService languageService;

    @Autowired
    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<LanguageDto> addLanguage(@RequestBody @Validated(Create.class) LanguageDto languageDto) {
        LanguageDto language = languageService.createLanguage(languageDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(language);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LanguageDto> getLanguage(@PathVariable("id") Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(languageService.getLanguage(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<LanguageDto> updateLanguage(
            @PathVariable("id") Long id,
            @Validated(Update.class)
            @RequestBody LanguageDto languageDto) throws EntityNotFoundException {
        LanguageDto language = languageService.updateLanguage(id, languageDto);

        return ResponseEntity.ok(language);
    }

    @GetMapping
    public List<LanguageDto> getLanguages() {
        return languageService.getLanguages();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deleteLanguage(@PathVariable(value = "id") Long id) throws EntityNotFoundException {
        languageService.deleteLanguage(id);
    }
}
