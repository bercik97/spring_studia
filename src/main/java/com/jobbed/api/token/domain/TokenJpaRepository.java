package com.jobbed.api.token.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

interface TokenJpaRepository extends JpaRepository<TokenEntity, Long> {

    Optional<TokenEntity> findByCode(String code);

    Optional<TokenEntity> findByCodeAndCompanyName(String name, String companyName);

    List<TokenEntity> findAllByCompanyName(String companyName);

    @Modifying
    @Query("update TokenEntity t set t.code = ?1 where t.id = ?2")
    void setCodeById(String code, long id);
}
