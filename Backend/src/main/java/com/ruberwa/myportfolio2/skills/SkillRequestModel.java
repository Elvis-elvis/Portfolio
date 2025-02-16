package com.ruberwa.myportfolio2.skills;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SkillRequestModel {

    private String skillName;
    private String proficiency;
    private LocalDateTime dateAdded;
    private String skillId;
    private String skillLogo;


}
