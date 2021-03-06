package am.imdb.films.persistence.entity.relation;


import am.imdb.films.persistence.entity.FileEntity;
import am.imdb.films.persistence.entity.MovieEntity;
import am.imdb.films.persistence.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_file")
public class UserFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private FileEntity file;

    @Column(name = "general")
    private Boolean general;

    @PrePersist
    public void onCreate() {
        general = false;
    }
}

