package am.imdb.films.util.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImdbParser {

    @Value("${imdb.image.download.url}")
    private static String url;

    public static Map<Long, String> parseHtml(Map<Long, String> longStringMap) {

        Map<Long, String> result = new HashMap<>();

        longStringMap.forEach((id, imdbId) -> {
            Document doc = null;
            try {
                doc = Jsoup.connect(String.join(File.separator, url, imdbId)).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements newsHeadlines = doc.select("#mp-itn b a");
//            for (Element headline : newsHeadlines) {
//                log("%s\n\t%s",
//                        headline.attr("title"), headline.absUrl("href"));
//            }
        });



        return result;
    }
}
