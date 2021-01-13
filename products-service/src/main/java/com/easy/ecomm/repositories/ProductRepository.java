package com.easy.ecomm.repositories;

import com.easy.ecomm.model.Product;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import lombok.SneakyThrows;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Stream;

@ApplicationScoped
public class ProductRepository implements PanacheMongoRepositoryBase<Product, String> {

        public String saveProduct(Product product){
            product.setId(UUID.randomUUID().toString());
            product.setCreatedDate(LocalDate.now());
            product.setUpdatedDate(null);
            persistOrUpdate(product);
            return product.getId();
        }

        public String updateProduct(String productId, Product product){
            Product productFound = findProductById(productId);
            product.setCreatedDate(productFound.getCreatedDate());
            product.setId(productId);
            product.setUpdatedDate(LocalDate.now());
            update(product);
            return product.getId();
        }

        @SneakyThrows
        public Product findProductById(String productId){
            return findByIdOptional(productId).orElseThrow(() ->
                    new NotFoundException(String.format("[%s] Product not found", productId)));
        }

        public Stream<Product> findByCategory(String category){
            return find("category", category).stream();
        }
}
