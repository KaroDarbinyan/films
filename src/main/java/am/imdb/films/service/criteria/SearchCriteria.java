package am.imdb.films.service.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.Objects;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer page;
    private Integer size;

    private String sortField;
    private String sortDirection;


    public Pageable composePageRequest() {
        if (size != null && size == Integer.MAX_VALUE) {
            return null;
        }

        int page = this.page == null ? 0 : this.page;
        int size = this.size == null ? 20 : this.size;

        if (Objects.nonNull(sortField)) {
            return PageRequest.of(page, size, Sort.by(getDirection(), sortField));
        }

        return PageRequest.of(page, size);
    }

    private Sort.Direction getDirection() {
        if (Sort.Direction.ASC.name().equalsIgnoreCase(sortDirection)) return Sort.Direction.ASC;
        else if (Sort.Direction.DESC.name().equalsIgnoreCase(sortDirection)) return Sort.Direction.DESC;
        else return Sort.DEFAULT_DIRECTION;
    }

}
