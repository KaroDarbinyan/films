package am.imdb.films.persistence.repository;


import am.imdb.films.persistence.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Long> {
    CountryEntity findLanguageByName(String name);

//    @Query("SELECT u FROM CountryEntity u LEFT JOIN u.listOfProductionCountries")
//    List<CountryEntity> findAllCountries();


}

