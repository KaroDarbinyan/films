package am.films.api.model;

import am.films.api.model.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "person")
public class Person extends BaseEntity {

    @Column(name = "k_p_id")
    private int kPId;

    @Column(name = "web_url")
    private String webUrl;

    @Column(name = "name_ru")
    private String nameRu;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "sex")
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Column(name = "poster_url")
    private String posterUrl;

    @Column(name = "growth")
    private int growth;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "death")
    private String death;

    @Column(name = "age")
    private int age;

    @Column(name = "birth_place")
    private String birthPlace;

    @Column(name = "death_place")
    private String deathPlace;

    @Column(name = "has_awards")
    private int hasAwards;

    @Column(name = "profession")
    @Enumerated(EnumType.STRING)
    private Profession profession;

    @OneToMany(targetEntity = Fact.class)
    private List<Fact> facts;

    @ManyToMany
    private List<Film> films;
}
