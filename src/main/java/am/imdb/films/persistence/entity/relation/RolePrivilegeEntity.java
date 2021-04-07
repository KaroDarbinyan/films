package am.imdb.films.persistence.entity.relation;


import am.imdb.films.persistence.entity.PrivilegeEntity;
import am.imdb.films.persistence.entity.RoleEntity;
import am.imdb.films.persistence.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role_privilege")
public class RolePrivilegeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "privilege_id", referencedColumnName = "id")
    private PrivilegeEntity privilege;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private RoleEntity role;

}

