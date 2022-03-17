package com.jobbed.api.company.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface CompanyJpaRepository extends JpaRepository<CompanyEntity, Long> {

    Optional<CompanyEntity> findByName(String name);
}
