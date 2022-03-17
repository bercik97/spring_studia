package com.jobbed.api.comment;

import com.jobbed.api.comment.domain.CommentFacade;
import com.jobbed.api.comment.domain.CommentType;
import com.jobbed.api.comment.domain.command.CreateCommentCommand;
import com.jobbed.api.comment.domain.command.FindCommentCommand;
import com.jobbed.api.comment.domain.dto.CommentDto;
import com.jobbed.api.comment.domain.dto.CreateCommentDto;
import com.jobbed.api.security.model.CustomUserDetails;
import com.jobbed.api.shared.pageable.PageResponse;
import com.jobbed.api.shared.pageable.PageUtil;
import com.jobbed.api.user.domain.UserAggregate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
class CommentController {

    private final CommentFacade facade;

    @PostMapping
    public void create(Authentication authentication, @RequestBody CreateCommentDto dto) {
        UserAggregate user = ((CustomUserDetails) authentication.getPrincipal()).getAggregate();
        facade.create(CreateCommentCommand.of(dto, user.getEmail(), user.getCompanyName()));
    }

    @GetMapping("{relationId}")
    public PageResponse<CommentDto> findAllByRelationIdAndTypeAndDateAndCompanyName(Authentication authentication, Pageable pageable,
                                                                                    @PathVariable("relationId") long relationId,
                                                                                    @RequestParam("type") CommentType type,
                                                                                    @RequestParam("date") LocalDate date) {
        UserAggregate user = ((CustomUserDetails) authentication.getPrincipal()).getAggregate();
        FindCommentCommand command = new FindCommentCommand(relationId, type, date, user.getCompanyName());
        return PageUtil.toPageResponse(facade.findAllByRelationIdAndTypeAndDateAndCompanyName(pageable, command));
    }
}
