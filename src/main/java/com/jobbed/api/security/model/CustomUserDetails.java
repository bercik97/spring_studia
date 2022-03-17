package com.jobbed.api.security.model;

import com.jobbed.api.user.domain.UserAggregate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final UserAggregate aggregate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return aggregate.getRoles()
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRole()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return aggregate.getPassword();
    }

    @Override
    public String getUsername() {
        return aggregate.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return aggregate.isEnabled();
    }
}
