package am.imdb.films.service;


import am.imdb.films.exception.EntityNotFoundException;
import am.imdb.films.persistence.entity.CountryEntity;
import am.imdb.films.persistence.repository.CountryRepository;
import am.imdb.films.service.dto.CountryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        CountryEntity entity = countryRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        CountryEntity countryEntity = CountryDto.toEntity(countryDto, entity);
        return CountryDto.toDto(countryRepository.save(countryEntity));
    }

    public List<CountryDto> getCountries() {
        List<CountryEntity> countryEntities = countryRepository.findAll();
        return CountryDto.toDtoList(countryEntities);
    }

    public void deleteCountry(Long id) throws EntityNotFoundException {
        countryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        countryRepository.deleteById(id);
    }
}
