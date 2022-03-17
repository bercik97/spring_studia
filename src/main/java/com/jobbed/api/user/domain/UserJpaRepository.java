package com.jobbed.api.user.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    @Modifying
    @Query("update UserEntity u set u.isEnabled = true where u.id = ?1")
    void enableById(long id);

    @Modifying
    @Query("update UserEntity u set u.password = ?1 where u.email = ?2")
    void setPasswordByEmail(String password, String email);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByIdAndCompanyName(long id, String companyName);

    Optional<UserEntity> findByEmailAndCompanyName(String email, String companyName);

    Page<UserEntity> findAllByCompanyNameAndIdIsNot(String companyName, long userId, Pageable pageable);
}
