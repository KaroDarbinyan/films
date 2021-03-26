package am.imdb.films.persistence.entity;


import am.imdb.films.persistence.entity.relation.MovieCountryEntity;
import am.imdb.films.persistence.entity.relation.MovieGenreEntity;
import am.imdb.films.persistence.entity.relation.MovieLanguageEntity;
import am.imdb.films.persistence.entity.relation.MoviePersonEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movie")
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "imdb_id")
    private String imdbId;

    @Column(name = "title")
    private String title;

    @Column(name = "original_title")
    private String originalTitle;

    @Column(name = "year")
    private String year;

    @Column(name = "date_published")
    private String datePublished;

    @Column(name = "duration")
    private String duration;

    @Column(name = "director")
    private String director;

    @Column(name = "writer")
    private String writer;

    @Column(name = "production_company")
    private String productionCompany;

    @Column(name = "actors")
    private String actors;

    @Column(name = "description")
    private String description;

    @Column(name = "avg_vote")
    private String avgVote;

    @Column(name = "budget")
    private String budget;

    @Column(name = "usa_gross_income")
    private String usaGrossIncome;

    @Column(name = "worldwide_gross_income")
    private String worldWideGrossIncome;

    @Column(name = "metas_core")
    private String metasCore;

    @Column(name = "reviews_from_users")
    private String reviewsFromUsers;

    @Column(name = "reviews_from_critics")
    private String reviewsFromCritics;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "movie", targetEntity = MovieGenreEntity.class)
    private List<MovieGenreEntity> listOfMovieGenre;

    @OneToMany(mappedBy = "movie", targetEntity = MovieCountryEntity.class)
    private List<MovieCountryEntity> listOfMovieCountry;

    @OneToMany(mappedBy = "movie", targetEntity = MovieLanguageEntity.class)
    private List<MovieLanguageEntity> listOfMovieLanguage;

    @OneToMany(mappedBy = "movie", targetEntity = MoviePersonEntity.class)
    private List<MoviePersonEntity> listOfMoviePerson;

    @OneToOne(mappedBy = "movie", targetEntity = RatingEntity.class, fetch = FetchType.LAZY, optional = false)
    private RatingEntity rating;
}

