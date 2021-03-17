package am.imdb.films.service;

import am.imdb.films.persistence.entity.MovieEntity;
import am.imdb.films.persistence.entity.PersonEntity;
import am.imdb.films.persistence.repository.PersonRepository;
import am.imdb.films.service.dto.MovieDto;
import am.imdb.films.service.dto.PersonDto;
import am.imdb.films.util.parser.PersonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonParser personParser;
    private final Logger logger = LoggerFactory.getLogger(PersonService.class);


    @Autowired
    public PersonService(PersonRepository personRepository, PersonParser personParser) {
        this.personRepository = personRepository;
        this.personParser = personParser;
    }

    public List<PersonDto> getPersons() {
        List<PersonEntity> persons = personRepository.findAll();
        return PersonDto.toDto(persons);
    }

    public PersonDto getPerson(Long id) throws Exception {
        PersonEntity person = personRepository.findById(id).orElseThrow(() -> new Exception("person not found"));

        return PersonDto.toDto(person);
    }

    public Map<String, Long> parseCSV(MultipartFile csvFile) throws IOException {
        List<PersonDto> personDtoList = personParser.csvParser(csvFile);
        long saved = 0;
        long existed = 0;
        long notPreserved = 0;
        long size = Integer.toUnsignedLong(personDtoList.size());
        for (PersonDto personDto : personDtoList) {
            PersonEntity personEntity = personRepository.findPersonByImdbId(personDto.getImdbId());
            try {
                if (personEntity == null) {
                    personEntity = personRepository.save(PersonDto.toEntity(personDto));
                    ++saved;
                } else {
                    ++existed;
                }
            } catch (Exception e) {
                logger.error(String.format("%s not preserved object -> %s", ++notPreserved, personDto.getImdbId()));
            }
            logger.info(String.format("In progress, [saved -> %s], [existed -> %s], [not preserved -> %s] of %s", saved, existed, notPreserved, size));

        }

        Map<String, Long> result = new HashMap<>();
        result.put("size", size);
        result.put("saved", saved);
        result.put("existed", existed);
        result.put("not_preserved", notPreserved);
        return result;
    }

    public PersonDto createPerson(PersonDto personDto) {
        PersonEntity personEntity = personRepository.findPersonByImdbId(personDto.getImdbId());

        if (personEntity == null) {
            personEntity = PersonDto.toEntity(personDto);
        }

        return PersonDto.toDto(personRepository.save(personEntity));
    }

    public List<PersonDto> createPersons(List<PersonDto> dtoList) {
        List<PersonEntity> personEntities = PersonDto.toEntity(dtoList);
        return PersonDto.toDto(personRepository.saveAll(personEntities));
    }
}
