package com.easy.ecomm.mock;

import com.easy.ecomm.model.UserRegisterRequest;
import com.easy.ecomm.utils.StringUtils;

public class UserRegisterMock {

    private UserRegisterMock(){
    }

    public static UserRegisterRequest onlyMandatoryFields(){
        return UserRegisterRequest.builder()
                .password("123Abc!@")
                .email(StringUtils.generateRandomEmail())
                .firstName(StringUtils.generateRandomString(8))
                .lastName(StringUtils.generateRandomString(8))
                .build();
    }

}
