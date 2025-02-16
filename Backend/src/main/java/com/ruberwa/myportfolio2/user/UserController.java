package com.ruberwa.myportfolio2.user;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("api/user")
@Validated
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"}, allowedHeaders = "content-Type", allowCredentials = "true")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = "")
    public Flux<UserResponseModel> getAll() {

        return userService.getAll();
    }

}