package com.easy.ecomm.mock;

import com.easy.ecomm.model.dto.UserDto;
import com.easy.ecomm.utils.StringUtils;

public class UserDtoMock {

    private UserDtoMock(){
    }

    public static UserDto onlyMandatoryFields(){
        return UserDto.builder()
                .password("123Abc!@")
                .email(StringUtils.generateRandomEmail())
                .firstName(StringUtils.generateRandomString(8))
                .lastName(StringUtils.generateRandomString(8))
                .build();
    }

    public static UserDto withoutPassword(){
        UserDto userDto = onlyMandatoryFields();
        userDto.setPassword(null);
        return userDto;
    }

    public static UserDto withoutEmail(){
        UserDto userDto = onlyMandatoryFields();
        userDto.setEmail(null);
        return userDto;
    }

    public static UserDto withoutFirstName(){
        UserDto userDto = onlyMandatoryFields();
        userDto.setFirstName(null);
        return userDto;
    }

}
