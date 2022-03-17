package com.jobbed.api.workplace.domain;

import com.jobbed.api.shared.vo.IdVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class WorkplaceRepositoryImpl implements WorkplaceRepository {

    private final WorkplaceJpaRepository jpaRepository;

    @Override
    public IdVO<Long> save(WorkplaceAggregate workplace) {
        WorkplaceEntity entity = workplace.toEntity();
        jpaRepository.save(entity);
        return new IdVO<>(entity.getId());
    }

    @Override
    public void update(WorkplaceAggregate workplace) {
        jpaRepository.findByIdAndCompanyName(workplace.getId(), workplace.getCompanyName())
                .ifPresent(workplaceEntity -> {
                    workplaceEntity.setName(workplace.getName());
                    workplaceEntity.setDescription(workplace.getDescription());
                    jpaRepository.save(workplaceEntity);
                });
    }

    @Override
    public Page<WorkplaceAggregate> findAllByCompanyName(Pageable pageable, String companyName) {
        Page<WorkplaceEntity> workplaces = jpaRepository.findAllByCompanyName(companyName, pageable);
        return new PageImpl<>(workplaces
                .stream()
                .map(WorkplaceEntity::toAggregate)
                .collect(Collectors.toList()), pageable, workplaces.getTotalElements());
    }

    @Override
    public Optional<WorkplaceAggregate> findByIdAndCompanyName(long id, String companyName) {
        return jpaRepository.findByIdAndCompanyName(id, companyName).map(WorkplaceEntity::toAggregate);
    }

    @Override
    public Optional<WorkplaceAggregate> findByNameAndCompanyName(String name, String companyName) {
        return jpaRepository.findByNameAndCompanyName(name, companyName).map(WorkplaceEntity::toAggregate);
    }

    @Override
    public void deleteById(WorkplaceAggregate workplace) {
        jpaRepository.deleteById(workplace.getId());
    }
}
