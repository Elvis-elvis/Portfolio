package com.ruberwa.myportfolio2.bio;

import reactor.core.publisher.Mono;

public interface BioService {
    Mono<Bio> getBio();
    Mono<Bio> updateBio(String content);
}
