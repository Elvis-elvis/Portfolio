package com.ruberwa.myportfolio2.Language;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LanguageService {
    Flux<Language> getAllLanguages();
    Mono<Language> addLanguage(Language language);
    Mono<Language> updateLanguage(String id, Language language);
    Mono<Void> deleteLanguage(String id);
}