package am.imdb.films.service.model.wrapper;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class QueryResponseWrapper<T> {

    private int number;
    private int size;
    private int numberOfElements;
    private int totalPages;
    private long totalElements;
    private List<T> content;

    public QueryResponseWrapper(Page<T> page) {
        this.number = page.getNumber();
        this.size = page.getSize();
        this.numberOfElements = page.getNumberOfElements();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.content = page.getContent();

    }

}
