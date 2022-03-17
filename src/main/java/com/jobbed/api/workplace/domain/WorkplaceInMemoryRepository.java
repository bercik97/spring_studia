package com.jobbed.api.workplace.domain;

import com.jobbed.api.shared.vo.IdVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class WorkplaceInMemoryRepository implements WorkplaceRepository {

    private final ConcurrentHashMap<Long, WorkplaceEntity> db;

    private static long idCounter = 0;

    @Override
    public IdVO<Long> save(WorkplaceAggregate workplace) {
        workplace.setId(++idCounter);
        db.put(idCounter, workplace.toEntity());
        return new IdVO<>(idCounter);
    }

    @Override
    public void update(WorkplaceAggregate workplace) {
        findByIdAndCompanyName(workplace.getId(), workplace.getCompanyName())
                .ifPresent(toUpdate -> {
                    toUpdate.setName(workplace.getName());
                    toUpdate.setDescription(workplace.getDescription());
                    db.put(toUpdate.getId(), toUpdate.toEntity());
                });
    }

    @Override
    public Page<WorkplaceAggregate> findAllByCompanyName(Pageable pageable, String companyName) {
        return new PageImpl<>(db.values()
                .stream()
                .filter(workplace -> companyName.equals(workplace.getCompanyName()))
                .map(WorkplaceEntity::toAggregate)
                .collect(Collectors.toList()), pageable, db.values().size());
    }

    @Override
    public Optional<WorkplaceAggregate> findByIdAndCompanyName(long id, String companyName) {
        return db.values()
                .stream()
                .filter(workplace -> workplace.getId() == id)
                .filter(workplace -> workplace.getCompanyName().equals(companyName))
                .map(WorkplaceEntity::toAggregate)
                .findFirst();
    }

    @Override
    public Optional<WorkplaceAggregate> findByNameAndCompanyName(String name, String companyName) {
        return db.values()
                .stream()
                .filter(workplace -> name.equals(workplace.getName()))
                .filter(workplace -> companyName.equals(workplace.getCompanyName()))
                .map(WorkplaceEntity::toAggregate)
                .findFirst();
    }

    @Override
    public void deleteById(WorkplaceAggregate workplace) {
        db.remove(workplace.getId());
    }
}
