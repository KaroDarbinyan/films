package am.imdb.films.service;

import am.imdb.films.exception.EntityNotFoundException;
import am.imdb.films.persistence.entity.FileEntity;
import am.imdb.films.persistence.entity.PersonEntity;
import am.imdb.films.persistence.entity.relation.PersonFileEntity;
import am.imdb.films.persistence.repository.PersonFileRepository;
import am.imdb.films.persistence.repository.PersonRepository;
import am.imdb.films.service.control.CsvControl;
import am.imdb.films.service.criteria.SearchCriteria;
import am.imdb.films.service.dto.PersonDto;
import am.imdb.films.service.dto.FileDto;
import am.imdb.films.service.model.csv.Person;
import am.imdb.films.service.model.map.MapEntityKeys;
import am.imdb.films.service.model.wrapper.QueryResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final CsvControl<Person> csvControl;
    private final FileService fileService;
    private final PersonFileRepository personFileRepository;

    @Autowired
    public PersonService(PersonRepository personRepository, CsvControl<Person> csvControl, FileService fileService, PersonFileRepository personFileRepository) {
        this.personRepository = personRepository;
        this.csvControl = csvControl;
        this.fileService = fileService;
        this.personFileRepository = personFileRepository;
    }

    public PersonDto createPerson(PersonDto personDto) {
        PersonEntity personEntity = PersonDto.toEntity(personDto, new PersonEntity());
        PersonEntity entity = personRepository.save(personEntity);
        return PersonDto.toDto(entity);
    }

    public PersonDto getPerson(Long id) throws EntityNotFoundException {
        PersonEntity person = personRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        return PersonDto.toDto(person);
    }

    public PersonDto updatePerson(Long id, PersonDto personDto) throws EntityNotFoundException {
        PersonEntity personEntity = personRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        PersonDto.toEntity(personDto, personEntity);
        return PersonDto.toDto(personRepository.save(personEntity));
    }

    public QueryResponseWrapper<PersonDto> getPersons(SearchCriteria criteria) {
        Page<PersonDto> content = personRepository.findAllWithPagination(criteria.composePageRequest());
        return new QueryResponseWrapper<>(content);
    }

    public void deletePerson(Long id) throws EntityNotFoundException {
        personRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        personRepository.deleteById(id);
    }

    public Map<String, Integer> parseCsv(MultipartFile csvFile) {
        Set<String> allPersonsImdbId = personRepository.findAllPersonsImdbId();
        List<List<Person>> persons = csvControl.getEntitiesFromCsv(csvFile, Person.class);
        AtomicInteger existed = new AtomicInteger();

        List<List<PersonEntity>> personEntitiesList = persons.stream()
                .map(personList -> personList.stream()
                        .filter(person -> {
                            boolean contains = allPersonsImdbId.contains(person.getImdbId());
                            if (contains) existed.getAndIncrement();
                            return !contains;
                        })
                        .map(Person::toEntity)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());


        int saved = 0;
        for (List<PersonEntity> personEntities : personEntitiesList) {
            saved += personRepository.saveAll(personEntities).size();
        }

        return Map.of("saved", saved, "existed", existed.intValue());
    }

    public FileDto addFile(MultipartFile file, Long id) {
        PersonEntity personEntity = personRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        FileEntity fileEntity = new FileEntity();
        fileEntity.setPath(String.format("person/%s", id));
        fileEntity = fileService.storeFile(file, fileEntity);
        PersonFileEntity personFileEntity = new PersonFileEntity();
        personFileEntity.setFile(fileEntity);
        personFileEntity.setPerson(personEntity);
        personFileRepository.save(personFileEntity);

        return FileDto.toDto(fileEntity);
    }

    public Map<String, Long> getPersonsImdbIdsAndIds() {
        List<MapEntityKeys<Long, String>> list = personRepository.findAllPersonImdbIdsAndIds();

        return new MapEntityKeys<Long, String>().toReverseMap(list);
    }
}
