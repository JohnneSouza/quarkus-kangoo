package com.easy.ecomm.resources;

import com.easy.ecomm.model.User;
import com.easy.ecomm.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResources {

    @Inject
    UserService userService;

    @POST
    public User saveUser(User user){
        return userService.saveUser(user);
    }

    @GET
    public Iterable<User> findAll(){
        return userService.findAll();
    }
    @GET
    @Path("{id}")
    public User findByUserById(@PathParam("id") int id){
        return userService.findUserById(id);
    }

    @GET
    @Path("email/{email}")
    public User findUserByEmail(@PathParam("email") String email){
        return userService.finderByEmail(email);
    }

}
