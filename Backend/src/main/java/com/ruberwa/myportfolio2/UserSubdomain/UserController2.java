package com.ruberwa.myportfolio2.UserSubdomain;


import com.ruberwa.myportfolio2.utils.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController2 {

    private final UserService2 userService2;

    @PostMapping("/{userId}/login")
    public Mono<ResponseEntity<UserResponseModel2>> handleUserLogin(@PathVariable String userId) {
        return userService2.addUserFromAuth0(userId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}/sync")
    public Mono<ResponseEntity<UserResponseModel2>> syncUser(@PathVariable String userId) {
        return userService2.syncUserWithAuth0(userId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Flux<UserResponseModel2> getAllUsers() {
        log.info("Received request to fetch all users.");
        return userService2.getAllUsers()
                .doOnNext(user -> log.info("Fetched user: {}", user))
                .doOnError(e -> log.error("Error fetching all users: {}", e.getMessage()));
    }

    @GetMapping("/{userId}")
    public Mono<UserResponseModel2> getUserByUserId(@PathVariable String userId) {
        return userService2.getUserByUserId(userId);
    }

    @GetMapping("/staff")
    public Flux<UserResponseModel2> getStaff() {
        return userService2.getStaff();
    }

    @DeleteMapping("/staff/{userId}")
    public Mono<ResponseEntity<Void>> deleteStaff(@PathVariable String userId) {
        return userService2.deleteStaff(userId)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                .onErrorResume(NotFoundException.class, e -> Mono.just(new ResponseEntity<Void>(HttpStatus.NOT_FOUND)));
    }

    @PutMapping("/staff/{userId}")
    public Mono<ResponseEntity<UserResponseModel2>> updateStaff(@RequestBody Mono<UserRequestModel2> userRequestModel2, @PathVariable String userId) {
        return userService2.updateStaff(userRequestModel2, userId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/staff/{userId}")
    public Mono<ResponseEntity<UserResponseModel2>> addStaffMember(@PathVariable String userId) {
        log.info("Received request to add user with ID {} as staff", userId);

        return userService2.addStaffRoleToUser(userId)
                .map(ResponseEntity::ok)
                .onErrorResume(NotFoundException.class, e -> {
                    log.error("User not found: {}", e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
                })
                .onErrorResume(IllegalStateException.class, e -> {
                    log.error("Invalid operation: {}", e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).build());
                })
                .doOnError(e -> log.error("Error processing addStaffMember request: {}", e.getMessage()));
    }

    @PutMapping("/{userId}")
    public Mono<ResponseEntity<UserResponseModel2>> UpdateUser(@PathVariable String userId, @RequestBody Mono<UserRequestModel2> userRequestModel2) {
        return userService2.updateUser(userRequestModel2,userId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
