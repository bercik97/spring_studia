package com.jobbed.api.user.domain;

import com.jobbed.api.confirmation_token.domain.ConfirmationTokenFacade;
import com.jobbed.api.notification.domain.NotificationFacade;
import com.jobbed.api.user.domain.strategy.UserStrategy;
import com.jobbed.api.user.domain.strategy.UserStrategyFactory;
import com.jobbed.api.user.domain.strategy.UserStrategyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
class UserConfiguration {

    @Bean
    public UserFacade userFacade(UserRepository repository,
                                 List<UserStrategy> userStrategies,
                                 ConfirmationTokenFacade confirmationTokenFacade,
                                 NotificationFacade notificationFacade,
                                 PasswordEncoder passwordEncoder) {
        UserValidator validator = new UserValidator(repository, passwordEncoder);
        UserStrategyFactory userStrategyFactory = new UserStrategyFactory(userStrategies);
        UserStrategyService userStrategyService = new UserStrategyService(userStrategyFactory);
        UserService service = new UserService(repository, validator, userStrategyService, confirmationTokenFacade, notificationFacade);
        return new UserFacade(service);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public UserFacade userFacade(ConcurrentHashMap<Long, UserEntity> db,
                                 List<UserStrategy> userStrategies,
                                 ConfirmationTokenFacade confirmationTokenFacade,
                                 NotificationFacade notificationFacade,
                                 PasswordEncoder passwordEncoder) {
        UserInMemoryRepository repository = new UserInMemoryRepository(db);
        UserValidator validator = new UserValidator(repository, passwordEncoder);
        UserStrategyFactory userStrategyFactory = new UserStrategyFactory(userStrategies);
        UserStrategyService userStrategyService = new UserStrategyService(userStrategyFactory);
        UserService service = new UserService(repository, validator, userStrategyService, confirmationTokenFacade, notificationFacade);
        return new UserFacade(service);
    }
}
