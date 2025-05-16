package com.ruberwa.myportfolio2.bio;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("bio")
public class Bio {
    @Id
    private String id;
    private String content;
}
