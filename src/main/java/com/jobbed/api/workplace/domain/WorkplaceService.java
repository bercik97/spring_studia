package com.jobbed.api.workplace.domain;

import com.jobbed.api.shared.error.ErrorStatus;
import com.jobbed.api.shared.exception.type.NotFoundException;
import com.jobbed.api.shared.vo.IdVO;
import com.jobbed.api.workplace.domain.command.CreateWorkplaceCommand;
import com.jobbed.api.workplace.domain.command.DeleteWorkplaceCommand;
import com.jobbed.api.workplace.domain.command.UpdateWorkplaceCommand;
import com.jobbed.api.workplace.domain.dto.WorkplaceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class WorkplaceService {

    private final WorkplaceRepository repository;
    private final WorkplaceValidator validator;

    public IdVO<Long> create(CreateWorkplaceCommand command) {
        validator.validate(command);
        return repository.save(WorkplaceFactory.create(command));
    }

    public void updateByIdAndCompanyName(UpdateWorkplaceCommand command) {
        WorkplaceAggregate workplace = repository.findByIdAndCompanyName(command.id(), command.companyName()).orElseThrow(() -> new NotFoundException(ErrorStatus.WORKPLACE_NOT_FOUND));
        validator.validate(command);
        repository.update(WorkplaceFactory.update(workplace, command));
    }

    public Page<WorkplaceDto> findAllByCompanyName(Pageable pageable, String companyName) {
        Page<WorkplaceAggregate> workplaces = repository.findAllByCompanyName(pageable, companyName);
        return new PageImpl<>(workplaces
                .stream()
                .map(WorkplaceDto::from)
                .collect(Collectors.toList()), pageable, workplaces.getTotalElements());
    }

    public WorkplaceDto findByIdAndCompanyName(long id, String companyName) {
        return repository.findByIdAndCompanyName(id, companyName)
                .map(WorkplaceDto::from)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.WORKPLACE_NOT_FOUND));
    }

    public void deleteByIdAndCompanyName(DeleteWorkplaceCommand command) {
        Optional<WorkplaceAggregate> foundWorkplace = repository.findByIdAndCompanyName(command.id(), command.companyName());
        if (foundWorkplace.isEmpty()) {
            throw new NotFoundException(ErrorStatus.WORKPLACE_NOT_FOUND);
        }
        repository.deleteById(foundWorkplace.get());
    }
}
