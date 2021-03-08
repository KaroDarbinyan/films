package am.films.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@SpringBootApplication
public class FilmsApplication {


    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(FilmsApplication.class, args);



//        Pattern p = Pattern.compile("\\b(Фильм|смотреть онлайн)\\b\\s?");
//        Matcher m = p.matcher("Фильм Эффект Манделы смотреть онлайн\n");
//        String s = m.replaceAll("");
//        System.out.println(s);

//        KinoPoiskApiUnofficialClient restClient = new KinoPoiskApiUnofficialClient();
//        String json = restClient.get("staff/8817");
//        ObjectMapper objectMapper = new ObjectMapper();
//objectMapper.
//        JsonNode jsonNode =
//                objectMapper.readTree(json);
//
//        System.out.println(jsonNode.get("personId"));
//
//        System.out.println(json);
//
//        System.exit(0);
//
//
//        String url = "https://www.kinopoisk.ru/film/%s/";
//        for (int i = 0; i < 1; i++) {
//            int id = (int) Math.floor(Math.random() * 3628821);
//            id = 326;
//            Connection connect = Jsoup.connect(String.format(url, id));
//
//            try {
//                Document document = connect.get();
//
//
//                if (document != null) {
//                    System.out.println(document.title());
////                    String text = document.select("div.videoInfoPanel-header-info h1").text();
////                    System.out.println(text);
////                    System.out.println(text.replaceAll("\\b(Фильм|смотреть онлайн)\\b\\s?", "").trim());
//                }
//            }catch (HttpStatusException e) {
//                System.out.println(111);
//                continue;
//            }
//
//        }
//


//        SpringApplication.run(FilmsApplication.class, args);
    }

}
