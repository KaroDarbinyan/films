package am.imdb.films.service;

import am.imdb.films.exception.EntityNotFoundException;
import am.imdb.films.persistence.entity.LanguageEntity;
import am.imdb.films.persistence.repository.LanguageRepository;
import am.imdb.films.service.criteria.SearchCriteria;
import am.imdb.films.service.dto.LanguageDto;
import am.imdb.films.service.model.wrapper.QueryResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

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
        LanguageEntity languageEntity = languageRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        LanguageDto.toEntity(languageDto, languageEntity);
        return LanguageDto.toDto(languageRepository.save(languageEntity));
    }

    public QueryResponseWrapper<LanguageDto> getLanguages(SearchCriteria criteria) {
        Page<LanguageDto> content = languageRepository.findAllWithPagination(criteria.composePageRequest());
        return new QueryResponseWrapper<>(content);
    }

    public void deleteLanguage(Long id) throws EntityNotFoundException {
        languageRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        languageRepository.deleteById(id);
    }

}
