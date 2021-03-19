package am.imdb.films.persistence.entity;


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

    @Column(name = "movie_id")
    private Long movieId;

    @Column(name = "ordering")
    private String ordering;

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "category")
    private String category;

    @Column(name = "job")
    private String job;

    @Column(name = "characters")
    private String characters;

//    @ManyToOne
//    @JoinColumn(name = "movie_id", referencedColumnName = "id")
//    private MovieEntity movie;
//
//    @ManyToOne
//    @JoinColumn(name = "person_id", referencedColumnName = "id")
//    private PersonEntity person;
}

