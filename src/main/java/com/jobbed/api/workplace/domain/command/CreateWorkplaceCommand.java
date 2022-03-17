package com.jobbed.api.workplace.domain.command;

import com.jobbed.api.workplace.domain.dto.CreateWorkplaceDto;
import com.jobbed.api.workplace.domain.vo.WorkplaceLocation;

public record CreateWorkplaceCommand(String name, String address, String description, WorkplaceLocation location, String companyName) {

    public static CreateWorkplaceCommand of(CreateWorkplaceDto dto, String companyName) {
        return new CreateWorkplaceCommand(dto.name(), dto.address(), dto.description(), dto.location(), companyName);
    }
}
