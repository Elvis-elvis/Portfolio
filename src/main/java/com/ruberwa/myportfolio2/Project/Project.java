package com.ruberwa.myportfolio2.Project;

import com.ruberwa.myportfolio2.skills.Skill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "projects")
public class Project {
    @Id
    private String id;
    private String projectId;
    private String projectName;
    private String iconUrl;
    private String gitRepo;
    private List<Skill> skills;
}
