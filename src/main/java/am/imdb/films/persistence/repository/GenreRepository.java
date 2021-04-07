package am.imdb.films.persistence.repository;


import am.imdb.films.persistence.entity.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Long> {

    Optional<GenreEntity> findByName(String name);

    List<GenreEntity> findByNameIn(Collection<String> names);


}

