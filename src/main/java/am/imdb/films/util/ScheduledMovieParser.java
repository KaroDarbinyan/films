package am.imdb.films.util;

import am.imdb.films.persistence.entity.FileEntity;
import am.imdb.films.persistence.entity.MovieEntity;
import am.imdb.films.persistence.repository.MovieRepository;
import am.imdb.films.service.FileService;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableScheduling
public class ScheduledMovieParser {

    @Value("${imdb.url}")
    private String url;

    @Value("${file.upload-dir}")
    String uploadDir;

    private MovieEntity entity;
    private Document document;

    private final int PAGE = 0;
    private final int SIZE = 20;

    private final MovieRepository movieRepository;
    private final FileService fileService;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ScheduledMovieParser(MovieRepository movieRepository, FileService fileService, JdbcTemplate jdbcTemplate) {
        this.movieRepository = movieRepository;
        this.fileService = fileService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Scheduled(fixedDelay = 1000)
    public void scheduleFixedDelayTask() throws InterruptedException {
        List<String> countryValues = new ArrayList<>();
        List<String> languageValues = new ArrayList<>();
        Page<MovieEntity> movieEntities = movieRepository.findPersonByParseErrorAndParseSuccess(false, false, PageRequest.of(PAGE, SIZE, Sort.by("releaseDate").descending()));
        movieEntities.forEach(movie -> {
            try {
                entity = movie;
                document = Jsoup.connect(String.join("/", url, "title", movie.getImdbId())).get();

                movie.setBudget(getTxtBlock("Budget:"));
                movie.setUsaGrossIncome(getTxtBlock("Gross USA:"));
                movie.setWorldWideGrossIncome(getTxtBlock("Cumulative Worldwide Gross:"));

                List<Double> reviews = getReviews();
                movie.setReviewsFromUsers(reviews.get(0));
                movie.setReviewsFromCritics(reviews.get(1));

                Element metasCoreEl = document.selectFirst("div.metacriticScore.score_unfavorable.titleReviewBarSubItem span");
                movie.setMetasCore(metasCoreEl != null ? Double.parseDouble(metasCoreEl.text()) : 0D);

                Element descEl = document.selectFirst("div#titleStoryLine div.inline.canwrap span");
                movie.setDescription(descEl != null ? descEl.text() : null);
//                parseImages();

                countryValues.addAll(getEntities("Country:"));
                languageValues.addAll(getEntities("Language:"));
                movie.setParsSuccess(true);
            } catch (Exception e) {
                movie.setParseError(true);
                System.err.println(e.getMessage());
            }
            movieRepository.save(movie);
        });
        if (!countryValues.isEmpty()) saveMovieRelations(countryValues, "country");
        if (!languageValues.isEmpty()) saveMovieRelations(languageValues, "language");

    }

    private String getTxtBlock(String key) {
        Element element = document.selectFirst(String.format("div.txt-block:contains(%s)", key));
        Node node = null;

        if (element != null) node = element.childNode(2);
        return node == null ? null : node.toString();
    }

    private List<Double> getReviews() {
        Element element = document.selectFirst("div.titleReviewBarItem.titleReviewbarItemBorder span.subText");
        List<Double> reviews = Arrays.asList(0D, 0D);
        Elements elements = null;

        if (element != null) elements = element.select("a");
        if (elements == null) return reviews;

        List<String> strings = elements.eachText();
        if (strings.get(0) != null) reviews.set(0, Double.parseDouble(strings.get(0).replaceAll("[^0-9]", "")));
        if (strings.get(1) != null) reviews.set(1, Double.parseDouble(strings.get(1).replaceAll("[^0-9]", "")));

        return reviews;
    }

    private List<String> getEntities(String key) {
        Element element = document.selectFirst(String.format("div.txt-block:contains(%s)", key));
        List<String> entities = List.of();
        Elements elements = null;

        if (element != null) elements = element.children();

        if (elements != null) elements = elements.select("a");

        if (elements == null) return entities;

        return elements.eachText().isEmpty() ? entities : elements.eachText().stream()
                .map(str -> String.format("('%s', '%s')", entity.getImdbId(), str)).collect(Collectors.toList());
    }

    private void saveMovieRelations(List<String> values, String table) {
        String query = "insert into movie_tableName(movie_id, tableName_id)\n" +
                "select movie.id, tableName.id from (values %s)\n" +
                "as data(movie, tableName)\n" +
                "join movie on movie.imdb_id = data.movie\n" +
                "join tableName on tableName.name = data.tableName;";

        query = query.replaceAll("tableName", table);

        jdbcTemplate.execute(String.format(query, String.join(",\n", values)));
    }

    private void parseImages() {
        Element element = document.selectFirst("div.poster a img");

        if (element != null) saveImages(element.attr("src"), true);

        Elements elements = document.select("div.mediastrip a img");
        if (elements != null)
            elements.eachAttr("loadlate").forEach(src -> saveImages(src, false));
    }

    private void saveImages(String src, boolean general) {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setPath(String.format("movie/%s", entity.getId()));
        fileEntity.setExtension(FilenameUtils.getExtension(src));
        fileEntity.setFileName(String.join(".", String.valueOf(System.currentTimeMillis()), fileEntity.getExtension()));
        Path path = Paths.get(String.join(File.separator, uploadDir, fileEntity.getPath(), fileEntity.getFileName()));

        if (fileService.downloadFile(URI.create(src), path.normalize().toString())) {
            try {
                fileEntity.setContentType(Files.probeContentType(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
            fileEntity.setExtension(FilenameUtils.getExtension(path.normalize().toString()));
            FileEntity saveFileEntity = fileService.save(fileEntity);
            String sql = "INSERT INTO movie_file (movie_id, file_id, general) values (%s, %s, %s);";
            jdbcTemplate.execute(String.format(sql, entity.getId(), saveFileEntity.getId(), general));
        }
    }

}
