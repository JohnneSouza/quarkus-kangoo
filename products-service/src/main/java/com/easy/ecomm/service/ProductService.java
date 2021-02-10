package com.easy.ecomm.service;

import com.easy.ecomm.model.Product;
import com.easy.ecomm.model.ProductDto;
import com.easy.ecomm.repositories.ProductRepository;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class ProductService {

    @Inject
    ProductRepository productRepository;

    public Product save(ProductDto productDto){
        Product product = new Product();
        product.setCreatedDate(LocalDate.now());
        product.setDescription(productDto.getDescription());
        product.setCategory(productDto.getCategory());
        product.setColor(productDto.getColor());
        product.setPriceCost(productDto.getPriceCost());
        product.setPriceSell(productDto.getPriceSell());
        product.setStockAmount(productDto.getStockAmount());
        return productRepository.save(product);
    }

    public Product findById(String id){
        return productRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    public List<Product> findAll(){
        return productRepository.findAll().list();
    }

}
