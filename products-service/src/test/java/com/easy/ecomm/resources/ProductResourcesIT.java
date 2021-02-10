package com.easy.ecomm.resources;

import com.easy.ecomm.mock.ProductDtoMock;
import com.easy.ecomm.model.ProductDto;
import com.easy.ecomm.testcontainer.MongoContainer;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;

@QuarkusTest
@QuarkusTestResource(MongoContainer.class)
class ProductResourcesIT {

    private static final String PRODUCTS_PATH = "/products";

    @Test
    void shouldSuccessfullySaveProduct() {

        given().body(ProductDtoMock.allFields())
                .contentType(ContentType.JSON)
                .when()
                .post(PRODUCTS_PATH).prettyPeek()
                .then()
                .statusCode(201);
    }

    private void executeProductPost(ProductDto product) {
        given().body(product)
                .contentType(ContentType.JSON)
                .when()
                .post(PRODUCTS_PATH).prettyPeek();
    }

}
