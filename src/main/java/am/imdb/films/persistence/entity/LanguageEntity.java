package am.imdb.films.persistence.entity;


import am.imdb.films.persistence.entity.relation.MovieLanguageEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "language")
public class LanguageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "language", targetEntity = MovieLanguageEntity.class)
    private List<MovieLanguageEntity> listOfMovieLanguage;

}

