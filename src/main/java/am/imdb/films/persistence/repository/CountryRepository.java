package am.imdb.films.persistence.repository;


import am.imdb.films.persistence.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Long> {

    CountryEntity findByName(String name);
    List<CountryEntity> findByNameIn(Set<String> names);

//    @Query("SELECT u FROM CountryEntity u LEFT JOIN u.listOfProductionCountries")
//    List<CountryEntity> findAllCountries();


}

