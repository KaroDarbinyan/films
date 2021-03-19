package am.imdb.films.service;

import am.imdb.films.persistence.entity.MovieEntity;
import am.imdb.films.persistence.entity.PersonEntity;
import am.imdb.films.persistence.repository.PersonRepository;
import am.imdb.films.service.dto.PersonDto;
import am.imdb.films.util.parser.PersonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public Map<String, Integer> batchInsert(List<PersonEntity> personEntities) {
        int existed = (int) personRepository.count();
        int size = personEntities.size();
        int notPreserved = 0;
        int counter = 0;
        int saved = 0;

        List<PersonEntity> tempPersons = new ArrayList<>();

        for (PersonEntity personEntity : personEntities) {
            tempPersons.add(personEntity);

            if ((counter + 1) % 1000 == 0 || (counter + 1) == size) {
                try {
                    personRepository.saveAll(tempPersons);
                    saved += tempPersons.size();
                } catch (Exception e) {
                    notPreserved += tempPersons.size();
                    logger.error(String.format("%s not preserved", notPreserved));
                }
                tempPersons.clear();
                logger.info(String.format("In progress, [saved -> %s], [existed -> %s], [not preserved -> %s] of %s",
                        saved, existed, notPreserved, size));
            }

            counter++;
        }

        return Map.of(
                "size", size,
                "saved", saved,
                "existed", existed,
                "not_preserved", notPreserved
        );
    }

    public PersonDto getPerson(Long id) throws Exception {
        PersonEntity person = personRepository.findById(id).orElseThrow(() -> new Exception("person not found"));

        return PersonDto.toDto(person);
    }

    public List<PersonDto> getPersons() {
        List<PersonEntity> persons = personRepository.findAll();
        return PersonDto.toDto(persons);
    }

    public PersonDto updatePerson(Long id, PersonDto personDto) throws Exception {
        PersonEntity personEntity = personRepository.findById(id)
                .orElseThrow(() -> new Exception("person not found"));

        if (personDto.getImdbId() != null)
            personEntity.setImdbId(personDto.getImdbId());
        if (personDto.getName() != null)
            personEntity.setName(personDto.getName());
        if (personDto.getBirthName() != null)
            personEntity.setBirthName(personDto.getBirthName());
        if (personDto.getHeight() != null)
            personEntity.setHeight(personDto.getHeight());
        if (personDto.getBio() != null)
            personEntity.setBio(personDto.getBio());
        if (personDto.getBirthDetails() != null)
            personEntity.setBirthDetails(personDto.getBirthDetails());
        if (personDto.getDateOfBirth() != null)
            personEntity.setDateOfBirth(personDto.getDateOfBirth());
        if (personDto.getPlaceOfBirth() != null)
            personEntity.setPlaceOfBirth(personDto.getPlaceOfBirth());
        if (personDto.getDeathDetails() != null)
            personEntity.setDeathDetails(personDto.getDeathDetails());
        if (personDto.getDateOfDeath() != null)
            personEntity.setDateOfDeath(personDto.getDateOfDeath());
        if (personDto.getPlaceOfDeath() != null)
            personEntity.setPlaceOfDeath(personDto.getPlaceOfDeath());
        if (personDto.getReasonOfDeath() != null)
            personEntity.setReasonOfDeath(personDto.getReasonOfDeath());
        if (personDto.getSpousesString() != null)
            personEntity.setSpousesString(personDto.getSpousesString());
        if (personDto.getSpouses() != null)
            personEntity.setSpouses(personDto.getSpouses());
        if (personDto.getDivorces() != null)
            personEntity.setDivorces(personDto.getDivorces());
        if (personDto.getSpousesWithChildren() != null)
            personEntity.setSpousesWithChildren(personDto.getSpousesWithChildren());
        if (personDto.getChildren() != null)
            personEntity.setChildren(personDto.getChildren());

        personEntity = personRepository.save(personEntity);
        return PersonDto.toDto(personEntity);
    }

    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

    public Map<String, Integer> parseCSV(MultipartFile csvFile) throws IOException {
        List<PersonDto> personDtoList = personParser.csvParser(csvFile);

        return batchInsert(PersonDto.toEntity(personDtoList));
    }

    public PersonEntity findPersonByImdbId(String personId) {
        return personRepository.findPersonByImdbId(personId);
    }


    public List<PersonEntity> getByImdbIdIn(List<String> imdbIds) {
        return personRepository.findByImdbIdIn(imdbIds);
    }

    public List<Map<String, String>> getPersonIdAndImdbId() {
        return personRepository.findAllMovieIdAndIds();
    }
}
