package am.imdb.films.persistence.repository;

import am.imdb.films.persistence.entity.UserEntity;
import am.imdb.films.service.dto.base.BaseUserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT new am.imdb.films.service.dto.base.BaseUserDto(" +
            "u.id,u.firstName,u.lastName,u.email,u.password,u.status" +
            ") FROM UserEntity u")
    Page<BaseUserDto> findAllWithPagination(Pageable composePageRequest);
}
