package com.easy.ecomm.resources;

import com.easy.ecomm.mock.ProductDtoMock;
import com.easy.ecomm.model.Product;
import com.easy.ecomm.model.ProductDto;
import com.easy.ecomm.testcontainer.MongoContainer;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
@QuarkusTestResource(MongoContainer.class)
class ProductResourcesIT {

    private static final String PRODUCTS_PATH = "/products/";

    @Test
    void shouldSuccessfullySaveProduct() {

        given().body(ProductDtoMock.allFields())
                .contentType(ContentType.JSON)
                .when()
                .post(PRODUCTS_PATH).prettyPeek()
                .then()
                .statusCode(201);
    }

    @Test
    void shouldSuccessfullyFindProductById(){
        ProductDto productDto = ProductDtoMock.allFields();

        Product product = given().body(productDto)
                .contentType(ContentType.JSON)
                .when()
                .post(PRODUCTS_PATH).prettyPeek()
                .then().extract()
                .response().getBody().as(Product.class);

        given().body(ProductDtoMock.allFields())
                .contentType(ContentType.JSON)
                .when()
                .get(PRODUCTS_PATH + product.getId()).prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .response().getBody().as(Product.class);


    }

    private void executeProductPost(ProductDto product) {
        given().body(product)
                .contentType(ContentType.JSON)
                .when()
                .post(PRODUCTS_PATH);
    }

}
