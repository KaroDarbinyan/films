package am.imdb.films.persistence.entity;


import am.imdb.films.persistence.entity.relation.MovieCountryEntity;
import am.imdb.films.persistence.entity.relation.MoviePersonEntity;
import am.imdb.films.persistence.entity.relation.PersonFileEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person")
@EqualsAndHashCode(callSuper = true)
public class PersonEntity extends BaseEntity{

    @Column(name = "imdb_id")
    private String imdbId;

    @Column(name = "name")
    private String name;

    @Column(name = "birth_name")
    private String birthName;

    @Column(name = "height")
    private Integer height;

    @Column(name = "bio")
    private String bio;

    @Column(name = "birth_details")
    private String birthDetails;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "place_of_birth")
    private String placeOfBirth;

    @Column(name = "death_details")
    private String deathDetails;

    @Column(name = "date_of_death")
    private String dateOfDeath;

    @Column(name = "place_of_death")
    private String placeOfDeath;

    @Column(name = "reason_of_death")
    private String reasonOfDeath;

    @Column(name = "spouses_string")
    private String spousesString;

    @Column(name = "spouses")
    private Integer spouses;

    @Column(name = "divorces")
    private Integer divorces;

    @Column(name = "spouses_with_children")
    private Integer spousesWithChildren;

    @Column(name = "children")
    private Integer children;

    @OneToMany(mappedBy = "person", targetEntity = PersonFileEntity.class)
    private List<PersonFileEntity> listOfPersonFile;


}

