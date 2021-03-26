package am.imdb.films.persistence.entity;


import am.imdb.films.persistence.entity.relation.MoviePersonEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person")
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "imdb_id")
    private String imdbId;

    @Column(name = "name")
    private String name;

    @Column(name = "birth_name")
    private String birthName;

    @Column(name = "height")
    private String height;

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
    private String spouses;

    @Column(name = "divorces")
    private String divorces;

    @Column(name = "spouses_with_children")
    private String spousesWithChildren;

    @Column(name = "children")
    private String children;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "person", targetEntity = MoviePersonEntity.class)
    private List<MoviePersonEntity> listOfMoviePerson;

}

