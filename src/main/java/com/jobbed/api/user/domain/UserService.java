package com.jobbed.api.user.domain;

import com.jobbed.api.confirmation_token.domain.ConfirmationTokenFacade;
import com.jobbed.api.confirmation_token.domain.ConfirmationTokenType;
import com.jobbed.api.confirmation_token.domain.command.CreateConfirmationTokenCommand;
import com.jobbed.api.confirmation_token.domain.vo.ConfirmationTokenUuid;
import com.jobbed.api.confirmation_token.domain.vo.ConfirmationTokenVO;
import com.jobbed.api.notification.domain.NotificationFacade;
import com.jobbed.api.notification.domain.NotificationSubject;
import com.jobbed.api.notification.domain.NotificationType;
import com.jobbed.api.notification.domain.command.SendNotificationCommand;
import com.jobbed.api.shared.error.ErrorStatus;
import com.jobbed.api.shared.exception.type.NotFoundException;
import com.jobbed.api.user.domain.command.UpdatePasswordCommand;
import com.jobbed.api.user.domain.command.UpdateUserCommand;
import com.jobbed.api.user.domain.dto.CreateUserDto;
import com.jobbed.api.user.domain.dto.UpdateUserDto;
import com.jobbed.api.user.domain.dto.UserDetailsDto;
import com.jobbed.api.user.domain.dto.UserDto;
import com.jobbed.api.user.domain.strategy.UserStrategyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class UserService {

    private final UserRepository repository;
    private final UserValidator validator;
    private final UserStrategyService userStrategyService;
    private final ConfirmationTokenFacade confirmationTokenFacade;
    private final NotificationFacade notificationFacade;

    public void create(CreateUserDto dto) {
        validator.validate(dto);
        UserAggregate user = userStrategyService.processCreation(dto);
        UserAggregate createdUser = repository.save(user);
        ConfirmationTokenUuid confirmationTokenUuid = confirmationTokenFacade.create(new CreateConfirmationTokenCommand(createdUser.getId(), ConfirmationTokenType.ACCOUNT_CONFIRMATION));
        notificationFacade.send(new SendNotificationCommand(dto.email(), confirmationTokenUuid, NotificationSubject.ACCOUNT_VERIFICATION, NotificationType.EMAIL));
    }

    public boolean confirmAccount(ConfirmationTokenUuid confirmationTokenUuid) {
        ConfirmationTokenVO confirmationTokenVO = confirmationTokenFacade.confirm(confirmationTokenUuid);
        if (confirmationTokenVO == null) {
            return false;
        }
        repository.enableById(confirmationTokenVO.userId());
        return true;
    }

    public void updatePassword(UpdatePasswordCommand command) {
        validator.validate(command);
        repository.setPasswordByEmail(command.newPassword(), command.email());
    }

    public Page<UserDto> findAllByCompanyNameExceptMe(Pageable pageable, String companyName, long userId) {
        Page<UserAggregate> users = repository.findAllByCompanyNameExceptMe(pageable, companyName, userId);
        return new PageImpl<>(users
                .stream()
                .map(UserDto::from)
                .collect(Collectors.toList()), pageable, users.getTotalElements());
    }

    public UserDetailsDto findByIdAndCompanyName(long id, String companyName) {
        return repository.findByIdAndCompanyName(id, companyName)
                .map(UserDetailsDto::from)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.USER_NOT_FOUND));
    }

    public UserDetailsDto findByEmailAndCompanyName(String email, String companyName) {
        return repository.findByEmailAndCompanyName(email, companyName)
                .map(UserDetailsDto::from)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.USER_NOT_FOUND));
    }

    public void updateByIdAndCompanyName(long userId, String companyName, UpdateUserDto dto) {
        UserAggregate user = repository.findByIdAndCompanyName(userId, companyName).orElseThrow(() -> new NotFoundException(ErrorStatus.USER_NOT_FOUND));
        validator.validate(dto);
        UserAggregate updatedUser = userStrategyService.processUpdate(UpdateUserCommand.of(userId, companyName, user, dto), user.getRoles());
        repository.update(updatedUser);
    }

    public void deleteByIdAndCompanyName(long id, String companyName) {
        Optional<UserAggregate> foundUser = repository.findByIdAndCompanyName(id, companyName);
        if (foundUser.isEmpty()) {
            throw new NotFoundException(ErrorStatus.USER_NOT_FOUND);
        }
        repository.deleteById(foundUser.get());
    }
}
