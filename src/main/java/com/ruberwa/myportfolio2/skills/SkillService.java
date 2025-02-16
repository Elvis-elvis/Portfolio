package com.ruberwa.myportfolio2.skills;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.ruberwa.myportfolio2.skills.Skill;
import com.ruberwa.myportfolio2.skills.SkillRequestModel;
import com.ruberwa.myportfolio2.skills.SkillResponseModel;




public interface SkillService {
    Flux<Skill> getAllSkills();
    Mono<SkillResponseModel> addSkill(Mono<SkillRequestModel> skillRequestModel);
}