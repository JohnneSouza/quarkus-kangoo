package com.easy.ecomm.mock;

import com.easy.ecomm.model.Product;

import java.time.LocalDate;
import java.util.UUID;

public final class ProductMock {

    private ProductMock() {
    }

    public static Product onlyMandatoryFields(){
        Product product = new Product();
        product.setDescription("Lorem ipsum dolor sit amet.");
        product.setCategory("books");

        return product;
    }

    public static Product allFields(){
        Product product = onlyMandatoryFields();
        product.setColor("Blue");
        product.setPrice(Math.random() * 42);
        product.setId(UUID.randomUUID().toString());
        product.setStockAmount(42);

        return product;
    }

}
