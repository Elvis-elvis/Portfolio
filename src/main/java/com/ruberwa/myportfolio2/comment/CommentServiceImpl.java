package com.ruberwa.myportfolio2.comment;

import com.ruberwa.myportfolio2.utils.EntityDTOUtil;
import com.ruberwa.myportfolio2.utils.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.ruberwa.myportfolio2.comment.CommentRepository;
import com.ruberwa.myportfolio2.comment.CommentRequestModel;
import com.ruberwa.myportfolio2.comment.CommentResponseModel;
import org.springframework.stereotype.Service;





import java.time.LocalDateTime;


@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Flux<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public Mono<CommentResponseModel> addComment(Mono<CommentRequestModel> commentRequestModel) {
        return commentRequestModel
                .map(request -> {
                    request.setDateSubmitted(LocalDateTime.now()); // Set the submission date
                    return EntityDTOUtil.toCommentEntity(request); // Convert request to entity
                })
                .flatMap(commentRepository::insert) // Save the comment in the repository
                .flatMap(savedComment -> commentRepository.findById(savedComment.getId())) // Fetch the saved comment
                .map(EntityDTOUtil::toCommentResponseModel); // Map the entity to a response model
    }

    @Override
    public Flux<CommentResponseModel> getApprovedComments() {
        return commentRepository.findAll()
                .filter(Comment::isApproved)
                .map(EntityDTOUtil::toCommentResponseModel);
    }

    @Override
    public Flux<CommentResponseModel> getUnapprovedComments() {
        return commentRepository.findAll()
                .filter(comment -> !comment.isApproved())
                .map(EntityDTOUtil::toCommentResponseModel);
    }


    @Override
    public Mono<CommentResponseModel> approveComment(String commentId) {
        return commentRepository.findByCommentId(commentId)
                .flatMap(comment -> {
                    comment.setApproved(true);
                    return commentRepository.save(comment);
                })
                .map(EntityDTOUtil::toCommentResponseModel);
    }

    @Override
    public Mono<Void> deleteReview(String reviewId) {
        return commentRepository.findByCommentId(reviewId)
                .switchIfEmpty(Mono.error(new NotFoundException("Re with ID '" + reviewId + "' not found.")))
                .flatMap(commentRepository::delete);
    }

}
