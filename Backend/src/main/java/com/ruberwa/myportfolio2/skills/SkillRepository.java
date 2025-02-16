package com.ruberwa.myportfolio2.skills;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface SkillRepository extends ReactiveMongoRepository<Skill, String> {
    Mono<Skill> findSkillById(String id);

}
