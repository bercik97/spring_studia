package com.jobbed.api.security.dto;

import com.jobbed.api.shared.converter.StringToLowerCaseConverter;

public record AuthenticationRequest(String email, String password) {

    public AuthenticationRequest(String email, String password) {
        this.email = StringToLowerCaseConverter.convert(email);
        this.password = password;
    }
}
