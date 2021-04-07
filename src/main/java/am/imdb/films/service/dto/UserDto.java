package am.imdb.films.service.dto;

import am.imdb.films.persistence.entity.UserEntity;
import am.imdb.films.service.validation.model.Create;
import am.imdb.films.service.validation.model.Update;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserDto(Long id, String username, String firstName, String lastName, String status) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
    }

    public static UserDto toDto(UserEntity entity) {
        return UserDto
                .builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static UserEntity toEntity(UserDto dto, UserEntity entity) {
        if (Objects.isNull(entity.getId())) entity.setId(dto.getId());
        entity.setUsername(dto.getUsername());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setStatus(dto.getStatus());
        if (Objects.isNull(entity.getCreatedAt())) entity.setCreatedAt(dto.getCreatedAt());
        if (Objects.isNull(entity.getUpdatedAt())) entity.setUpdatedAt(dto.getUpdatedAt());
        return entity;
    }

    public static List<UserDto> toDtoList(Collection<UserEntity> entityCollection) {
        return entityCollection.stream()
                .map(UserDto::toDto)
                .collect(Collectors.toList());
    }

    public static List<UserEntity> toEntityList(Collection<UserDto> dtoCollection) {
        return dtoCollection.stream()
                .map(languageDto -> UserDto.toEntity(languageDto, new UserEntity()))
                .collect(Collectors.toList());
    }

}
