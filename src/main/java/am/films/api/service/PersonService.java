package am.films.api.service;

import am.films.api.model.Person;

import java.util.List;

public interface PersonService {

    Person addPerson(Person person);
    void delete(Person person);
    Person getByKPId(int kPId);

}
