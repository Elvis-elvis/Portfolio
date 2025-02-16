package com.ruberwa.myportfolio2.skills;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "skills")
public class Skill {
    @Id
    private String id;
    private String skillId;
    private String skillName;
    private String iconUrl;
    private String proficiency;
    private String skillLogo;

}
