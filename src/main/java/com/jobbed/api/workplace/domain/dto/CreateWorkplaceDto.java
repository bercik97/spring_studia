package com.jobbed.api.workplace.domain.dto;

import com.jobbed.api.workplace.domain.vo.WorkplaceLocation;

public record CreateWorkplaceDto(String name, String address, String description, WorkplaceLocation location) {
}
