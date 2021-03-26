package am.imdb.films.persistence.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"storage\"")
public class StorageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "path")
    private String path;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "extension")
    private String extension;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "general")
    private Boolean general;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "movie_id")
    private Long movieId;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
