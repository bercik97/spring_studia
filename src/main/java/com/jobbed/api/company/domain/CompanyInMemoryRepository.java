package com.jobbed.api.company.domain;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
class CompanyInMemoryRepository implements CompanyRepository {

    private final ConcurrentHashMap<Long, CompanyEntity> db;

    private static long idCounter = 0;

    @Override
    public void save(CompanyAggregate company) {
        company.setId(++idCounter);
        db.put(idCounter, company.toEntity());
    }

    @Override
    public Optional<CompanyAggregate> findByName(String name) {
        return db.values()
                .stream()
                .filter(company -> name.equals(company.getName()))
                .map(CompanyEntity::toAggregate)
                .findFirst();
    }
}
