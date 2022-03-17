package com.jobbed.api.workplace.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface WorkplaceJpaRepository extends JpaRepository<WorkplaceEntity, Long> {

    Page<WorkplaceEntity> findAllByCompanyName(String companyName, Pageable pageable);

    Optional<WorkplaceEntity> findByIdAndCompanyName(long id, String companyName);

    Optional<WorkplaceEntity> findByNameAndCompanyName(String name, String companyName);
}
