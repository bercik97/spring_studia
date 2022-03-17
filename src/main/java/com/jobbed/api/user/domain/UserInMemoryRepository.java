package com.jobbed.api.user.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class UserInMemoryRepository implements UserRepository {

    private final ConcurrentHashMap<Long, UserEntity> db;

    private static long idCounter = 0;

    @Override
    public UserAggregate save(UserAggregate user) {
        UserEntity entity = user.toEntity();
        entity.setId(++idCounter);
        db.put(idCounter, entity);
        return entity.toAggregate();
    }

    @Override
    public void update(UserAggregate user) {
        findByEmail(user.getEmail())
                .ifPresent(toUpdate -> {
                    toUpdate.setName(user.getName());
                    toUpdate.setSurname(user.getSurname());
                    toUpdate.setPhoneNumber(user.getPhoneNumber());
                    toUpdate.setPhoneNumberCode(user.getPhoneNumberCode());
                    db.put(toUpdate.getId(), toUpdate.toEntity());
                });
    }

    @Override
    public void enableById(long id) {
        db.values()
                .stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .ifPresent(user -> {
                    user.setEnabled(true);
                    db.put(user.getId(), user);
                });
    }

    @Override
    public void setPasswordByEmail(String newPassword, String email) {
        db.values()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .ifPresent(user -> {
                    user.setPassword(newPassword);
                    db.put(user.getId(), user);
                });
    }

    @Override
    public Optional<UserAggregate> findByEmail(String email) {
        return db.values()
                .stream()
                .filter(user -> email.equals(user.getEmail()))
                .map(UserEntity::toAggregate)
                .findFirst();
    }

    @Override
    public Optional<UserAggregate> findByIdAndCompanyName(long id, String companyName) {
        return db.values()
                .stream()
                .filter(user -> user.getId() == id)
                .filter(user -> user.getCompanyName().equals(companyName))
                .map(UserEntity::toAggregate)
                .findFirst();
    }

    @Override
    public Optional<UserAggregate> findByEmailAndCompanyName(String email, String companyName) {
        return db.values()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .filter(user -> user.getCompanyName().equals(companyName))
                .map(UserEntity::toAggregate)
                .findFirst();
    }

    @Override
    public List<UserAggregate> findAll() {
        return db.values()
                .stream()
                .map(UserEntity::toAggregate)
                .toList();
    }

    @Override
    public Page<UserAggregate> findAllByCompanyNameExceptMe(Pageable pageable, String companyName, long userId) {
        return new PageImpl<>(db.values()
                .stream()
                .filter(user -> companyName.equals(user.getCompanyName()))
                .filter(user -> user.getId() != userId)
                .map(UserEntity::toAggregate)
                .collect(Collectors.toList()), pageable, db.values().size());
    }

    @Override
    public void deleteById(UserAggregate userAggregate) {
        db.remove(userAggregate.getId());
    }
}
