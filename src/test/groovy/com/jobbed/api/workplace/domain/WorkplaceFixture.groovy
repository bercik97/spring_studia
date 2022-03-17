package com.jobbed.api.workplace.domain

import com.jobbed.api.workplace.domain.dto.CreateWorkplaceDto
import com.jobbed.api.workplace.domain.dto.UpdateWorkplaceDto
import com.jobbed.api.workplace.domain.vo.WorkplaceLocation

trait WorkplaceFixture {

    static WorkplaceEntity createWorkplaceEntity(def id = 1L, def companyName = 'Jobbed') {
        def entity = new WorkplaceEntity(
                'Office',
                'Diamond street 1/4',
                'Every monday at 6pm',
                10.1, 10.1,
                companyName)
        entity.setId(id)
        return entity
    }

    static CreateWorkplaceDto createWorkplaceDto(def name = 'Office',
                                                 def address = 'Diamond street 1/4',
                                                 description = 'Every monday at 6pm',
                                                 def location = new WorkplaceLocation(10.1, 10.1)) {
        return new CreateWorkplaceDto(name, address, description, location)
    }

    static UpdateWorkplaceDto createUpdateWorkplaceDto(def name = 'Office',
                                                       def description = 'Every monday at 6pm') {
        return new UpdateWorkplaceDto(name, description)
    }
}
