package com.ruberwa.myportfolio2.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "comments")
public class Comment {
    @Id
    private String id;
    private String commentId;
    private String author;
    private String content;
    private LocalDateTime dateSubmitted;
    private boolean approved;

}
