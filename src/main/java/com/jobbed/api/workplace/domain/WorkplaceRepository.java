package com.jobbed.api.workplace.domain;

import com.jobbed.api.shared.vo.IdVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface WorkplaceRepository {

    IdVO<Long> save(WorkplaceAggregate workplace);

    void update(WorkplaceAggregate workplace);

    Page<WorkplaceAggregate> findAllByCompanyName(Pageable pageable, String companyName);

    Optional<WorkplaceAggregate> findByIdAndCompanyName(long id, String companyName);

    Optional<WorkplaceAggregate> findByNameAndCompanyName(String name, String companyName);

    void deleteById(WorkplaceAggregate workplace);
}
