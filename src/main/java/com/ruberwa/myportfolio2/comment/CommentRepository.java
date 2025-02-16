package com.ruberwa.myportfolio2.comment;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {
    Mono<Comment> findByCommentId(String commentId);

}
