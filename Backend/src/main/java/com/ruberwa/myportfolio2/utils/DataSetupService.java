package com.ruberwa.myportfolio2.utils;

import com.ruberwa.myportfolio2.Project.Project;
import com.ruberwa.myportfolio2.Project.ProjectRepository;
import com.ruberwa.myportfolio2.UserSubdomain.User2;
import com.ruberwa.myportfolio2.comment.Comment;
import com.ruberwa.myportfolio2.comment.CommentRepository;
import com.ruberwa.myportfolio2.skills.Skill;
import com.ruberwa.myportfolio2.skills.SkillRepository;
import com.ruberwa.myportfolio2.user.UserEntity;
import com.ruberwa.myportfolio2.user.UserRepository;
import com.ruberwa.myportfolio2.UserSubdomain.UserRepository2;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataSetupService implements CommandLineRunner {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository2 userRepo;
    private final SkillRepository skillRepo;





    @Override
    public void run(String... args) throws Exception{
        setupUserEntity();
        setupComments();
        setupProject();
        setupUsers();
        setupSkills();
    }

    private UserEntity buildUserEntity(String userId, String firstname, String lastname, int age, String origins) {
        UserEntity me = new UserEntity();
        me.setUserId(userId);
        me.setFirstname(firstname);
        me.setLastname(lastname);
        me.setAge(age);
        me.setOrigins(origins);
        return me;
    }


    private void setupUserEntity() {
        List<UserEntity> me = List.of(
                buildUserEntity("002", "Elvis", "Ruberwa", 19, "Rwandan")
        );

        Flux.fromIterable(me)
                .flatMap(me1 -> userRepository.findUserByUserId(me1.getUserId())
                        .switchIfEmpty(userRepository.insert(me1)))
                .subscribe();
    }
    private void setupComments() {
        List<Comment> comments = List.of(
                new Comment( "01", "1",  "Paul Jermaine ", "I like the portfolio!", LocalDateTime.now(), true),
                new Comment( "02", "2", "Klay Alexander", "Good!", LocalDateTime.now(), true)
        );

        Flux.fromIterable(comments)
                .flatMap(commentRepository::save)
                .subscribe();
    }


    private Project buildProject(String projectId, String projectName, String iconUrl, String gitRepo, List<Skill> skills) {
        return Project.builder()
                .projectId(projectId)
                .projectName(projectName)
                .iconUrl(iconUrl)
                .gitRepo(gitRepo)
                .skills(skills)
                .build();
    }

    private Skill buildSkill(String skillId, String skillName, String skillLogo) {
        return Skill.builder()
                .skillId(skillId)
                .skillName(skillName)
                .skillLogo(skillLogo)
                .build();
    }

    private void setupProject() {

        Project project1 = buildProject(
                "projectId1",
                "Compte Express",
                "https://i.postimg.cc/jdW1329Z/noodle-Star.png",
                "https://github.com/Carlos-123321/CompteExpress",
                List.of(
                        Skill.builder().skillId("skillId1").skillName("Java").skillLogo("https://i.postimg.cc/8CHJ8130/java.png").build(),
                        Skill.builder().skillId("skillId2").skillName("Spring Boot").skillLogo("https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/spring/spring-original-wordmark.svg").build(),
                        Skill.builder().skillId("skillId3").skillName("React").skillLogo("https://i.postimg.cc/B67j6mJ5/atom.png").build(),
                        Skill.builder().skillId("skillId4").skillName("TypeScript").skillLogo("https://i.postimg.cc/SKDsTgY0/typescript.png").build(),
                        Skill.builder().skillId("skillId5").skillName("MongoDb").skillLogo("https://i.postimg.cc/HWKJ1vZd/database.png").build(),
                        Skill.builder().skillId("skillId6").skillName("Git").skillLogo("https://i.postimg.cc/B6n84rfQ/git.png").build()
                )
        );

        Project project2 = buildProject(
                "projectId2",
                "Champlain PetClinic",
                "https://i.postimg.cc/nzRd0fGy/champlain-Pet-Clinic.png",
                "https://github.com/cgerard321/champlain_petclinic",
                List.of(
                        Skill.builder().skillId("skillId1").skillName("Java").skillLogo("https://i.postimg.cc/8CHJ8130/java.png").build(),
                        Skill.builder().skillId("skillId2").skillName("Spring Boot").skillLogo("https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/spring/spring-original-wordmark.svg").build(),
                        Skill.builder().skillId("skillId3").skillName("React").skillLogo("https://i.postimg.cc/B67j6mJ5/atom.png").build(),
                        Skill.builder().skillId("skillId4").skillName("TypeScript").skillLogo("https://i.postimg.cc/SKDsTgY0/typescript.png").build(),
                        Skill.builder().skillId("skillId5").skillName("MongoDb").skillLogo("https://i.postimg.cc/HWKJ1vZd/database.png").build(),
                        Skill.builder().skillId("skillId6").skillName("Git").skillLogo("https://i.postimg.cc/B6n84rfQ/git.png").build()
                )
        );

        Project project3 = buildProject(
                "projectId3",
                "Interactive Dashboard for Civision Company",
                "https://i.postimg.cc/gJTD3qYd/pokemon.png",
                "https://github.com/Elvis-elvis/interactive-dashboard",
                List.of(
                        Skill.builder().skillId("skillId1").skillName("Java").skillLogo("https://i.postimg.cc/8CHJ8130/java.png").build(),
                        Skill.builder().skillId("skillId2").skillName("Spring Boot").skillLogo("https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/spring/spring-original-wordmark.svg").build(),
                        Skill.builder().skillId("skillId3").skillName("React").skillLogo("https://i.postimg.cc/B67j6mJ5/atom.png").build(),
                        Skill.builder().skillId("skillId4").skillName("Javascript").skillLogo("https://i.postimg.cc/4xvntfJx/js.png").build(),
                        Skill.builder().skillId("skillId5").skillName("MySQL").skillLogo("https://i.postimg.cc/qvNPBnff/mysql.png").build()
                )
        );

        Flux.just(project1, project2, project3)
                .flatMap(project -> projectRepository.findByProjectId(project.getProjectId())
                        .switchIfEmpty(Mono.defer(() -> {
                            System.out.println("Inserting project: " + project.getProjectId());
                            return projectRepository.save(project);
                        }))
                )
                .subscribe();

    }

    private void setupSkills() {
        Skill java = buildSkill("skillId1", "Java", "https://i.postimg.cc/8CHJ8130/java.png");
        Skill springBoot = buildSkill("skillId2", "Spring Boot", "https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/spring/spring-original-wordmark.svg");
        Skill react = buildSkill("skillId3", "React", "https://i.postimg.cc/B67j6mJ5/atom.png");
        Skill typescript = buildSkill("skillId4", "TypeScript", "https://i.postimg.cc/SKDsTgY0/typescript.png");
        Skill mongodb = buildSkill("skillId5", "MongoDb", "https://i.postimg.cc/HWKJ1vZd/database.png");
        Skill javascript = buildSkill("skillId6", "JavaScript", "https://i.postimg.cc/4xvntfJx/js.png");
        Skill mysql = buildSkill("skillId7", "Mysql", "https://i.postimg.cc/qvNPBnff/mysql.png");
        Skill csharp = buildSkill("skillId8", "C#", "https://i.postimg.cc/d3zDRDkL/c-sharp.png");
        Skill python= buildSkill("skillId12", "Python", "https://i.postimg.cc/1XgfNDzk/python.png");

        Flux.just(java, springBoot, react, typescript, mongodb, javascript, mysql, csharp, python)
                .flatMap(skill -> skillRepo.findSkillById(skill.getSkillId())
                        .switchIfEmpty(Mono.defer(() -> {
                            System.out.println("Inserting skill: " + skill.getSkillId());
                            return skillRepo.save(skill);
                        }))
                )
                .subscribe();
    }

    private void setupUsers() {
        User2 user3 = buildUser("elvis", "jshn@hotmail.com", "Elvis", "Ruberwa", List.of("Elvis"), null);
        User2 user5 = buildUser("user", "samantha@example.com", "Samantha", "Lee", List.of("User"), List.of("read"));
        Flux.just( user3, user5)
                .flatMap(user -> {
                    System.out.println("Checking if user exists: " + user.getUserId());

                    // Check if the user already exists by userId (or email)
                    return userRepo.findByUserId(user.getUserId()) // Assuming userId is the unique identifier
                            .doOnTerminate(() -> System.out.println("Terminated: " + user.getUserId()))
                            .switchIfEmpty(Mono.defer(() -> {
                                System.out.println("Inserting user: " + user.getUserId());
                                return userRepo.save(user); // Save if user doesn't exist
                            }));
                })
                .subscribe();
    }


    private User2 buildUser(String userId, String email, String firstName, String lastName, List<String> roles, List<String> permissions) {
        return User2.builder()
                .userId(userId)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .roles(roles)
                .permissions(permissions)
                .build();
    }

}