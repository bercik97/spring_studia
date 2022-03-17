package com.jobbed.api.security;

import com.jobbed.api.security.dto.AuthenticationRequest;
import com.jobbed.api.security.dto.AuthenticationResponse;
import com.jobbed.api.shared.error.ErrorStatus;
import com.jobbed.api.shared.exception.type.ValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtService jwtService;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        final var token = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        try {
            authenticationManager.authenticate(token);
        } catch (BadCredentialsException e) {
            log.warn("BadCredentialsException is thrown!");
            throw new ValidationException(ErrorStatus.USER_BAD_CREDENTIALS);
        }
        final var userDetails = userDetailsService.loadUserByUsername(request.email());
        final var jwt = jwtService.generateToken(userDetails);
        return new AuthenticationResponse(jwt);
    }
}
