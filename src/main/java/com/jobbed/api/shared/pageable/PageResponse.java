package com.jobbed.api.shared.pageable;

import java.util.List;

public record PageResponse<T>(List<T> data, Pagination pagination) {

    public record Pagination(int pageNumber, long totalElements, int totalPages, int pageSize) {
    }
}
