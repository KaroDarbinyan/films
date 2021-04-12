package am.imdb.films.service.criteria;

import lombok.*;


@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserSearchCriteria extends SearchCriteria {

    private String username;
    private String firstName;
    private String lastName;
    private String status;

    public UserSearchCriteria() {
        this.username = getValueOrDefault(this.username);
        this.firstName = getValueOrDefault(this.firstName);
        this.lastName = getValueOrDefault(this.lastName);
        this.status = getValueOrDefault(this.status);
    }
}
