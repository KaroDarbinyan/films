package am.imdb.films.service;

import am.imdb.films.exception.EntityNotFoundException;
import am.imdb.films.persistence.entity.GenreEntity;
import am.imdb.films.persistence.repository.GenreRepository;
import am.imdb.films.service.criteria.SearchCriteria;
import am.imdb.films.service.dto.GenreDto;
import am.imdb.films.service.model.wrapper.QueryResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }


    public GenreDto createGenre(GenreDto genreDto) {
        GenreEntity genreEntity = GenreDto.toEntity(genreDto, new GenreEntity());
        GenreEntity entity = genreRepository.save(genreEntity);
        return GenreDto.toDto(entity);
    }

    public GenreDto getGenre(Long id) throws EntityNotFoundException {
        GenreEntity genre = genreRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        return GenreDto.toDto(genre);
    }

    public GenreDto updateGenre(Long id, GenreDto genreDto) throws EntityNotFoundException {
        GenreEntity genreEntity = genreRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        GenreDto.toEntity(genreDto, genreEntity);
        return GenreDto.toDto(genreRepository.save(genreEntity));
    }

    public QueryResponseWrapper<GenreDto> getGenres(SearchCriteria criteria) {
        Page<GenreDto> content = genreRepository.findAllWithPagination(criteria.composePageRequest());
        return new QueryResponseWrapper<>(content);
    }

    public void deleteGenre(Long id) throws EntityNotFoundException {
        genreRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        genreRepository.deleteById(id);
    }

    public Set<GenreEntity> saveAll(Set<GenreEntity> genres) {
        return Set.copyOf(genreRepository.saveAll(genres));
    }
}
