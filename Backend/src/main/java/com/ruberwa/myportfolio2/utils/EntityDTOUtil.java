package com.ruberwa.myportfolio2.utils;

import com.ruberwa.myportfolio2.Project.Project;
import com.ruberwa.myportfolio2.Project.ProjectRequestModel;
import com.ruberwa.myportfolio2.Project.ProjectResponseModel;
import com.ruberwa.myportfolio2.UserSubdomain.User2;
import com.ruberwa.myportfolio2.UserSubdomain.UserResponseModel2;
import com.ruberwa.myportfolio2.comment.Comment;
import com.ruberwa.myportfolio2.comment.CommentRequestModel;
import com.ruberwa.myportfolio2.comment.CommentResponseModel;
import com.ruberwa.myportfolio2.skills.Skill;
import com.ruberwa.myportfolio2.skills.SkillRequestModel;
import com.ruberwa.myportfolio2.skills.SkillResponseModel;
import com.ruberwa.myportfolio2.user.UserEntity;
import com.ruberwa.myportfolio2.user.UserResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EntityDTOUtil {

    public static UserResponseModel toUserResponseModel(UserEntity user) {
        return UserResponseModel.builder()
                .userId(user.getUserId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .age(user.getAge())
                .origins(user.getOrigins())
                .build();
    }

    public static Comment toCommentEntity(CommentRequestModel request) {
        return Comment.builder()
                .author(request.getAuthor())
                .content(request.getContent())
                .dateSubmitted(request.getDateSubmitted())
                .build();
    }

    public static CommentResponseModel toCommentResponseModel(Comment comment) {
        return CommentResponseModel.builder()
                .id(comment.getId())
                .author(comment.getAuthor())
                .content(comment.getContent())
                .dateSubmitted(comment.getDateSubmitted())
                .approved(comment.isApproved())
                .build();
    }


    public static Skill toSkillEntity(SkillRequestModel request) {
        return Skill.builder()
                .skillName(request.getSkillName())  // Fixed field reference
                .proficiency(request.getProficiency())  // Ensure Skill has this field
                .build();
    }

    public static SkillResponseModel toSkillResponseModel(Skill skill) {
        return SkillResponseModel.builder()
                .skillId(skill.getSkillId())
                .skillName(skill.getSkillName())
                .proficiency(skill.getProficiency())  // Ensure Skill has this field
                .build();
    }

    public static ProjectResponseModel toProjectResponseModel(Project project) {
        ProjectResponseModel projectResponseModel  = new ProjectResponseModel();
        BeanUtils.copyProperties(project, projectResponseModel);
        return projectResponseModel;
    }

    public static Project toProjectEntity(ProjectRequestModel request) {
        return Project.builder()
                .projectId(generateOrderIdString())
                .projectName(request.getProjectName())
                .iconUrl(request.getIconUrl())
                .gitRepo(request.getGitRepo())
                .skills(request.getSkills())
                .build();
    }

    public static UserResponseModel2 toUserResponseModel2(User2 user) {
        UserResponseModel2 model = new UserResponseModel2();
        model.setUserId(user.getUserId());
        model.setEmail(user.getEmail());
        model.setFirstName(user.getFirstName());
        model.setLastName(user.getLastName());
        model.setRoles(user.getRoles());
        model.setPermissions(user.getPermissions());
        return model;
    }

    public static String generateOrderIdString() {
        return UUID.randomUUID().toString();
    }

}
