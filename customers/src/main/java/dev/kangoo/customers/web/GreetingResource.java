package dev.kangoo.customers.web;

import dev.kangoo.customers.domain.customer.Customer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
public class GreetingResource {

    @GET
    public Customer hello() {
        return new Customer();
    }
}