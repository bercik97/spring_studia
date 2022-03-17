package com.jobbed.api.workplace.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
class WorkplaceConfiguration {

    @Bean
    public WorkplaceFacade workplaceFacade(WorkplaceRepository repository) {
        WorkplaceValidator validator = new WorkplaceValidator(repository);
        WorkplaceService service = new WorkplaceService(repository, validator);
        return new WorkplaceFacade(service);
    }

    public WorkplaceFacade workplaceFacade(ConcurrentHashMap<Long, WorkplaceEntity> db) {
        WorkplaceInMemoryRepository repository = new WorkplaceInMemoryRepository(db);
        WorkplaceValidator validator = new WorkplaceValidator(repository);
        WorkplaceService service = new WorkplaceService(repository, validator);
        return new WorkplaceFacade(service);
    }
}
