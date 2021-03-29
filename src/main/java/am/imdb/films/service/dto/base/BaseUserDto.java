package am.imdb.films.service.dto.base;

import am.imdb.films.persistence.entity.UserEntity;
import am.imdb.films.service.model.validation.Create;
import am.imdb.films.service.model.validation.Update;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseUserDto {

    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    private Long id;
    protected String firstName;
    protected String lastName;
    @Email(message = "Email should be valid")
    protected String email;
    protected String password;
    protected String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BaseUserDto(Long id, String firstName, String lastName, String email, String password, String status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.status = status;
    }

    public static BaseUserDto toBaseDto(UserEntity entity) {
        return BaseUserDto
                .builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .password(null)
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static UserEntity toEntity(BaseUserDto dto, UserEntity entity) {
        if (Objects.isNull(entity.getId())) entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setStatus(dto.getStatus());
        if (Objects.isNull(entity.getCreatedAt())) entity.setCreatedAt(dto.getCreatedAt());
        if (Objects.isNull(entity.getUpdatedAt())) entity.setUpdatedAt(dto.getUpdatedAt());
        return entity;
    }

    public static List<BaseUserDto> toBaseDtoList(Collection<UserEntity> entityCollection) {
        return entityCollection.stream()
                .map(BaseUserDto::toBaseDto)
                .collect(Collectors.toList());
    }

    public static List<UserEntity> toEntityList(Collection<BaseUserDto> dtoCollection) {
        return dtoCollection.stream()
                .map(languageDto -> BaseUserDto.toEntity(languageDto, new UserEntity()))
                .collect(Collectors.toList());
    }

}
