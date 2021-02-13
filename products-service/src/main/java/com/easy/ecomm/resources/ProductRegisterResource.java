package com.easy.ecomm.resources;

import com.easy.ecomm.model.Product;
import com.easy.ecomm.model.ProductDto;
import com.easy.ecomm.service.ProductService;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductRegisterResource {

    @Inject
    ProductService productService;

    @POST
    public Response saveProduct(ProductDto product) {
        return Response.status(Response.Status.CREATED).entity(productService.save(product)).build();
    }

    @GET
    @Path("{id}")
    public Product findById(@PathParam("id") String id){
        return productService.findById(new ObjectId(id));
    }

    @GET
    public List<Product> findAll(){
        return productService.findAll();
    }

    @PUT
    @Path("{id}")
    public Response updateProduct(@PathParam("id") String id, ProductDto product) {
        return Response.status(Response.Status.OK).entity(productService.update(new ObjectId(id), product)).build();
    }

    @POST
    @Path("{id}")
    public Response changeActiveStatus(@PathParam("id") String id) {
        return Response.status(Response.Status.OK).entity(productService.changeActiveStatus(new ObjectId(id))).build();
    }

}



