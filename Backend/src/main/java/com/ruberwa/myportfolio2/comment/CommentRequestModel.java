package com.ruberwa.myportfolio2.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequestModel {
    private String author;
    private String content;
    private LocalDateTime dateSubmitted;
    private boolean approved;
}
