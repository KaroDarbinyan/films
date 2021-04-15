package am.imdb.films.persistence.entity.relation;


import am.imdb.films.persistence.entity.MovieEntity;
import am.imdb.films.persistence.entity.PersonEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movie_person")
public class MoviePersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ordering")
    private Integer ordering;

    @Column(name = "category")
    private String category;

    @Column(name = "characters")
    private String characters;

    @ManyToOne
    @JoinColumn(name = "movie_imdb_id", referencedColumnName = "imdb_id")
    private MovieEntity movie;

    @ManyToOne
    @JoinColumn(name = "person_imdb_id", referencedColumnName = "imdb_id")
    private PersonEntity person;
}

