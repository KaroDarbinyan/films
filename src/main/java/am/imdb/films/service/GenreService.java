package am.imdb.films.service;

import am.imdb.films.persistence.entity.CountryEntity;
import am.imdb.films.persistence.entity.GenreEntity;
import am.imdb.films.persistence.entity.LanguageEntity;
import am.imdb.films.persistence.repository.GenreRepository;
import am.imdb.films.service.dto.CountryDto;
import am.imdb.films.service.dto.GenreDto;
import am.imdb.films.service.dto.LanguageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }


    public List<GenreDto> createGenres(Set<GenreDto> genreDtoSet) {
        List<GenreEntity> genreEntityList = GenreDto.toEntity(genreDtoSet);
        genreRepository.saveAll(genreEntityList);
        return GenreDto.toDto(genreEntityList);
    }

    public GenreEntity getLanguageByName(String name) {
        return genreRepository.findLanguageByName(name);
    }


    public GenreDto createGenre(GenreDto genreDto) {
        GenreEntity genreEntity = GenreDto.toEntity(genreDto);
        genreRepository.save(genreEntity);
        return GenreDto.toDto(genreEntity);
    }
}
