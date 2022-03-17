package com.jobbed.api.security;

import com.jobbed.api.security.model.CustomUserDetails;
import com.jobbed.api.shared.error.ErrorStatus;
import com.jobbed.api.shared.exception.type.ValidationException;
import com.jobbed.api.user.domain.UserAggregate;
import com.jobbed.api.user.domain.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAggregate user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        if (!user.isEnabled()) {
            log.warn("User is not enabled: {}", user);
            throw new ValidationException(ErrorStatus.USER_NOT_ENABLED);
        }
        return new CustomUserDetails(user);
    }
}
