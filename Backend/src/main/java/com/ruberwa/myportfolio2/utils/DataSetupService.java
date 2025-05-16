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
import com.ruberwa.myportfolio2.bio.Bio;
import com.ruberwa.myportfolio2.bio.BioRepository;
import com.ruberwa.myportfolio2.Language.Language;
import com.ruberwa.myportfolio2.Language.LanguageRepository;
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
    private final BioRepository bioRepository;
    private final LanguageRepository languageRepository;

    @Override
    public void run(String... args) throws Exception {
        setupUserEntity();
        setupComments();
        setupProject();
        setupUsers();
        setupSkills();
        setupLanguages();
        setupBio();
    }

    private void setupBio() {
        Bio defaultBio = Bio.builder()
                .content("I’m a third-year Computer Science student at Champlain College, focused on full stack development, IT, and cybersecurity. I’m passionate about working in teams, which I’ve integrated through playing basketball and working in multiple group projects. I’m ambitious about personal growth and have strong integrity, always ensuring my work is done with care and accountability.")
                .build();

        bioRepository.findAll()
                .hasElements()
                .flatMap(exists -> {
                    if (!exists) {
                        return bioRepository.save(defaultBio);
                    }
                    return Mono.empty();
                })
                .subscribe();
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
                new Comment("01", "Paul Jermaine ", "I like the portfolio!", LocalDateTime.now(), true),
                new Comment("02", "Klay Alexander", "Good!", LocalDateTime.now(), true),
                new Comment("03", "A", "A", LocalDateTime.now(), true)
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
                "https://i.postimg.cc/3J0cNFRn/compteexpress.png",
                "https://github.com/Carlos-123321/CompteExpress",
                List.of(
                        Skill.builder().skillId("skillId1").skillName("Java").skillLogo("https://i.postimg.cc/8CHJ8130/java.png").build(),
                        Skill.builder().skillId("skillId2").skillName("Spring Boot").skillLogo("https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/spring/spring-original-wordmark.svg").build(),
                        Skill.builder().skillId("skillId3").skillName("React").skillLogo("https://i.postimg.cc/B67j6mJ5/atom.png").build(),
                        Skill.builder().skillId("skillId4").skillName("TypeScript").skillLogo("https://i.postimg.cc/SKDsTgY0/typescript.png").build(),
                        Skill.builder().skillId("skillId5").skillName("MariaDb").skillLogo("https://i.postimg.cc/5tkFGLVF/mariadb.png").build(),
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
                "https://i.postimg.cc/nzHvbps6/civision.png",
                "https://github.com/Elvis-elvis/interactive-dashboard",
                List.of(
                        Skill.builder().skillId("skillId1").skillName("Typescript").skillLogo("https://i.postimg.cc/SKDsTgY0/typescript.png").build(),
                        Skill.builder().skillId("skillId2").skillName("CSS").skillLogo("https://i.postimg.cc/Jh7bSRHS/css.png").build(),
                        Skill.builder().skillId("skillId3").skillName("Javascript").skillLogo("https://i.postimg.cc/76d0C7ty/js.png").build()
                )
        );

        Flux.just(project1, project2, project3)
                .flatMap(project -> projectRepository.findByProjectId(project.getProjectId())
                        .switchIfEmpty(Mono.defer(() -> projectRepository.save(project))))
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
        Skill python = buildSkill("skillId12", "Python", "https://i.postimg.cc/1XgfNDzk/python.png");

        Flux.just(java, springBoot, react, typescript, mongodb, javascript, mysql, csharp, python)
                .flatMap(skill -> skillRepo.findSkillById(skill.getSkillId())
                        .switchIfEmpty(Mono.defer(() -> skillRepo.save(skill))))
                .subscribe();
    }

    private void setupLanguages() {
        List<Language> languages = List.of(
                new Language(null, "English", "https://upload.wikimedia.org/wikipedia/en/a/a4/Flag_of_the_United_States.svg"),
                new Language(null, "French", "https://upload.wikimedia.org/wikipedia/en/c/c3/Flag_of_France.svg"),
                new Language(null, "Kinyarwanda", "https://upload.wikimedia.org/wikipedia/commons/1/17/Flag_of_Rwanda.svg"),
                new Language(null, "Spanish", "https://upload.wikimedia.org/wikipedia/en/9/9a/Flag_of_Spain.svg")
        );

        Flux.fromIterable(languages)
                .flatMap(lang -> languageRepository.findByName(lang.getName())
                        .switchIfEmpty(languageRepository.save(lang)))
                .subscribe();
    }

    private void setupUsers() {
        User2 user3 = buildUser("elvis", "elvis@gmail.com", "Elvis", "Ruberwa", List.of("Elvis"), null);
        User2 user5 = buildUser("user", "user@gmail.com", "User", "Testing", List.of("User"), List.of("read"));
        Flux.just(user3, user5)
                .flatMap(user -> userRepo.findByUserId(user.getUserId())
                        .switchIfEmpty(Mono.defer(() -> userRepo.save(user))))
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