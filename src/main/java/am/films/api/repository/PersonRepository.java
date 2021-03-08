package am.films.api.repository;

import am.films.api.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

//@EnableJpaRepositories
public interface PersonRepository extends JpaRepository<Person, Integer> {

//    List<Person> findByKPId(int kPId);
    @Query("select p from Person p where p.kPId = :kPId")
    Person findByKPId(@Param("kPId") int kPId);


}
