package am.imdb.films.persistence.repository;


import am.imdb.films.persistence.entity.MovieEntity;
import am.imdb.films.persistence.entity.PersonEntity;
import am.imdb.films.service.model.wrapper.MoviesWrapper;
import am.imdb.films.service.model.wrapper.PersonsWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    PersonEntity findByImdbId(String imdbId);

    List<PersonEntity> findByImdbIdIn(List<String> imdbIds);

    @Query(value = "SELECT imdb_id FROM person", nativeQuery = true)
    Set<String> findAllPersonsImdbId();

    @Query("SELECT new am.imdb.films.service.model.wrapper.PersonsWrapper(p.id,p.name,p.bio,p.birthDetails, p.dateOfBirth, p.dateOfDeath) FROM PersonEntity p")
    Page<PersonsWrapper> findAllWithPagination(Pageable composePageRequest);

//    @Modifying
//    @Transactional
//    @Query(value = "insert into person (imdb_id,name,birth_name,height,bio,birth_details,date_of_birth,place_of_birth,death_details,date_of_death," +
//            "place_of_death,reason_of_death,spouses_string,spouses,divorces,spouses_with_children,children) VALUES :values", nativeQuery = true)
//    void batchInsert(@Param("values") String values);

}

