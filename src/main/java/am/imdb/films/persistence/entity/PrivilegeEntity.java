package am.imdb.films.persistence.entity;

import am.imdb.films.persistence.entity.relation.RolePrivilegeEntity;
import am.imdb.films.persistence.entity.relation.UserRoleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "privilege")
public class PrivilegeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "privilege", targetEntity = RolePrivilegeEntity.class)
    private List<RolePrivilegeEntity> listOfRolePrivilege;
}
