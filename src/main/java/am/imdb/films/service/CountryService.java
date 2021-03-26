package am.imdb.films.service;


import am.imdb.films.exception.EntityNotFoundException;
import am.imdb.films.persistence.entity.CountryEntity;
import am.imdb.films.persistence.repository.CountryRepository;
import am.imdb.films.service.criteria.SearchCriteria;
import am.imdb.films.service.dto.CountryDto;
import am.imdb.films.service.model.wrapper.QueryResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }


    public CountryDto createCountry(CountryDto countryDto) {
        CountryEntity countryEntity = CountryDto.toEntity(countryDto, new CountryEntity());
        CountryEntity entity = countryRepository.save(countryEntity);
        return CountryDto.toDto(entity);
    }

    public CountryDto getCountry(Long id) throws EntityNotFoundException {
        CountryEntity country = countryRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        return CountryDto.toDto(country);
    }

    public CountryDto updateCountry(Long id, CountryDto countryDto) throws EntityNotFoundException {
        CountryEntity countryEntity = countryRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        CountryDto.toEntity(countryDto, countryEntity);
        return CountryDto.toDto(countryRepository.save(countryEntity));
    }

    public QueryResponseWrapper<CountryDto> getCountries(SearchCriteria criteria) {
        Page<CountryDto> content = countryRepository.findAllWithPagination(criteria.composePageRequest());
        return new QueryResponseWrapper<>(content);
    }

    public void deleteCountry(Long id) throws EntityNotFoundException {
        countryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        countryRepository.deleteById(id);
    }
}
