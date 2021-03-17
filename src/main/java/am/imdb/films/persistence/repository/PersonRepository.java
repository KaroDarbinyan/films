package am.imdb.films.persistence.repository;


import am.imdb.films.persistence.entity.LanguageEntity;
import am.imdb.films.persistence.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    PersonEntity findPersonByImdbId(String imdbId);

//    @Query("SELECT u FROM PersonEntity u LEFT JOIN u.listOfMovieCasts WHERE " +
//            ":name IS NULL OR u.name = :name")
//    List<PersonEntity> findAllPersons(String name);
}

