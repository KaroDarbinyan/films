package am.imdb.films.persistence.entity;


import am.imdb.films.persistence.entity.relation.MovieFileEntity;
import am.imdb.films.persistence.entity.relation.PersonFileEntity;
import am.imdb.films.persistence.entity.relation.UserFileEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "file")
@EqualsAndHashCode(callSuper = true)
public class FileEntity extends BaseEntity{

    @Column(name = "path")
    private String path;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "extension")
    private String extension;

    @Column(name = "content_type")
    private String contentType;

    @OneToMany(mappedBy = "file", targetEntity = UserFileEntity.class)
    private List<UserFileEntity> listOfUserFile;

    @OneToMany(mappedBy = "file", targetEntity = PersonFileEntity.class)
    private List<PersonFileEntity> listOfPersonFile;

    @OneToMany(mappedBy = "file", targetEntity = MovieFileEntity.class)
    private List<MovieFileEntity> listOfMovieFile;

}
