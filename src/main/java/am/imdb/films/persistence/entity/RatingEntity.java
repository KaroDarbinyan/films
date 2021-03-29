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
@Table(name = "rating")
@EqualsAndHashCode(callSuper = true)
public class RatingEntity extends BaseEntity{

    @Column(name = "average_rating")
    private String averageRating;

    @Column(name = "num_votes")
    private String numVotes;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private MovieEntity movie;


}

