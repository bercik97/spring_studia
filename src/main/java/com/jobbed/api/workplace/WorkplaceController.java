package com.jobbed.api.workplace;

import com.jobbed.api.security.model.CustomUserDetails;
import com.jobbed.api.shared.pageable.PageResponse;
import com.jobbed.api.shared.pageable.PageUtil;
import com.jobbed.api.user.domain.UserAggregate;
import com.jobbed.api.workplace.domain.WorkplaceFacade;
import com.jobbed.api.workplace.domain.dto.WorkplaceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workplaces")
@RequiredArgsConstructor
class WorkplaceController {

    private final WorkplaceFacade facade;

    @GetMapping
    public PageResponse<WorkplaceDto> findAll(Authentication authentication, Pageable pageable) {
        UserAggregate user = ((CustomUserDetails) authentication.getPrincipal()).getAggregate();
        return PageUtil.toPageResponse(facade.findAllByCompanyName(pageable, user.getCompanyName()));
    }

    @GetMapping("{id}")
    public WorkplaceDto findByIdAndCompanyName(Authentication authentication, @PathVariable long id) {
        UserAggregate user = ((CustomUserDetails) authentication.getPrincipal()).getAggregate();
        return facade.findByIdAndCompanyName(id, user.getCompanyName());
    }
}
