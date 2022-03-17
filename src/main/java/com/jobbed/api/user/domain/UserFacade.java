package com.jobbed.api.user.domain;

import com.jobbed.api.confirmation_token.domain.vo.ConfirmationTokenUuid;
import com.jobbed.api.user.domain.command.UpdatePasswordCommand;
import com.jobbed.api.user.domain.dto.CreateUserDto;
import com.jobbed.api.user.domain.dto.UpdateUserDto;
import com.jobbed.api.user.domain.dto.UserDetailsDto;
import com.jobbed.api.user.domain.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;

@Transactional
@RequiredArgsConstructor
public class UserFacade {

    private final UserService service;

    public void create(CreateUserDto dto) {
        service.create(dto);
    }

    public boolean confirmAccount(ConfirmationTokenUuid confirmationTokenUuid) {
        return service.confirmAccount(confirmationTokenUuid);
    }

    public void updatePassword(UpdatePasswordCommand command) {
        service.updatePassword(command);
    }

    public Page<UserDto> findAllByCompanyNameExceptMe(Pageable pageable, String companyName, long userId) {
        return service.findAllByCompanyNameExceptMe(pageable, companyName, userId);
    }

    public UserDetailsDto findByIdAndCompanyName(long id, String companyName) {
        return service.findByIdAndCompanyName(id, companyName);
    }

    public UserDetailsDto findByEmailAndCompanyName(String email, String companyName) {
        return service.findByEmailAndCompanyName(email, companyName);
    }

    public void updateByIdAndCompanyName(long id, String companyName, UpdateUserDto dto) {
        service.updateByIdAndCompanyName(id, companyName, dto);
    }

    public void deleteByIdAndCompanyName(long id, String companyName) {
        service.deleteByIdAndCompanyName(id, companyName);
    }
}
