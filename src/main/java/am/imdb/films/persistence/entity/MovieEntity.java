package am.imdb.films.persistence.entity;


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


    @ManyToMany
    @JoinTable(
            name = "movie_language",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id"))
    private List<LanguageEntity> languages;

    @ManyToMany
    @JoinTable(
            name = "movie_country",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id"))
    private List<CountryEntity> countries;

    @ManyToMany
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<GenreEntity> genres;

//    @OneToMany(mappedBy = "movie", targetEntity = MovieGenreEntity.class)
//    private List<MovieGenreEntity> listOfMovieGenres;
//
//    @OneToMany(mappedBy = "movie", targetEntity = MovieCountryEntity.class)
//    private List<MovieCountryEntity> listOfMovieCountries;
//
//    @OneToMany(mappedBy = "movie", targetEntity = MovieLanguageEntity.class)
//    private List<MovieLanguageEntity> listOfMovieLanguages;
//
//    @OneToMany(mappedBy = "movie", targetEntity = MoviePersonEntity.class)
//    private List<MoviePersonEntity> listOfMoviePersons;
}

