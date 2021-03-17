package am.imdb.films.controller;



import am.imdb.films.service.PersonService;
import am.imdb.films.service.dto.PersonDto;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> getPerson(@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.ok(personService.getPerson(id));
    }


    @PostMapping("/import-from-csv-file")
    public ResponseEntity<Map<String, Long>> uploadCSVFile(@RequestParam(name = "file") MultipartFile csvFile) throws Exception {

        if (csvFile.isEmpty()) {
            throw new Exception("Required request part 'file' is not present");
        }
        if (!Objects.equals(csvFile.getContentType(), "text/csv")) {
            throw new Exception("The file must be in csv format");
        }

        Map<String, Long> result = personService.parseCSV(csvFile);
        return ResponseEntity.ok().body(result);
    }
}
