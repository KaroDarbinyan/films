package am.imdb.films.service;


import am.imdb.films.persistence.entity.CountryEntity;
import am.imdb.films.persistence.entity.LanguageEntity;
import am.imdb.films.persistence.repository.CountryRepository;
import am.imdb.films.service.dto.CountryDto;
import am.imdb.films.service.dto.LanguageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }


    public List<CountryDto> getCountries() {
        List<CountryEntity> countries = countryRepository.findAll();
        return CountryDto.toDto(countries);
    }

    public CountryDto getCountry(Long id) throws Exception {
        CountryEntity country = countryRepository.findById(id)
                .orElseThrow(() -> new Exception("country not found"));
        return CountryDto.toDto(country);
    }

    public CountryEntity getLanguageByName(String name) {
        return countryRepository.findLanguageByName(name);
    }

    public CountryDto createCountry(CountryDto countryDto) {
        CountryEntity countryEntity = CountryDto.toEntity(countryDto);
        countryRepository.save(countryEntity);
        return CountryDto.toDto(countryEntity);
    }
}
