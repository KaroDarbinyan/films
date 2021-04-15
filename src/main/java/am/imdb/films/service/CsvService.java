package am.imdb.films.service;

import java.util.List;

public interface CsvService<T> {

    void saveAll(List<T> list);
}
