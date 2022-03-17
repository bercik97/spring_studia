package com.jobbed.api.shared.pageable;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageUtil {

    public static <T> PageResponse<T> toPageResponse(Page<T> page) {
        Pageable pageable = page.getPageable();
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        PageResponse.Pagination pagination = new PageResponse.Pagination(pageNumber, page.getTotalElements(), page.getTotalPages(), pageSize);
        return new PageResponse<>(page.getContent(), pagination);
    }
}
