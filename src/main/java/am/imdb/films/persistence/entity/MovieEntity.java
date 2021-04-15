package am.imdb.films.persistence.entity;


import am.imdb.films.persistence.entity.relation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movie")
@EqualsAndHashCode(callSuper = true)
public class MovieEntity extends BaseEntity {

    @Column(name = "imdb_id")
    private String imdbId;

    @Column(name = "title")
    private String title;

    @Column(name = "release_date")
    private Integer releaseDate;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "production_company")
    private String productionCompany;

    @Column(name = "description")
    private String description;

    @Column(name = "avg_vote")
    private Double avgVote;

    @Column(name = "votes")
    private Integer votes;

    @Column(name = "budget")
    private String budget;

    @Column(name = "usa_gross_income")
    private String usaGrossIncome;

    @Column(name = "worldwide_gross_income")
    private String worldWideGrossIncome;

    @Column(name = "metas_core")
    private Double metasCore;

    @Column(name = "reviews_from_users")
    private Double reviewsFromUsers;

    @Column(name = "reviews_from_critics")
    private Double reviewsFromCritics;

    @Column(name = "parse_error")
    private boolean parseError;

//    public void onCreate() {
//        parseError = false;
//    }

    @OneToMany(mappedBy = "movie", targetEntity = MovieGenreEntity.class)
    private List<MovieGenreEntity> listOfMovieGenre;

    @OneToMany(mappedBy = "movie", targetEntity = MovieCountryEntity.class)
    private List<MovieCountryEntity> listOfMovieCountry;

    @OneToMany(mappedBy = "movie", targetEntity = MovieLanguageEntity.class)
    private List<MovieLanguageEntity> listOfMovieLanguage;

    @OneToMany(mappedBy = "movie", targetEntity = MoviePersonEntity.class)
    private List<MoviePersonEntity> listOfMoviePerson;

    @OneToMany(mappedBy = "movie", targetEntity = MovieFileEntity.class)
    private List<MovieFileEntity> listOfMovieFile;

    @OneToOne(mappedBy = "movie", targetEntity = RatingEntity.class)
    private RatingEntity rating;

    @OneToMany(mappedBy = "movie", targetEntity = UserFavoriteEntity.class)
    private Set<UserFavoriteEntity> listOfUserFavorite;
}

