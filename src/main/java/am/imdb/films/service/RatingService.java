package am.imdb.films.service;

import am.imdb.films.persistence.entity.MovieEntity;
import am.imdb.films.persistence.entity.RatingEntity;
import am.imdb.films.persistence.repository.RatingRepository;
import am.imdb.films.service.dto.RatingDto;
import am.imdb.films.util.parser.RatingParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RatingService {

    private final RatingParser ratingParser;
    private final MovieService movieService;
    private final RatingRepository ratingRepository;
    private final Logger logger = LoggerFactory.getLogger(RatingService.class);


    @Autowired
    public RatingService(RatingRepository ratingRepository, RatingParser ratingParser, MovieService movieService) {
        this.ratingRepository = ratingRepository;
        this.ratingParser = ratingParser;
        this.movieService = movieService;
    }

    public List<RatingDto> getRatings() {
        List<RatingEntity> ratings = ratingRepository.findAll();
        return RatingDto.toDto(ratings);
    }

    public RatingDto getRating(Long id) throws Exception {
        RatingEntity rating = ratingRepository.findById(id).orElseThrow(() -> new Exception("person not found"));

        return RatingDto.toDto(rating);
    }

    public Map<String, Long> parseCSV(MultipartFile csvFile) throws IOException {
        List<RatingDto> ratingDtoList = ratingParser.csvParser(csvFile);
        long saved = 0;
        long existed = 0;
        long notPreserved = 0;
        long size = Integer.toUnsignedLong(ratingDtoList.size());
        for (RatingDto ratingDto : ratingDtoList) {
            MovieEntity movieEntity = movieService.findMovieByImdbId(ratingDto.getMovieId());
            try {
                if (movieEntity != null) {
                    if (ratingRepository.findRatingByMovieId(ratingDto.getMovieId()) == null) {
                        ratingRepository.save(RatingDto.toEntity(ratingDto));
                        ++saved;
                    } else {
                        ++existed;
                    }
                } else {
                    logger.error(String.format("There is no movie with this imdbID (%s)", ratingDto.getMovieId()));
                }
            } catch (Exception e) {
                logger.error(String.format("%s not preserved object -> %s", ++notPreserved, ratingDto.getId()));
            }
            logger.info(String.format("In progress, [saved -> %s], [existed -> %s], [not preserved -> %s] of %s", saved, existed, notPreserved, size));

        }

        Map<String, Long> result = new HashMap<>();
        result.put("size", size);
        result.put("saved", saved);
        result.put("existed", existed);
        result.put("not_preserved", notPreserved);
        return result;
    }
}
