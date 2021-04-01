package am.imdb.films.persistence.repository;


import am.imdb.films.persistence.entity.LanguageEntity;
import am.imdb.films.persistence.entity.RoleEntity;
import am.imdb.films.service.dto.LanguageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

}

