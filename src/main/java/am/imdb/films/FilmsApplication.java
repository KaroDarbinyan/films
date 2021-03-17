package am.imdb.films;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@SpringBootApplication
public class FilmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilmsApplication.class, args);
	}

}
