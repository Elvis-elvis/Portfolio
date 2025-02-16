package com.ruberwa.myportfolio2.skills;

import com.ruberwa.myportfolio2.utils.EntityDTOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@Slf4j
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public Flux<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    @Override
    public Mono<SkillResponseModel> addSkill(Mono<SkillRequestModel> skillRequestModel) {
        return skillRequestModel
                .map(request -> {
                    request.setDateAdded(LocalDateTime.now()); // Set the date the skill was added
                    return EntityDTOUtil.toSkillEntity(request); // Convert request to entity
                })
                .flatMap(skillRepository::insert) // Save the skill in the repository
                .flatMap(savedSkill -> skillRepository.findById(savedSkill.getId())) // Fetch the saved skill
                .map(EntityDTOUtil::toSkillResponseModel); // Map the entity to a response model
    }
}
