package am.imdb.films.persistence.repository;


import am.imdb.films.persistence.entity.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Long> {


    GenreEntity findLanguageByName(String name);
}

