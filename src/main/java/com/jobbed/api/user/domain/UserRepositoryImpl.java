package com.jobbed.api.user.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserAggregate save(UserAggregate user) {
        UserEntity entity = user.toEntity();
        jpaRepository.save(entity);
        return entity.toAggregate();
    }

    @Override
    public void update(UserAggregate user) {
        jpaRepository.findByEmail(user.getEmail())
                .ifPresent(userEntity -> {
                    userEntity.setName(user.getName());
                    userEntity.setSurname(user.getSurname());
                    userEntity.setPhoneNumber(user.getPhoneNumber());
                    userEntity.setPhoneNumberCode(user.getPhoneNumberCode());
                    jpaRepository.save(userEntity);
                });
    }

    @Override
    public void enableById(long id) {
        jpaRepository.enableById(id);
    }

    @Override
    public void setPasswordByEmail(String newPassword, String email) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        jpaRepository.setPasswordByEmail(encodedPassword, email);
    }

    @Override
    public Optional<UserAggregate> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(UserEntity::toAggregate);
    }

    @Override
    public Optional<UserAggregate> findByIdAndCompanyName(long id, String companyName) {
        return jpaRepository.findByIdAndCompanyName(id, companyName).map(UserEntity::toAggregate);
    }

    @Override
    public Optional<UserAggregate> findByEmailAndCompanyName(String email, String companyName) {
        return jpaRepository.findByEmailAndCompanyName(email, companyName).map(UserEntity::toAggregate);
    }

    @Override
    public List<UserAggregate> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(UserEntity::toAggregate)
                .toList();
    }

    @Override
    public Page<UserAggregate> findAllByCompanyNameExceptMe(Pageable pageable, String companyName, long userId) {
        Page<UserEntity> employees = jpaRepository.findAllByCompanyNameAndIdIsNot(companyName, userId, pageable);
        return new PageImpl<>(employees
                .stream()
                .map(UserEntity::toAggregate)
                .collect(Collectors.toList()), pageable, employees.getTotalElements());
    }

    @Override
    public void deleteById(UserAggregate userAggregate) {
        jpaRepository.deleteById(userAggregate.getId());
    }
}
