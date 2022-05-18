package dev.kangoo.customers.rest;

import dev.kangoo.customers.core.product.Product;
import dev.kangoo.customers.core.product.ProductDto;
import dev.kangoo.customers.core.product.ProductService;
import dev.kangoo.customers.core.support.PageRequest;
import dev.kangoo.customers.core.support.PageResponse;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    ProductService productService;

    @GET
    @Path("{id}")
    public Response findProductById(@PathParam("id") String id){
        return Response.status(Response.Status.OK).entity(productService.findById(new ObjectId(id))).build();
    }

    @POST
    public Response saveProduct(@Valid ProductDto product) {
        return Response.status(Response.Status.CREATED).entity(productService.save(product)).build();
    }

    @GET
    public PageResponse<Product> findAll(@BeanParam PageRequest pageRequest){
        return productService.findAll(pageRequest);
    }

    @PUT
    @Path("{id}")
    public Response updateProduct(@PathParam("id") String id, ProductDto product) {
        return Response.status(Response.Status.OK).entity(productService.update(new ObjectId(id), product)).build();
    }

    @POST
    @Path("list/id")
    public List<Product> findById(List<String> idList){
        return productService.findByIdList(idList);
    }

}



