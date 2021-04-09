package am.imdb.films.persistence.entity.relation;


import am.imdb.films.persistence.entity.FileEntity;
import am.imdb.films.persistence.entity.MovieEntity;
import am.imdb.films.persistence.entity.PersonEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person_file")
public class PersonFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private PersonEntity person;

    @ManyToOne
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private FileEntity file;

    @Column(name = "general")
    private boolean general;

    @PrePersist
    public void onCreate() {
        general = false;
    }
}

