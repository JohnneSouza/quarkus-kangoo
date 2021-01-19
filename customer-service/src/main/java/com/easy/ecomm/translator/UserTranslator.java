package com.easy.ecomm.translator;

import com.easy.ecomm.model.User;
import com.easy.ecomm.model.UserRegisterRequest;
import com.easy.ecomm.model.UserRegisterResponse;

public class UserTranslator {

    private UserTranslator() {
    }

    public static User userRegisterRequestToUser(UserRegisterRequest userRegisterRequest){
        return User.builder()
                .firstName(userRegisterRequest.getFirstName())
                .lastName(userRegisterRequest.getLastName())
                .password(userRegisterRequest.getPassword())
                .email(userRegisterRequest.getEmail())
                .build();
    }

    public static User userToUserRegisterResponse(User user){
        return UserRegisterResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .active(user.isActive())
                .build();
    }
}
