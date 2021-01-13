package com.easy.ecomm.resources;

import com.easy.ecomm.mock.ProductMock;
import com.easy.ecomm.model.Product;
import com.easy.ecomm.testcontainer.MongoContainer;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@QuarkusTestResource(MongoContainer.class)
class ProductResourcesIT {

    private static final String PRODUCTS_PATH = "/products";
    private static String UUID;

    @Test
    @Order(1)
    void shouldSuccessfullyPrepareTestEnvironment(){
        Product productOne = ProductMock.allFields();
        Product productTwo = ProductMock.allFields();
        Product productThree = ProductMock.allFields();
        Product productFour = ProductMock.allFields();

        productOne.setCategory("automotive");
        productTwo.setCategory("electronic");

        List<Product> productList = Arrays.asList(productOne, productTwo, productThree, productFour);
        productList.forEach(this::executeProductPost);

        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(PRODUCTS_PATH)
                .then()
                .statusCode(200)
                .extract().response();

        List<Product> productListResponse = response.getBody().as(new TypeRef<>() {
        });

        UUID = productListResponse.get(3).getId();

        assertEquals(4, productListResponse.size());
    }

    @Test
    void shouldSuccessfullyRetrieveProductsByCategory() {
        Response response = given()
                .contentType(ContentType.JSON)
                .param("category", "books")
                .when()
                .get(PRODUCTS_PATH)
                .thenReturn();

        Product[] productListResponse = response.getBody().as(Product[].class);

        // ProductMock.allFields() by default will set category as Books;
        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        assertEquals(2, productListResponse.length);
    }

    @Test
    void shouldSuccessfullyUpdateProduct() {
        Product productToUpdate = ProductMock.allFields();
        productToUpdate.setId(UUID);
        productToUpdate.setColor("color");
        productToUpdate.setDescription("description");
        productToUpdate.setStockAmount(40);

        given().body(productToUpdate)
                .contentType(ContentType.JSON)
                .when()
                .put(PRODUCTS_PATH + "/" + UUID).prettyPeek().thenReturn()
                .then()
                .statusCode(HttpStatus.SC_OK);

        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(PRODUCTS_PATH + "/" + UUID)
                .thenReturn();

        Product productResponse = response.getBody().as(Product.class);

        assertNotNull(productResponse.getUpdatedDate());
        assertNotNull(productResponse.getCreatedDate());

        assertEquals(LocalDate.now(), productResponse.getCreatedDate());
        assertEquals(LocalDate.now(), productResponse.getUpdatedDate());

        assertEquals(productToUpdate.getDescription(), productResponse.getDescription());
        assertEquals(productToUpdate.getCategory(), productResponse.getCategory());
        assertEquals(productToUpdate.getColor(), productResponse.getColor());
    }

    @Test
    @Order(2)
    void shouldSuccessfullyFindProductById() {
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(PRODUCTS_PATH + "/" + UUID)
                .thenReturn();

        Product productResponse = response.getBody().as(Product.class);

        assertNull(productResponse.getUpdatedDate());
        assertEquals(UUID, productResponse.getId());
        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        assertEquals(ProductMock.allFields().getCategory(), productResponse.getCategory());
        assertEquals(ProductMock.allFields().getColor(), productResponse.getColor());
        assertEquals(LocalDate.now(), productResponse.getCreatedDate());
        assertNull(productResponse.getUpdatedDate());

    }

    @Test
    void shouldNotRetrieveProductWhenIdDoNotExist(){
        given().contentType(ContentType.JSON)
                .when()
                .get(PRODUCTS_PATH + "/" + java.util.UUID.randomUUID().toString())
                .then().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    void shouldSuccessfullyRetrieveAllProducts() {
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(PRODUCTS_PATH)
                .thenReturn();

        Product[] productListResponse = response.getBody().as(Product[].class);

        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        assertEquals(4, productListResponse.length);

    }

    private void executeProductPost(Product product) {
        given().body(product)
                .contentType(ContentType.JSON)
                .when()
                .post(PRODUCTS_PATH).prettyPeek();
    }

}
