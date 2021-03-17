package am.imdb.films.service;

import am.imdb.films.persistence.entity.LanguageEntity;
import am.imdb.films.persistence.entity.MovieEntity;
import am.imdb.films.persistence.repository.LanguageRepository;
import am.imdb.films.service.dto.LanguageDto;
import am.imdb.films.service.dto.MovieDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class LanguageService {

    private final LanguageRepository languageRepository;

    @Autowired
    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }


    public LanguageDto createLanguage(LanguageDto languageDto) {
        LanguageEntity languageEntity = LanguageDto.toEntity(languageDto);
        languageRepository.save(languageEntity);
        return LanguageDto.toDto(languageEntity);
    }

    public List<LanguageDto> createLanguages(Set<LanguageDto> languageDtoSet) {
        List<LanguageEntity> languageEntityList = LanguageDto.toEntity(languageDtoSet);
        languageRepository.saveAll(languageEntityList);
        return LanguageDto.toDto(languageEntityList);
    }

    public LanguageEntity getLanguageByName(String name) {
        return languageRepository.findLanguageByName(name);
    }
}
