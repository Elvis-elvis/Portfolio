package com.ruberwa.myportfolio2.user;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class UserResponseModel {
    String userId;
    String firstname;
    String lastname;
    int age;
    String origins;
}