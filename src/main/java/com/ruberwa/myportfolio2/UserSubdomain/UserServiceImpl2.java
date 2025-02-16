package com.ruberwa.myportfolio2.UserSubdomain;



import com.ruberwa.myportfolio2.auth0.Auth0Service;
import com.ruberwa.myportfolio2.utils.EntityDTOUtil;
import com.ruberwa.myportfolio2.utils.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl2 implements UserService2 {

    private final Auth0Service auth0Service;
    private final UserRepository2 userRepository2;

    @Override
    public Mono<UserResponseModel2> addUserFromAuth0(String auth0UserId) {
        return auth0Service.getUserById(auth0UserId)
                .switchIfEmpty(Mono.error(new NotFoundException("User not found with Auth0 ID: " + auth0UserId)))
                .flatMap(auth0User ->
                        userRepository2.findByUserId(auth0UserId)
                                .switchIfEmpty(
                                        auth0Service.assignRoleToUser(auth0UserId, "\n" +
                                                        "rol_DnljSOm5M4k8XrLU")
                                                .doOnSuccess(unused -> log.info("Successfully assigned 'Customer' role to User ID: {}", auth0UserId))
                                                .doOnError(error -> log.error("Failed to assign 'Customer' role to User ID: {}", auth0UserId, error))
                                                .then(auth0Service.getUserById(auth0UserId)
                                                        .doOnSuccess(updatedAuth0User -> log.info("Updated Auth0 User Details After Role Assignment: {}", updatedAuth0User))
                                                        .flatMap(updatedAuth0User ->
                                                                userRepository2.save(
                                                                        User2.builder()
                                                                                .userId(auth0UserId)
                                                                                .email(updatedAuth0User.getEmail())
                                                                                .firstName(updatedAuth0User.getFirstName())
                                                                                .lastName(updatedAuth0User.getLastName())
                                                                                .roles(updatedAuth0User.getRoles())
                                                                                .permissions(updatedAuth0User.getPermissions())
                                                                                .build()
                                                                ).doOnSuccess(user -> log.info("User successfully created in MongoDB: {}", user))
                                                        )
                                                )
                                )
                )
                .map(EntityDTOUtil::toUserResponseModel2)
                .doOnSuccess(user -> log.info("Final User Response: {}", user))
                .doOnError(error -> log.error("Error processing user with ID: {}", auth0UserId, error));
    }


    @Override
    public Mono<UserResponseModel2> syncUserWithAuth0(String auth0UserId) {
        log.info("Starting user sync process for Auth0 User ID: {}", auth0UserId);

        return auth0Service.getUserById(auth0UserId)
                .doOnSuccess(auth0User -> log.info("Fetched Auth0 User Details: {}", auth0User))
                .flatMap(auth0User -> userRepository2.findByUserId(auth0UserId)
                        .flatMap(existingUser -> {
                            log.info("Existing User Found in Database: {}", existingUser);

                            // Update User Fields
                            existingUser.setEmail(auth0User.getEmail());
                            existingUser.setFirstName(auth0User.getFirstName());
                            existingUser.setLastName(auth0User.getLastName());
                            existingUser.setRoles(auth0User.getRoles());
                            existingUser.setPermissions(auth0User.getPermissions());

                            log.info("Updated User Details Before Saving: {}", existingUser);

                            return userRepository2.save(existingUser)
                                    .doOnSuccess(updatedUser -> log.info("Successfully Synced User in MongoDB: {}", updatedUser))
                                    .doOnError(error -> log.error("Failed to Save Synced User to MongoDB: {}", error.getMessage()));
                        })
                        .switchIfEmpty(Mono.error(new RuntimeException("User Not Found in Database: " + auth0UserId)))
                )
                .map(EntityDTOUtil::toUserResponseModel2)
                .doOnSuccess(user -> log.info("Final Synced User Response: {}", user))
                .doOnError(error -> log.error("Error Syncing User with ID {}: {}", auth0UserId, error.getMessage()));
    }

    @Override
    public Flux<UserResponseModel2> getAllUsers() {
        return userRepository2.findAll()
                .map(EntityDTOUtil::toUserResponseModel2)
                .doOnNext(user -> log.info("Fetched User from Database: {}", user))
                .doOnError(error -> log.error("Error fetching users: {}", error.getMessage()));
    }


    @Override
    public Mono<UserResponseModel2> getUserByUserId(String userId) {
        return userRepository2.findByUserId(userId)
                .switchIfEmpty(Mono.error(new NotFoundException("User not found with ID: " + userId)))
                .map(EntityDTOUtil::toUserResponseModel2)
                .doOnSuccess(user -> log.info("Fetched User from Database: {}", user))
                .doOnError(error -> log.error("Error fetching user with ID {}: {}", userId, error.getMessage()));
    }

    @Override
    public Flux<UserResponseModel2> getStaff() {
        return userRepository2.findAll()
                .filter(user -> user.getRoles() != null && user.getRoles().contains("Staff"))
                .map(EntityDTOUtil::toUserResponseModel2)
                .doOnNext(user -> log.info("Fetched Staff from Database: {}", user))
                .doOnError(error -> log.error("Error fetching staff: {}", error.getMessage()));
    }

    public Mono<Void> deleteStaff(String userId) {
        return userRepository2.findByUserId(userId)
                .filter(user -> user.getRoles() != null && user.getRoles().contains("Staff"))
                .switchIfEmpty(Mono.error(new NotFoundException("Staff member not found or not a valid staff")))
                .flatMap(user -> userRepository2.delete(user))
                .doOnSuccess(unused -> log.info("Staff member with ID {} deleted successfully", userId))
                .doOnError(error -> log.error("Error deleting staff member: {}", error.getMessage()));
    }

    @Override
    public Mono<UserResponseModel2> updateStaff(Mono<UserRequestModel2> userRequestModel2, String userId) {
        return userRepository2.findByUserId(userId)
                .filter(user -> user.getRoles() != null && user.getRoles().contains("Staff"))
                .flatMap(existingUser -> userRequestModel2.map(requestModel -> {
                    existingUser.setFirstName(requestModel.getFirstName());
                    existingUser.setLastName(requestModel.getLastName());
                    existingUser.setEmail(requestModel.getEmail());
                    existingUser.setRoles(requestModel.getRoles());
                    existingUser.setPermissions(requestModel.getPermissions());
                    return existingUser;
                }))
                .switchIfEmpty(Mono.error(new NotFoundException("Staff not found with id: " + userId)))
                .flatMap(userRepository2::save)
                .map(EntityDTOUtil::toUserResponseModel2);
    }

    public Mono<UserResponseModel2> addStaffRoleToUser(String userId) {
        log.info("Starting process to add user with ID {} as staff", userId);

        return userRepository2.findByUserId(userId)
                .switchIfEmpty(Mono.error(new NotFoundException("User not found with ID: " + userId)))
                .flatMap(user -> {
//                    if (user.getRoles() != null && user.getRoles().contains("Staff")) {
//                        return Mono.error(new IllegalStateException("User with ID " + userId + " is already a staff member"));
//                    }

                    // Update user roles
                    user.getRoles().add("Staff");

                    //log.info("Assigning 'Staff' role to user with ID: {}", userId);

                    return auth0Service.assignRoleToUser(userId, "rol_1DSAOq7EC8sfW0KF")
                            //.doOnSuccess(unused -> log.info("Successfully assigned 'Staff' role in Auth0 for user with ID: {}", userId))
                            //.doOnError(error -> log.error("Failed to assign 'Staff' role in Auth0 for user with ID: {}", userId, error))
                            .then(userRepository2.save(user));
                            //.doOnSuccess(updatedUser -> log.info("User successfully updated as Staff in MongoDB: {}", updatedUser))
                            //.doOnError(error -> log.error("Failed to save user with Staff role in MongoDB: {}", error.getMessage()));
                })
                .map(EntityDTOUtil::toUserResponseModel2);
                //.doOnSuccess(user -> log.info("Final Staff Member Response: {}", user))
                //.doOnError(error -> log.error("Error adding staff member with ID {}: {}", userId, error.getMessage()));

    }

    @Override
    public Mono<UserResponseModel2> updateUser(Mono<UserRequestModel2> userRequestModel2, String userId) {
        return userRepository2.findByUserId(userId)
                .flatMap(existingUser -> userRequestModel2.map(requestModel ->{
                    existingUser.setFirstName(requestModel.getFirstName());
                    existingUser.setLastName(requestModel.getLastName());
                    existingUser.setEmail(requestModel.getEmail());
                    existingUser.setRoles(requestModel.getRoles());
                    existingUser.setPermissions(requestModel.getPermissions());
                    return existingUser;
                }))
                .switchIfEmpty(Mono.error(new NotFoundException("User not found with id:"  + userId)))
                .flatMap(userRepository2::save)
                .map(EntityDTOUtil::toUserResponseModel2);
    }


}

