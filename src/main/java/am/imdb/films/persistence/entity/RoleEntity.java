package am.imdb.films.persistence.entity;


import am.imdb.films.persistence.entity.relation.MovieLanguageEntity;
import am.imdb.films.persistence.entity.relation.RolePrivilegeEntity;
import am.imdb.films.persistence.entity.relation.UserRoleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "role", targetEntity = UserRoleEntity.class)
    private List<UserRoleEntity> listOfUserRole;

    @OneToMany(mappedBy = "role", targetEntity = RolePrivilegeEntity.class, fetch = FetchType.EAGER)
    private List<RolePrivilegeEntity> listOfRolePrivilege;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleEntity that = (RoleEntity) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

