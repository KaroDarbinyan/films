package am.imdb.films.persistence.entity.relation;

import am.imdb.films.persistence.entity.CountryEntity;
import am.imdb.films.persistence.entity.MovieEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movie_country")
public class MovieCountryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private MovieEntity movie;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private CountryEntity country;


}

