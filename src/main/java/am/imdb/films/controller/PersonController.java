package am.imdb.films.controller;


import am.imdb.films.service.PersonService;
import am.imdb.films.service.dto.PersonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("persons")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<PersonDto> getPersons() {
        return personService.getPersons();
    }

    @PostMapping
    public ResponseEntity<PersonDto> addPerson(@RequestBody PersonDto personDto) {

        PersonDto person = personService.createPerson(personDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(person);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> getPerson(@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.ok(personService.getPerson(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDto> updatePerson(@PathVariable("id") Long id,
                                                  @RequestBody PersonDto personDto) throws Exception {
        PersonDto person = personService.updatePerson(id, personDto);
        if (person == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(person);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable("id") Long id) {
        personService.deletePerson(id);
    }


    @PostMapping("/import-from-csv-file")
    public ResponseEntity<Map<String, Integer>> uploadCSVFile(@RequestParam(name = "file") MultipartFile csvFile) throws Exception {

        if (csvFile.isEmpty()) {
            ResponseEntity.badRequest().body(Map.of("message", "Required request part 'file' is not present"));
        }
        if (!Objects.equals(csvFile.getContentType(), "text/csv")) {
            ResponseEntity.badRequest().body(Map.of("message", "The file must be in csv format"));
        }

        Map<String, Integer> result = personService.parseCSV(csvFile);
        return ResponseEntity.ok().body(result);
    }
}
