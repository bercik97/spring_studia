package com.jobbed.api.workplace.domain;

import com.jobbed.api.workplace.domain.command.CreateWorkplaceCommand;
import com.jobbed.api.workplace.domain.command.UpdateWorkplaceCommand;
import com.jobbed.api.workplace.domain.vo.WorkplaceLocation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class WorkplaceFactory {

    public static WorkplaceAggregate create(CreateWorkplaceCommand command) {
        WorkplaceLocation location = command.location();
        return WorkplaceAggregate.builder()
                .name(command.name())
                .address(command.address())
                .description(command.description())
                .latitude(location.latitude())
                .longitude(location.longitude())
                .companyName(command.companyName())
                .build();
    }

    public static WorkplaceAggregate update(WorkplaceAggregate workplaceAggregate, UpdateWorkplaceCommand command) {
        workplaceAggregate.setName(command.name());
        workplaceAggregate.setDescription(command.description());
        return workplaceAggregate;
    }
}
