package com.jobbed.api.user.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    UserAggregate save(UserAggregate user);

    void update(UserAggregate user);

    void enableById(long id);

    void setPasswordByEmail(String newPassword, String email);

    Optional<UserAggregate> findByEmail(String email);

    Optional<UserAggregate> findByIdAndCompanyName(long id, String companyName);

    Optional<UserAggregate> findByEmailAndCompanyName(String email, String companyName);

    List<UserAggregate> findAll();

    Page<UserAggregate> findAllByCompanyNameExceptMe(Pageable pageable, String companyName, long userId);

    void deleteById(UserAggregate userAggregate);
}
