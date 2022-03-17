package com.jobbed.api.workplace;

import com.jobbed.api.security.model.CustomUserDetails;
import com.jobbed.api.shared.vo.IdVO;
import com.jobbed.api.user.domain.UserAggregate;
import com.jobbed.api.workplace.domain.WorkplaceFacade;
import com.jobbed.api.workplace.domain.command.CreateWorkplaceCommand;
import com.jobbed.api.workplace.domain.command.DeleteWorkplaceCommand;
import com.jobbed.api.workplace.domain.command.UpdateWorkplaceCommand;
import com.jobbed.api.workplace.domain.dto.CreateWorkplaceDto;
import com.jobbed.api.workplace.domain.dto.UpdateWorkplaceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/director/workplaces")
@RequiredArgsConstructor
class WorkplaceDirectorController {

    private final WorkplaceFacade facade;

    @PostMapping
    public IdVO<Long> create(Authentication authentication, @RequestBody CreateWorkplaceDto dto) {
        UserAggregate user = ((CustomUserDetails) authentication.getPrincipal()).getAggregate();
        return facade.create(CreateWorkplaceCommand.of(dto, user.getCompanyName()));
    }

    @PutMapping("{id}")
    public void updateByIdAndCompanyName(Authentication authentication, @PathVariable long id, @RequestBody UpdateWorkplaceDto dto) {
        UserAggregate user = ((CustomUserDetails) authentication.getPrincipal()).getAggregate();
        facade.updateByIdAndCompanyName(UpdateWorkplaceCommand.of(id, dto, user.getCompanyName()));
    }

    @DeleteMapping("{id}")
    public void deleteByIdAndCompanyName(Authentication authentication, @PathVariable long id) {
        UserAggregate user = ((CustomUserDetails) authentication.getPrincipal()).getAggregate();
        facade.deleteByIdAndCompanyName(new DeleteWorkplaceCommand(id, user.getCompanyName()));
    }
}
