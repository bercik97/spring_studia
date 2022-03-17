package com.jobbed.api.user.domain.strategy;

import com.jobbed.api.role.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserStrategyFactory {

    private final List<UserStrategy> userStrategies;

    public UserStrategy getStrategyFor(RoleType roleType) {
        return userStrategies.stream()
                .filter(strategy -> strategy.isApplicableFor(roleType))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid roleType for user strategy"));
    }
}
