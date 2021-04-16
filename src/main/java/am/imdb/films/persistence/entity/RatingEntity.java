package am.imdb.films.persistence.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movie_rating")
@EqualsAndHashCode(callSuper = true)
public class RatingEntity extends BaseEntity{

    @Column(name = "average_rating")
    private Double averageRating;

    @Column(name = "num_votes")
    private Integer numVotes;

    @OneToOne
    @JoinColumn(name = "movie_imdb_id", referencedColumnName = "imdb_id")
    private MovieEntity movie;


}

