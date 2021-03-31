package am.imdb.films;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FilmsApplication {

	public static void main(String[] args) {
//		String s = "[\"\"Zamindar Bhubaneshwar Chaudhary\"\"]";
//		System.out.println(s.replaceAll("\"\"", "\""));

		SpringApplication.run(FilmsApplication.class, args);
	}

}
