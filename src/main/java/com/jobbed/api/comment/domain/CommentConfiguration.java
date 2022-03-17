package com.jobbed.api.comment.domain;

import com.jobbed.api.comment.domain.strategy.CommentStrategy;
import com.jobbed.api.comment.domain.strategy.CommentStrategyFactory;
import com.jobbed.api.comment.domain.strategy.CommentStrategyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
class CommentConfiguration {

    @Bean
    public CommentFacade commentFacade(CommentRepository repository,
                                       List<CommentStrategy> commentStrategies) {
        CommentValidator validator = new CommentValidator();
        CommentStrategyFactory commentStrategyFactory = new CommentStrategyFactory(commentStrategies);
        CommentStrategyService commentStrategyService = new CommentStrategyService(commentStrategyFactory);
        CommentService service = new CommentService(repository, validator, commentStrategyService);
        return new CommentFacade(service);
    }

    public CommentFacade commentFacade(ConcurrentHashMap<Long, CommentEntity> db,
                                       List<CommentStrategy> commentStrategies) {
        CommentInMemoryRepository repository = new CommentInMemoryRepository(db);
        CommentValidator validator = new CommentValidator();
        CommentStrategyFactory commentStrategyFactory = new CommentStrategyFactory(commentStrategies);
        CommentStrategyService commentStrategyService = new CommentStrategyService(commentStrategyFactory);
        CommentService service = new CommentService(repository, validator, commentStrategyService);
        return new CommentFacade(service);
    }
}
