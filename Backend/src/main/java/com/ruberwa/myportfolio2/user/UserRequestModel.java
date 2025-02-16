package com.ruberwa.myportfolio2.user;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserRequestModel {
    private String email;
    private String firstName;
    private String lastName;
}
