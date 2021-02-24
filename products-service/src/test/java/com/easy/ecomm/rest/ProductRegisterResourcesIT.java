package com.easy.ecomm.resources;

import com.easy.ecomm.mock.ProductDtoMock;
import com.easy.ecomm.model.Product;
import com.easy.ecomm.model.ProductDto;
import com.easy.ecomm.repositories.ProductRepository;
import com.easy.ecomm.testcontainer.MongoContainer;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@QuarkusTestResource(MongoContainer.class)
class ProductRegisterResourcesIT {

    private static final String PRODUCTS_PATH = "/products/";
    private static final String PRODUCTS_PATH_BY_ID_LIST = "/products/list/id";

    @Inject
    ProductRepository productRepository;


    @BeforeEach
    public void setup(){
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("Should Create a new Product")
    void shouldSuccessfullySaveProduct() {

        given().body(ProductDtoMock.allFields())
                .contentType(ContentType.JSON)
                .when()
                .post(PRODUCTS_PATH).prettyPeek()
                .then()
                .statusCode(201);
    }

    @Test
    @DisplayName("Should find Product by Id")
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

    @Test
    @DisplayName("Should update Product info")
    void shouldSuccessfullyUpdateProduct(){
        ProductDto productDto = ProductDtoMock.allFields();

        Product oldProduct = given().body(productDto)
                .contentType(ContentType.JSON)
                .when()
                .post(PRODUCTS_PATH).prettyPeek()
                .then().extract()
                .response().getBody().as(Product.class);

        ProductDto productToUpdate = new ProductDto();
        productToUpdate.setCategory("category");
        productToUpdate.setColor("color");
        productToUpdate.setDescription("description");
        productToUpdate.setPriceSell(0.1);
        productToUpdate.setPriceCost(0.2);

        Product updatedProduct = given().body(productToUpdate)
                .contentType(ContentType.JSON)
                .when()
                .put(PRODUCTS_PATH + oldProduct.getId().toString()).prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .response().getBody().as(Product.class);

        assertEquals(productToUpdate.getCategory(), updatedProduct.getCategory());
        assertEquals(productToUpdate.getColor(), updatedProduct.getColor());
        assertEquals(productToUpdate.getDescription(), updatedProduct.getDescription());
        assertEquals(productToUpdate.getPriceCost(), updatedProduct.getPriceCost());
        assertEquals(productToUpdate.getPriceSell(), updatedProduct.getPriceSell());
        assertEquals(LocalDate.now(), updatedProduct.getUpdatedDate());

    }

    @Test
    @DisplayName("Should find Products by Id List")
    void shouldSuccessfullyFindProductsByIdList(){

        // Create 3 Random Products
        executeProductPost(ProductDtoMock.allFields());
        executeProductPost(ProductDtoMock.allFields());
        executeProductPost(ProductDtoMock.allFields());

        // Create 2 Traceable Products
        List<Product> productList = new ArrayList<>();
        productList.add(executeProductPost(ProductDtoMock.allFields()));
        productList.add(executeProductPost(ProductDtoMock.allFields()));

        // Get the Products id;
        List<String> productIdList = new ArrayList<>();
        productList.forEach(product -> productIdList.add(product.getId().toString()));

        Product[] foundProducts = given().body(productIdList)
                .contentType(ContentType.JSON)
                .when()
                .post(PRODUCTS_PATH_BY_ID_LIST).prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .response().getBody().as(Product[].class);

        assertEquals(productIdList.size(), foundProducts.length);
    }

    private Product executeProductPost(ProductDto product) {
        return given().body(product)
                .contentType(ContentType.JSON)
                .when()
                .post(PRODUCTS_PATH)
                .thenReturn()
                .body().as(Product.class);
    }

}
