package dev.kangoo.customers.core.support;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageResponse<T> {

    private List<T> content;
    private long totalElements;
    private int totalPages;
    private int pageIndex;
    private int pageSize;

}
