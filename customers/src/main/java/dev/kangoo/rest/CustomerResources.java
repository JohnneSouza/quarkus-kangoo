package dev.kangoo.rest;

import dev.kangoo.core.customer.Customer;
import dev.kangoo.core.customer.CustomerDto;
import dev.kangoo.core.customer.CustomerService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/customers")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class CustomerResources {

    @Inject
    CustomerService customerService;

    @POST
    @Transactional
    @Operation(summary = "Creates a new Customer")
    @APIResponse(responseCode = "201", content =
    @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomerDto.class, required = true)))
    public Response register(@Valid CustomerDto user){
        return Response.status(Response.Status.CREATED)
                .entity(customerService.createUser(user, user.getPassword())).build();
    }

    @PUT
    @Transactional
    @Operation(summary = "Updates the Customer info")
    @APIResponse(responseCode = "200", content =
    @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomerDto.class, required = true)))
    @Path("{id}")
    public Response update(@Valid CustomerDto user, @PathParam("id") Long id){
        return Response.status(Response.Status.OK)
                .entity(customerService.updateUser(user, id, user.getPassword())).build();
    }

    @GET
    @Operation(summary = "Retrieve all Customers")
    @APIResponse(responseCode = "200", content =
    @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomerDto.class)))
    public Iterable<Customer> findAll(){
        return customerService.findAll();
    }

    @GET
    @Operation(summary = "Retrieve a Customer by its id")
    @APIResponse(responseCode = "200", content =
    @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomerDto.class)))
    @Path("{id}")
    public Customer findUserById(@PathParam("id") int id){
        return customerService.findUserById(id);
    }

    @GET
    @Operation(summary = "Retrieve a Customer by its email")
    @APIResponse(responseCode = "200", content =
    @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomerDto.class)))
    @Path("email/{email}")
    public Customer findUserByEmail(@PathParam("email") String email){
        return customerService.finderByEmail(email);
    }

    @GET
    @Transactional
    @Operation(summary = "Activate a Customer Account")
    @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON))
    @Path("activate/{key}")
    public Response activateUserAccount(@PathParam("key") String key){
        customerService.activateUser(key);
        return Response.status(Response.Status.OK).build();
    }

}
