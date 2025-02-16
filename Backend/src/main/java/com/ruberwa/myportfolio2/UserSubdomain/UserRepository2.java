package com.ruberwa.myportfolio2.UserSubdomain;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository2 extends ReactiveMongoRepository<User2, String> {

    Mono<User2> findByUserId(String userId);
}
