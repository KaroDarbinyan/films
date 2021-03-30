package am.imdb.films.persistence.entity.relation;



import am.imdb.films.persistence.entity.LanguageEntity;
import am.imdb.films.persistence.entity.MovieEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movie_language")
public class MovieLanguageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private MovieEntity movie;

    @ManyToOne
    @JoinColumn(name = "language_id", referencedColumnName = "id")
    private LanguageEntity language;

    public MovieLanguageEntity(MovieEntity movie, LanguageEntity language) {
        this.movie = movie;
        this.language = language;
    }
}

