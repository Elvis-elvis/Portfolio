package com.ruberwa.myportfolio2.bio;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/bio")
@CrossOrigin(origins = "*")
public class BioController {

    private final BioService bioService;

    public BioController(BioService bioService) {
        this.bioService = bioService;
    }

    @GetMapping
    public Mono<Bio> getBio() {
        return bioService.getBio();
    }

    @PutMapping
    public Mono<Bio> updateBio(@RequestBody Bio bio) {
        return bioService.updateBio(bio.getContent());
    }
}
