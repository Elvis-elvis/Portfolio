package com.ruberwa.myportfolio2.user;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Repository;


@Repository

public interface UserRepository extends ReactiveMongoRepository<UserEntity, String> {
    Mono<UserEntity> findUserByUserId(String userid);
}
