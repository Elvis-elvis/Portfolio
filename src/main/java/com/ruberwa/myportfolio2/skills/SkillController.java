package com.ruberwa.myportfolio2.skills;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.ruberwa.myportfolio2.skills.SkillService;
import com.ruberwa.myportfolio2.skills.Skill;




@RestController
@RequestMapping("api/skills")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"}, allowedHeaders = "content-Type", allowCredentials = "true")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping("")
    public Flux<Skill> getAllSkills() {
        return skillService.getAllSkills();
    }

    @PostMapping("")
    public Mono<SkillResponseModel> addSkill(@RequestBody SkillRequestModel skillRequestModel) {
        return skillService.addSkill(Mono.just(skillRequestModel));
    }
}
