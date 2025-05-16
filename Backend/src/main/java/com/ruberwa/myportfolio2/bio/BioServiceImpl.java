package com.ruberwa.myportfolio2.bio;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BioServiceImpl implements BioService {

    private final BioRepository bioRepository;

    public BioServiceImpl(BioRepository bioRepository) {
        this.bioRepository = bioRepository;
    }

    @Override
    public Mono<Bio> getBio() {
        return bioRepository.findAll().next(); // Assuming only one bio
    }

    @Override
    public Mono<Bio> updateBio(String content) {
        return getBio()
                .flatMap(existing -> {
                    existing.setContent(content);
                    return bioRepository.save(existing);
                });
    }
}
