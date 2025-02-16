package com.ruberwa.myportfolio2.UserSubdomain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserRequestModel2 {
    private String email;
    private String firstName;
    private String lastName;
    private List<String> roles;
    private List<String> permissions;
}
