package am.imdb.films.persistence.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "language")
public class LanguageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "code")
//    private String code;

    @Column(name = "name")
    private String name;

//
//    @ManyToMany
//    @JoinTable(
//            name = "lang_gen",
//            joinColumns = @JoinColumn(name = "lang_id"),
//            inverseJoinColumns = @JoinColumn(name = "gen_id"))
//    private List<GenreEntity> genderEntities;
//    @OneToMany(mappedBy = "language", targetEntity = LangGenEntity.class)
//    private List<LangGenEntity> list;
}

