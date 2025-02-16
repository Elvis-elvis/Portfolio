package com.ruberwa.myportfolio2.user;

import com.ruberwa.myportfolio2.utils.EntityDTOUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Flux<UserResponseModel> getAll(){
        return userRepository.findAll()
                .map(EntityDTOUtil::toUserResponseModel);
    }

}