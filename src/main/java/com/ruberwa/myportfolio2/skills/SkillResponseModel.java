package com.ruberwa.myportfolio2.skills;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SkillResponseModel {
    private String id;
    private String skillId;

    private String skillName;
    private String proficiency;
    private String dateAdded;
    private String skillLogo;

}
