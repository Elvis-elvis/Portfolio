package com.ruberwa.myportfolio2.user;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Flux<UserResponseModel> getAll();
}