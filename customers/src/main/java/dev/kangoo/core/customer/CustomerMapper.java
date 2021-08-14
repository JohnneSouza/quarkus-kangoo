package dev.kangoo.core.customer;

import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface CustomerMapper {

    CustomerDto customerToDto(Customer customer);

}
