package am.imdb.films.util.parser;

import am.imdb.films.persistence.entity.CountryEntity;
import am.imdb.films.persistence.entity.GenreEntity;
import am.imdb.films.persistence.entity.LanguageEntity;
import am.imdb.films.service.CountryService;
import am.imdb.films.service.GenreService;
import am.imdb.films.service.LanguageService;
import am.imdb.films.service.dto.CountryDto;
import am.imdb.films.service.dto.GenreDto;
import am.imdb.films.service.dto.LanguageDto;
import am.imdb.films.service.dto.MovieDto;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieParser {

    private final GenreService genreService;
    private final LanguageService languageService;
    private final CountryService countryService;

    @Autowired
    public MovieParser(GenreService genreService, LanguageService languageService, CountryService countryService) {
        this.genreService = genreService;
        this.languageService = languageService;
        this.countryService = countryService;
    }


    public List<MovieDto> csvParser(MultipartFile csvFile) throws IOException {

        try (Reader reader = new BufferedReader(new InputStreamReader(csvFile.getInputStream()))) {
            return new CsvToBeanBuilder<MovieDto>(reader)
                    .withType(MovieDto.class)
                    .build()
                    .parse();

//            return parse.stream()
//                    .peek(movieDto -> {
//                        movieDto.setGenres(GenreDto.toDto(genreService.getByNameIn(movieDto.getPersonNames())));
//                        movieDto.setCountries(CountryDto.toDto(countryService.getByNameIn(movieDto.getCountryNames())));
//                        movieDto.setLanguages(LanguageDto.toDto(languageService.getByNameIn(movieDto.getLanguageNames())));
//                    }).collect(Collectors.toList());

        } catch (IOException e) {
            throw new FileNotFoundException("file not found");
        }
    }
}
