package com.easy.ecomm.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.validation.constraints.Size;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class User extends UserDTO {

    private static final String PWD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>])$";
    private static final int PWD_MIN_LEN = 8;

    @Size(min = PWD_MIN_LEN)
    private String password;

}


