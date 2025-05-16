package com.ruberwa.myportfolio2.Language;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {
    private final LanguageRepository repo;

    public Flux<Language> getAllLanguages() {
        return repo.findAll();
    }

    public Mono<Language> addLanguage(Language lang) {
        return repo.save(lang);
    }

    public Mono<Language> updateLanguage(String id, Language lang) {
        return repo.findById(id)
                .flatMap(existing -> {
                    existing.setName(lang.getName());
                    existing.setFlagUrl(lang.getFlagUrl());
                    return repo.save(existing);
                });
    }

    public Mono<Void> deleteLanguage(String id) {
        return repo.deleteById(id);
    }
}
