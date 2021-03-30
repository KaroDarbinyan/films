package am.imdb.films.persistence.entity.relation;

import am.imdb.films.persistence.entity.GenreEntity;
import am.imdb.films.persistence.entity.MovieEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movie_genre")
public class MovieGenreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private MovieEntity movie;

    @ManyToOne
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    private GenreEntity genre;


    public MovieGenreEntity(MovieEntity movie, GenreEntity genre) {
        this.movie = movie;
        this.genre = genre;
    }
}

