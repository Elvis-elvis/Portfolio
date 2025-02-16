package com.ruberwa.myportfolio2.auth0;


import com.ruberwa.myportfolio2.UserSubdomain.UserResponseModel2;

import reactor.core.publisher.Mono;

public interface Auth0Service {

    Mono<UserResponseModel2> getUserById(String auth0UserId);
    Mono<Void> assignRoleToUser(String auth0UserId, String roleName);
}
