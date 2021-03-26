package am.imdb.films.service;

import am.imdb.films.exception.EntityNotFoundException;
import am.imdb.films.persistence.entity.MovieEntity;
import am.imdb.films.persistence.entity.RatingEntity;
import am.imdb.films.persistence.repository.RatingRepository;
import am.imdb.films.service.criteria.SearchCriteria;
import am.imdb.films.service.dto.RatingDto;
import am.imdb.films.service.model.wrapper.QueryResponseWrapper;
import am.imdb.films.util.parser.RatingParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RatingService {

    private final RatingParser ratingParser;
    private final MovieService movieService;
    private final RatingRepository ratingRepository;
    private final JdbcTemplate jdbcTemplate;
    private final Logger logger = LoggerFactory.getLogger(RatingService.class);


    @Autowired
    public RatingService(RatingRepository ratingRepository, RatingParser ratingParser, MovieService movieService, JdbcTemplate jdbcTemplate) {
        this.ratingRepository = ratingRepository;
        this.ratingParser = ratingParser;
        this.movieService = movieService;
        this.jdbcTemplate = jdbcTemplate;
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

    public Map<String, Integer> parseCSV(MultipartFile csvFile) throws IOException {
        List<RatingDto> ratingDtoList = ratingParser.csvParser(csvFile);

        return batchInsert(ratingDtoList);
    }

    public Map<String, Integer> batchInsert(List<RatingDto> ratingDtoList) {
        String insertQuery = "INSERT INTO rating (movie_id, average_rating, num_votes) VALUES %s;";
        int existed = (int) ratingRepository.count();
        int size = ratingDtoList.size();
        int counter = 0;
        int error = 0;
        int saved = 0;

        List<String> list = new ArrayList<>();
        Map<String, RatingDto> tempRatings = new HashMap<>();

        for (RatingDto ratingDto : ratingDtoList) {
            list.add(ratingDto.getMovieId());
            tempRatings.put(ratingDto.getMovieId(), ratingDto);

            if ((counter + 1) % 1000 == 0 || (counter + 1) == size) {

                try {
                    List<MovieEntity> idAndImdbIdByImdbIdIn = movieService.getByImdbIdIn(list);
                    list.clear();
                    for (MovieEntity movieEntity : idAndImdbIdByImdbIdIn) {
                        list.add(String.format("(%s, '%s', '%s')",
                                movieEntity.getId(),
                                tempRatings.get(movieEntity.getImdbId()).getAverageRating(),
                                tempRatings.get(movieEntity.getImdbId()).getNumVotes()));
                    }
                    String sql = String.format(insertQuery, String.join(",", list));
                    jdbcTemplate.execute(sql);

                    saved += list.size();
                } catch (Exception e) {
                    error += list.size();
                    logger.error(String.format("%s not preserved", error));
                }
                list.clear();
                tempRatings.clear();
                logger.info(String.format("In progress, [saved -> %s], [existed -> %s], [not preserved -> %s] of %s",
                        saved, existed, error, size));
            }

            counter++;
        }

        return Map.of(
                "size", size,
                "saved", saved,
                "error", error,
                "existed", existed,
                "not_preserved", size - saved
        );
    }

}
