package am.imdb.films.persistence.repository;

import am.imdb.films.persistence.entity.UserEntity;
import am.imdb.films.service.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u where " +
            "((:username is null or u.username like concat('%', :username, '%'))) and " +
            "((:firstName is null or u.firstName like concat('%', :firstName, '%'))) and " +
            "((:lastName is null or u.lastName like concat('%', :lastName, '%'))) and " +
            "((:status is null or u.status = :status))")
    Page<UserEntity> findAllWithPagination(
            @Param("username") String username,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("status") String status,
            Pageable composePageRequest);

    UserEntity findByUsername(String username);
}
