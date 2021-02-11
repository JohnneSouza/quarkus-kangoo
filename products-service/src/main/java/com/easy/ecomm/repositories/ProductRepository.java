package com.easy.ecomm.repositories;

import com.easy.ecomm.model.Product;
import io.quarkus.mongodb.panache.PanacheMongoRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class ProductRepository implements PanacheMongoRepository<Product> {

    public Product save(Product product){
        persist(product);
        return product;
    }

}
