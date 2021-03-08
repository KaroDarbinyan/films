package am.films.api.model;

import am.films.api.model.enums.FilmType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "film")
public class Film extends BaseEntity{


    @Column(name = "k_p_id")
    private int kPId;

    @Column(name = "name_ru")
    private String nameRu;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "web_url")
    private String webUrl;

    @Column(name = "poster_url")
    private String posterUrl;

    @Column(name = "poster_url_preview")
    private String posterUrlPreview;

    @Column(name = "year")
    private String year;

    @Column(name = "film_length")
    private String filmLength;

    @Column(name = "slogan")
    private String slogan;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    private FilmType type;

    @Column(name = "rating_m_p_a_a")
    private String ratingMPAA;

    @Column(name = "rating_age_limits")
    private int ratingAgeLimits;

    @Column(name = "premiere_ru")
    private Date premiereRu;

    @Column(name = "distributors")
    private String distributors;

    @Column(name = "premiere_world")
    private Date premiereWorld;

    @Column(name = "premiere_digital")
    private Date premiereDigital;

    @Column(name = "premiere_world_country")
    private String premiereWorldCountry;

    @Column(name = "premiere_dvd")
    private String premiereDvd;

    @Column(name = "premiere_blu_ray")
    private String premiereBluRay;

    @Column(name = "distributor_release")
    private String distributorRelease;

//    @Column(name = "year")
//    private List<Country> countries;

    @ManyToMany
    @JoinTable(
            name = "film_genre",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres;

    @OneToMany(targetEntity = Fact.class)
    private List<Fact> facts;

    @Column(name = "imdb_id")
    private String imdbId;
}
