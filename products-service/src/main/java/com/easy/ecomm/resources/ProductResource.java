package com.easy.ecomm.resources;

import com.easy.ecomm.model.Product;
import com.easy.ecomm.service.ProductService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.stream.Stream;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    ProductService productService;

    @POST
    public Response saveProduct(Product product) {
        return Response.status(Response.Status.CREATED).entity(productService.save(product)).build();
    }

    @GET
    @Path("{id}")
    public Product findById(@PathParam("id") String id){
        return productService.findById(id);
    }

    @GET
    public Stream<Product> findAll(@QueryParam("category") String category){
        return productService.findAll(category);
    }

}



