package am.imdb.films.service.model.wrapper;

import am.imdb.films.persistence.entity.UserEntity;

public class UserWrapper {

    Long id;
    String status;
    String firstName;

    public UserWrapper(Long id,
                       String status,
                       String firstName) {
        this.id = id;
        this.status = status;
        this.firstName = firstName;
    }
    public UserWrapper(UserEntity user) {
        this.id = user.getId();
        this.status = user.getStatus();
        this.firstName = user.getFirstName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
