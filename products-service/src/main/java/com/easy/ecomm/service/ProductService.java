package com.easy.ecomm.service;

import com.easy.ecomm.model.Product;
import com.easy.ecomm.repositories.ProductRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.stream.Stream;

@ApplicationScoped
public class ProductService {

    @Inject
    ProductRepository productRepository;

    public String save(Product product){
        return productRepository.saveProduct(product);
    }

    public Product findById(String id){
        return productRepository.findById(id);
    }

    public Stream<Product> findAll(String category) {
        if (category != null){
            return findByCategory(category);
        }
        return productRepository.findAll().stream();
    }

    public Stream<Product> findByCategory(String category) {
        return productRepository.findByCategory(category);
    }

}
