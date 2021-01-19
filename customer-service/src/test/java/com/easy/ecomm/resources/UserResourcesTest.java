package com.easy.ecomm.resources;

import com.easy.ecomm.mock.UserRegisterMock;
import com.easy.ecomm.model.User;
import com.easy.ecomm.model.UserRegisterRequest;
import com.easy.ecomm.testcontainers.PostgreSqlTestContainer;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class UserResourcesTest {

    private static final String USERS_PATH = "/users";

    @Test
    @DisplayName("Create new User")
    void shouldSuccessfullyCreateUser(){

        UserRegisterRequest requestUser = UserRegisterMock.onlyMandatoryFields();

        User responseUser = given()
                .body(requestUser)
                .contentType(ContentType.JSON)
                .when()
                .post(USERS_PATH)
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .extract().response()
                .getBody().as(User.class);

        assertFalse(responseUser.isActive());
        assertNull(responseUser.getPassword());
        assertTrue(responseUser.getId() > 0);
        assertEquals(requestUser.getEmail(), responseUser.getEmail());;
        assertEquals(requestUser.getFirstName(), responseUser.getFirstName());
        assertEquals(requestUser.getLastName(), responseUser.getLastName());

    }

}
