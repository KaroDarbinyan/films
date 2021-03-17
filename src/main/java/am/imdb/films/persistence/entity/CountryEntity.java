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
@Table(name = "country")
public class CountryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "iso_code")
//    private String isoCode;

    @Column(name = "name")
    private String name;

//    @OneToMany(mappedBy = "country", targetEntity = ProductionCountryEntity.class)
//    private List<ProductionCountryEntity> listOfProductionCountries;
}

