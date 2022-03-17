package com.jobbed.api.company.domain;

import java.util.Optional;

public interface CompanyRepository {

    void save(CompanyAggregate company);

    Optional<CompanyAggregate> findByName(String name);
}
