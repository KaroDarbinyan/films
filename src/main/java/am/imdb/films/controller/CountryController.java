package am.imdb.films.controller;


import am.imdb.films.exception.EntityNotFoundException;
import am.imdb.films.service.CountryService;
import am.imdb.films.service.criteria.SearchCriteria;
import am.imdb.films.service.dto.CountryDto;
import am.imdb.films.service.model.validation.Create;
import am.imdb.films.service.model.validation.Update;
import am.imdb.films.service.model.wrapper.QueryResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("countries")
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping
    public ResponseEntity<CountryDto> addCountry(@RequestBody @Validated(Create.class) CountryDto countryDto) {
        CountryDto country = countryService.createCountry(countryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(country);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryDto> getCountry(@PathVariable("id") Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(countryService.getCountry(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CountryDto> updateCountry(
            @PathVariable("id") Long id,
            @Validated(Update.class)
            @RequestBody CountryDto countryDto) throws EntityNotFoundException {
        CountryDto country =countryService.updateCountry(id, countryDto);

        return ResponseEntity.ok(country);
    }

    @GetMapping
    public QueryResponseWrapper<CountryDto> getCountries(SearchCriteria criteria) {
        return countryService.getCountries(criteria);
    }

    @DeleteMapping("/{id}")
    public void deleteCountry(@PathVariable(value = "id") Long id) throws EntityNotFoundException {
        countryService.deleteCountry(id);
    }
}
