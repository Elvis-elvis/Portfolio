package com.ruberwa.myportfolio2.UserSubdomain;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService2 {

    Mono<UserResponseModel2> addUserFromAuth0(String auth0UserId);
    Mono<UserResponseModel2> syncUserWithAuth0(String auth0UserId);
    Flux<UserResponseModel2> getAllUsers();
    Mono<UserResponseModel2> getUserByUserId(String auth0UserId);
    Flux<UserResponseModel2> getStaff();
    Mono<Void> deleteStaff(String userId);
    Mono<UserResponseModel2> updateStaff(Mono<UserRequestModel2> userRequestModel, String userId);
    Mono<UserResponseModel2> addStaffRoleToUser(String auth0UserId);
    Mono<UserResponseModel2> updateUser(Mono<UserRequestModel2> userRequestModel, String userId);
}

