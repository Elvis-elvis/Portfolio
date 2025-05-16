package com.ruberwa.myportfolio2.comment;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {
    // You can remove findByCommentId — use findById only
}
