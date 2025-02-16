package com.ruberwa.myportfolio2.comment;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CommentService {
    Flux<Comment> getAllComments();
    Mono<CommentResponseModel> addComment(Mono<CommentRequestModel> commentRequestModel);

    Flux<CommentResponseModel> getApprovedComments();
    Flux<CommentResponseModel> getUnapprovedComments();
    Mono<CommentResponseModel> approveComment(String commentId);

    Mono<Void> deleteReview(String reviewId);


}