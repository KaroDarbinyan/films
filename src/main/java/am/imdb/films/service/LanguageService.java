package am.imdb.films.service;

import am.imdb.films.exception.EntityNotFoundException;
import am.imdb.films.persistence.entity.LanguageEntity;
import am.imdb.films.persistence.repository.LanguageRepository;
import am.imdb.films.service.dto.LanguageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageService {

    private final LanguageRepository languageRepository;

    @Autowired
    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public LanguageDto createLanguage(LanguageDto languageDto) {
        LanguageEntity languageEntity = LanguageDto.toEntity(languageDto, new LanguageEntity());
        LanguageEntity entity = languageRepository.save(languageEntity);
        return LanguageDto.toDto(entity);
    }

    public LanguageDto getLanguage(Long id) throws EntityNotFoundException {
        LanguageEntity language = languageRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return LanguageDto.toDto(language);
    }

    public LanguageDto updateLanguage(Long id, LanguageDto languageDto) throws EntityNotFoundException {
        LanguageEntity entity = languageRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        LanguageEntity languageEntity = LanguageDto.toEntity(languageDto, entity);
        return LanguageDto.toDto(languageRepository.save(languageEntity));
    }

    public List<LanguageDto> getLanguages() {
        List<LanguageEntity> languageEntities = languageRepository.findAll();
        return LanguageDto.toDtoList(languageEntities);
    }

    public void deleteLanguage(Long id) throws EntityNotFoundException {
        languageRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        languageRepository.deleteById(id);
    }
}
