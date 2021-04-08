package am.imdb.films.persistence.repository;

import am.imdb.films.persistence.entity.relation.MovieLanguageEntity;
import am.imdb.films.persistence.entity.relation.UserFavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFavoriteRepository extends JpaRepository<UserFavoriteEntity, Long> {

}
