package com.ruberwa.myportfolio2.Project;

import com.ruberwa.myportfolio2.skills.Skill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectRequestModel {

    private String projectName;
    private String iconUrl;
    private String gitRepo;
    private List<Skill> skills;
}
