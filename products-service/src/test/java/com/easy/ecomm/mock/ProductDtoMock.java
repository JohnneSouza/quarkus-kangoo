package com.easy.ecomm.mock;

import com.easy.ecomm.model.ProductDto;

public final class ProductDtoMock {

    private ProductDtoMock() {
    }

    public static ProductDto onlyMandatoryFields(){
        ProductDto product = new ProductDto();
        product.setDescription("Lorem ipsum dolor sit amet.");
        product.setCategory("books");

        return product;
    }

    public static ProductDto allFields(){
        ProductDto product = onlyMandatoryFields();
        product.setColor("Blue");
        product.setPriceSell(Math.random() * 42);
        product.setPriceCost(Math.random() * 42);
        product.setStockAmount(42);

        return product;
    }

}
