package com.jobbed.api.company.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
class CompanyRepositoryImpl implements CompanyRepository {

    private final CompanyJpaRepository jpaRepository;

    @Override
    public void save(CompanyAggregate company) {
        jpaRepository.save(company.toEntity());
    }

    @Override
    public Optional<CompanyAggregate> findByName(String name) {
        return jpaRepository.findByName(name).map(CompanyEntity::toAggregate);
    }
}
