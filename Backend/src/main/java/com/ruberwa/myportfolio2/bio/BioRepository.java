package com.ruberwa.myportfolio2.bio;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BioRepository extends ReactiveMongoRepository<Bio, String> {
}
