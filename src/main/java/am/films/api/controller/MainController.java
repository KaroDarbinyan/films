package am.films.api.controller;

import am.films.api.cron.KinoPoiskApiUnofficialRestClient;
import am.films.api.factory.PersonFactory;
import am.films.api.model.Person;
import am.films.api.service.PersonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private final PersonService personService;
    private final KinoPoiskApiUnofficialRestClient kinoPoiskApiUnofficialRestClient;

    @Autowired
    public MainController(PersonService personService, KinoPoiskApiUnofficialRestClient kinoPoiskApiUnofficialClient) {
        this.personService = personService;
        this.kinoPoiskApiUnofficialRestClient = kinoPoiskApiUnofficialClient;
    }

    @GetMapping("/")
    public String home() throws JsonProcessingException {
        for (int i = 1000; i < 2000; i++) {
            System.out.println(i);
            String json = kinoPoiskApiUnofficialRestClient.get(String.format("/staff/%s", i));
            if (json != null) {
                Person person = PersonFactory.createPerson(json);
                if (personService.getByKPId(person.getKPId()) == null)
                    personService.addPerson(person);
            }
        }

        return "";
    }
}
