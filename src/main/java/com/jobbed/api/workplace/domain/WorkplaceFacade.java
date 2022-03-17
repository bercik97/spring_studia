package com.jobbed.api.workplace.domain;

import com.jobbed.api.shared.vo.IdVO;
import com.jobbed.api.workplace.domain.command.CreateWorkplaceCommand;
import com.jobbed.api.workplace.domain.command.DeleteWorkplaceCommand;
import com.jobbed.api.workplace.domain.command.UpdateWorkplaceCommand;
import com.jobbed.api.workplace.domain.dto.WorkplaceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;

@Transactional
@RequiredArgsConstructor
public class WorkplaceFacade {

    private final WorkplaceService service;

    public IdVO<Long> create(CreateWorkplaceCommand command) {
        return service.create(command);
    }

    public void updateByIdAndCompanyName(UpdateWorkplaceCommand command) {
        service.updateByIdAndCompanyName(command);
    }

    public Page<WorkplaceDto> findAllByCompanyName(Pageable pageable, String companyName) {
        return service.findAllByCompanyName(pageable, companyName);
    }

    public WorkplaceDto findByIdAndCompanyName(long id, String companyName) {
        return service.findByIdAndCompanyName(id, companyName);
    }

    public void deleteByIdAndCompanyName(DeleteWorkplaceCommand command) {
        service.deleteByIdAndCompanyName(command);
    }
}
