package am.imdb.films.persistence.repository;

import am.imdb.films.persistence.entity.UserEntity;
import am.imdb.films.service.criteria.UserSearchCriteria;
import am.imdb.films.service.model.wrapper.UserWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u where " +
            "((:#{#criteria.username} = '') or (u.username like concat('%', :#{#criteria.username}, '%'))) and " +
            "((:#{#criteria.firstName} = '') or (u.firstName like concat('%', :#{#criteria.firstName}, '%'))) and " +
            "((:#{#criteria.lastName} = '') or (u.lastName like concat('%', :#{#criteria.lastName}, '%'))) and " +
            "((:#{#criteria.status} = '') or (u.status = :#{#criteria.status}))")
    Page<UserEntity> findAllWithPagination(@Param("criteria") UserSearchCriteria criteria, Pageable pageable);

    UserEntity findByUsername(String username);

    @Query("SELECT new am.imdb.films.service.model.wrapper.UserWrapper(u.id," +
            "u.status," +
            "u.firstName) FROM UserEntity u join u.listOfUserFavorite louf")
    Page<UserWrapper> findAllWithPagination(Pageable pageable);
}
