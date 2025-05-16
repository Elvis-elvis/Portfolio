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
                .map(request -> Comment.builder()
                        .author(request.getAuthor())
                        .content(request.getContent())
                        .dateSubmitted(LocalDateTime.now())
                        .approved(false)
                        .build()
                )
                .flatMap(commentRepository::insert)
                .map(EntityDTOUtil::toCommentResponseModel);
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
    public Mono<CommentResponseModel> approveComment(String id) {
        return commentRepository.findById(id)
                .flatMap(comment -> {
                    comment.setApproved(true);
                    return commentRepository.save(comment);
                })
                .map(EntityDTOUtil::toCommentResponseModel);
    }

    @Override
    public Mono<Void> deleteReview(String id) {
        return commentRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Comment with ID '" + id + "' not found.")))
                .flatMap(commentRepository::delete);
    }


}
