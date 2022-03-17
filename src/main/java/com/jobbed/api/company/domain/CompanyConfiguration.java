package com.jobbed.api.company.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
class CompanyConfiguration {

    @Bean
    public CompanyFacade companyFacade(CompanyRepository repository) {
        CompanyValidator validator = new CompanyValidator(repository);
        CompanyService service = new CompanyService(repository, validator);
        return new CompanyFacade(service);
    }

    public CompanyFacade companyFacade(ConcurrentHashMap<Long, CompanyEntity> db) {
        CompanyInMemoryRepository repository = new CompanyInMemoryRepository(db);
        CompanyValidator validator = new CompanyValidator(repository);
        CompanyService service = new CompanyService(repository, validator);
        return new CompanyFacade(service);
    }
}
