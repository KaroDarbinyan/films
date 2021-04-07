package am.imdb.films.persistence.entity;


import am.imdb.films.persistence.entity.relation.MovieFileEntity;
import am.imdb.films.persistence.entity.relation.PersonFileEntity;
import am.imdb.films.persistence.entity.relation.UserFileEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@Table(name = "file")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "path")
    private String path;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "extension")
    private String extension;

    @Column(name = "content_type")
    private String contentType;

    @CreatedDate
    @Column(name = "created_at")
    protected LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "file", targetEntity = UserFileEntity.class)
    private List<UserFileEntity> listOfUserFile;

    @OneToMany(mappedBy = "file", targetEntity = PersonFileEntity.class)
    private List<PersonFileEntity> listOfPersonFile;

    @OneToMany(mappedBy = "file", targetEntity = MovieFileEntity.class)
    private List<MovieFileEntity> listOfMovieFile;

}
