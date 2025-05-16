package com.ruberwa.myportfolio2.Language;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/languages")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class LanguageController {

    private final LanguageService service;

    @GetMapping
    public Flux<Language> getAll() {
        return service.getAllLanguages();
    }

    @PostMapping
    public Mono<Language> add(@RequestBody Language lang) {
        return service.addLanguage(lang);
    }

    @PutMapping("/{id}")
    public Mono<Language> update(@PathVariable String id, @RequestBody Language lang) {
        return service.updateLanguage(id, lang);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return service.deleteLanguage(id);
    }
}
