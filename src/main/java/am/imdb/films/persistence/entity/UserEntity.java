package am.imdb.films.persistence.entity;

import am.imdb.films.persistence.entity.relation.UserFavoriteEntity;
import am.imdb.films.persistence.entity.relation.UserFileEntity;
import am.imdb.films.persistence.entity.relation.UserRoleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"user\"")
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends BaseEntity {

    @Column(name = "username")
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "user", targetEntity = UserRoleEntity.class, fetch = FetchType.EAGER)
    private List<UserRoleEntity> listOfUserRole;

    @OneToMany(mappedBy = "user", targetEntity = UserFileEntity.class)
    private List<UserFileEntity> listOfUserFile;

    @OneToMany(mappedBy = "user", targetEntity = UserFavoriteEntity.class, fetch = FetchType.LAZY)
    private Set<UserFavoriteEntity> listOfUserFavorite;


}
