package com.jobbed.api.workplace.domain.command;

import com.jobbed.api.workplace.domain.dto.UpdateWorkplaceDto;

public record UpdateWorkplaceCommand(long id, String name, String description, String companyName) {

    public static UpdateWorkplaceCommand of(long id, UpdateWorkplaceDto dto, String companyName) {
        return new UpdateWorkplaceCommand(id, dto.name(), dto.description(), companyName);
    }
}
