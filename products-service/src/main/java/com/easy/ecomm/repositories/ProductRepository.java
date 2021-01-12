package com.easy.ecomm.repositories;

import com.easy.ecomm.model.Product;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Stream;

@ApplicationScoped
public class ProductRepository implements PanacheMongoRepositoryBase<Product, String> {

        public String saveProduct(Product product){
            product.setId(UUID.randomUUID().toString());
            product.setCreatedDate(LocalDate.now());
            persistOrUpdate(product);
            return product.getId();
        }

        public String updateProduct(Product product){
            Product productFound = findById(product.getId());
            product.setUpdatedDate(LocalDate.now());
            persistOrUpdate(product);
            return product.getId();

        }

        public Product findById(String id){
            return find("_id", id).firstResult();
        }

        public Stream<Product> findByCategory(String category){
            return find("category", category).stream();
        }
}
