package am.imdb.films.service.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserSearchCriteria extends SearchCriteria {

    private String username;
    private String firstName;
    private String lastName;
    private String status;

}
