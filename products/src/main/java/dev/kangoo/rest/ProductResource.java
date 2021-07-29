package dev.kangoo.rest;

import dev.kangoo.core.product.Product;
import dev.kangoo.core.product.ProductDto;
import dev.kangoo.core.product.ProductService;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import javax.validation.Valid;
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
    @Path("list")
    public List<Product> findAll(){
        return productService.findAll();
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



