package com.easy.ecomm.mock;

import com.easy.ecomm.core.customer.CustomerDto;
import com.easy.ecomm.utils.StringUtils;

public class CustomerDtoMock {

    private CustomerDtoMock(){
    }

    public static CustomerDto onlyMandatoryFields(){
        CustomerDto customerDto = new CustomerDto();
        customerDto.setPassword("Pass123!@");
        customerDto.setEmail(StringUtils.generateRandomEmail());
        customerDto.setFirstName(StringUtils.generateRandomString(8));
        customerDto.setLastName(StringUtils.generateRandomString(8));

        return customerDto;
    }

    public static CustomerDto withoutPassword(){
        CustomerDto customerDto = onlyMandatoryFields();
        customerDto.setPassword(null);
        return customerDto;
    }

    public static CustomerDto withoutEmail(){
        CustomerDto customerDto = onlyMandatoryFields();
        customerDto.setEmail(null);
        return customerDto;
    }

    public static CustomerDto withoutFirstName(){
        CustomerDto customerDto = onlyMandatoryFields();
        customerDto.setFirstName(null);
        return customerDto;
    }

}
