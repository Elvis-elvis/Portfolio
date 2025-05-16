package com.ruberwa.myportfolio2.Language;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface LanguageRepository extends ReactiveMongoRepository<Language, String> {
    Mono<Language> findByName(String name);
}