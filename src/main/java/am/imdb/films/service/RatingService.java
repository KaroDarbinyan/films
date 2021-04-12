package am.imdb.films.service;

import am.imdb.films.exception.EntityNotFoundException;
import am.imdb.films.persistence.entity.RatingEntity;
import am.imdb.films.persistence.repository.RatingRepository;
import am.imdb.films.service.control.CsvControl;
import am.imdb.films.service.criteria.SearchCriteria;
import am.imdb.films.service.dto.RatingDto;
import am.imdb.films.util.model.csv.Rating;
import am.imdb.films.service.model.wrapper.QueryResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class RatingService {

    private final MovieService movieService;
    private final RatingRepository ratingRepository;
    private final JdbcTemplate jdbcTemplate;
    private final CsvControl<Rating> csvControl;

    @Autowired
    public RatingService(RatingRepository ratingRepository, MovieService movieService, JdbcTemplate jdbcTemplate, CsvControl<Rating> csvControl) {
        this.ratingRepository = ratingRepository;
        this.movieService = movieService;
        this.jdbcTemplate = jdbcTemplate;
        this.csvControl = csvControl;
    }

    public RatingDto createRating(RatingDto ratingDto) {
        RatingEntity ratingEntity = RatingDto.toEntity(ratingDto, new RatingEntity());
        RatingEntity entity = ratingRepository.save(ratingEntity);
        return RatingDto.toDto(entity);
    }

    public RatingDto getRating(Long id) throws EntityNotFoundException {
        RatingEntity rating = ratingRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        return RatingDto.toDto(rating);
    }

    public RatingDto updateRating(Long id, RatingDto ratingDto) throws EntityNotFoundException {
        RatingEntity ratingEntity = ratingRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        RatingDto.toEntity(ratingDto, ratingEntity);
        return RatingDto.toDto(ratingRepository.save(ratingEntity));
    }

    public QueryResponseWrapper<RatingDto> getRatings(SearchCriteria criteria) {
        Page<RatingDto> content = ratingRepository.findAllWithPagination(criteria.composePageRequest());
        return new QueryResponseWrapper<>(content);
    }

    public void deleteRating(Long id) throws EntityNotFoundException {
        ratingRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        ratingRepository.deleteById(id);
    }

    public Map<String, Integer> parseCsv(MultipartFile csvFile) {

        String insertQuery = "INSERT INTO rating (movie_id, average_rating, num_votes) VALUES %s;";
        List<List<Rating>> ratings = csvControl.getEntitiesFromCsv(csvFile, Rating.class);
        Map<String, Long> moviesImdbIdsAndIds = movieService.getMoviesImdbIdsAndIds();
        AtomicInteger existed = new AtomicInteger();


        List<List<String>> valuesList = ratings.stream()
                .map(ratingList -> ratingList.stream()
                        .filter(rating -> {
                            boolean contains = Objects.nonNull(rating.getMovieId()) && moviesImdbIdsAndIds.containsKey(rating.getMovieId());
                            if (contains) existed.getAndIncrement();
                            return contains;
                        })
                        .map(rating -> String.format("(%s, '%s', '%s')",
                                moviesImdbIdsAndIds.get(rating.getMovieId()),
                                rating.getAverageRating(),
                                rating.getNumVotes()
                        ))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        int saved = 0;
        for (List<String> values : valuesList) {
            String sql = String.format(insertQuery, String.join(",", values));
            jdbcTemplate.execute(sql);
            saved += values.size();
        }

        return Map.of("saved", saved, "existed", existed.intValue());
    }
}
