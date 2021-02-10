package com.easy.ecomm.mock;

import com.easy.ecomm.model.dto.UserDto;
import com.easy.ecomm.utils.StringUtils;

public class UserDtoMock {

    private UserDtoMock(){
    }

    public static UserDto onlyMandatoryFields(){
        UserDto userDto = new UserDto();
        userDto.setPassword("Pass123!@");
        userDto.setEmail(StringUtils.generateRandomEmail());
        userDto.setFirstName(StringUtils.generateRandomString(8));
        userDto.setLastName(StringUtils.generateRandomString(8));

        return userDto;
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
