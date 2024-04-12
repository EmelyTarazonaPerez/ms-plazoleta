package plazoleta.adapters.driven.jpa.msql.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class DataOrdering {
    private DataOrdering () {
        throw new IllegalStateException("Utility class");
    }
    public static Pageable getOrdering (int page, int size, boolean direction, String property) {
        Sort.Order orderPageable = direction ? Sort.Order.desc(property) : Sort.Order.asc(property);
        Sort sort = Sort.by(orderPageable);
        return PageRequest.of(page, size, sort);
    }

    public static Sort getSort (boolean direction, String property) {
        Sort.Order orderPageable = direction ? Sort.Order.desc(property) : Sort.Order.asc(property);
        return Sort.by(orderPageable);
    }

}
