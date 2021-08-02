package dev.kangoo.core.support;

import io.quarkus.mongodb.panache.PanacheQuery;

public class PaginationUtils {

    public static <T> PageResponse<T> generatePageResponse(PanacheQuery<T> page) {
        PageResponse<T> pageResponse = new PageResponse<>();
        pageResponse.setContent(page.list());
        pageResponse.setPageSize(page.page().size);
        pageResponse.setPageIndex(page.page().index);
        pageResponse.setTotalPages(page.pageCount());
        pageResponse.setTotalElements(page.count());
        return pageResponse;
    }

}
