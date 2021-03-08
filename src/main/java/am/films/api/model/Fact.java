package am.films.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fact")
public class Fact extends BaseEntity{

    @Column(name = "text")
    private String text;

    @ManyToOne(targetEntity = Film.class)
    private Film film;

    @ManyToOne(targetEntity = Person.class)
    private Person person;

}
