package com.easy.ecomm.resources;

import com.easy.ecomm.mock.CustomerDtoMock;
import com.easy.ecomm.model.Customer;
import com.easy.ecomm.model.dto.CustomerDto;
import com.easy.ecomm.testcontainers.PostgreSqlTestContainer;
import com.easy.ecomm.utils.StringUtils;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.ws.rs.core.Response;
import java.util.UUID;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@QuarkusTestResource(PostgreSqlTestContainer.class)
class CustomerResourcesIT {

    private static final String USERS_PATH = "/users";
    private static final String USER_PATH_BY_EMAIL = USERS_PATH + "/email/";
    private static final String USER_ACTIVATION_PATH = USERS_PATH + "/activate/";

    @Test
    @DisplayName("Should Create a new User")
    void shouldSuccessfullyCreateUser(){

        CustomerDto newUser = CustomerDtoMock.onlyMandatoryFields();

        Customer responseCustomer = given()
                .body(newUser)
                .contentType(ContentType.JSON)
                .when()
                .post(USERS_PATH).prettyPeek()
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .extract().response()
                .getBody().as(Customer.class);

        assertFalse(responseCustomer.isActive());
        assertNull(responseCustomer.getPassword());
        assertTrue(responseCustomer.getId() > 0);
        assertEquals(newUser.getEmail(), responseCustomer.getEmail());;
        assertEquals(newUser.getFirstName(), responseCustomer.getFirstName());
        assertEquals(newUser.getLastName(), responseCustomer.getLastName());

    }

    @Test
    @DisplayName("Should Find a User by Email")
    void shouldSuccessfullyFindUserByEmail(){

        CustomerDto requestUser = CustomerDtoMock.onlyMandatoryFields();

        executePost(requestUser);

        Customer responseCustomer = given()
                .contentType(ContentType.JSON)
                .when()
                .get(USER_PATH_BY_EMAIL + requestUser.getEmail()).prettyPeek()
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().response()
                .getBody().as(Customer.class);

        assertEquals(requestUser.getEmail(), responseCustomer.getEmail());
    }

    @Test
    @DisplayName("Should Not Find a User by Email")
    void shouldNotFindUserByEmail(){

        CustomerDto requestUser = CustomerDtoMock.onlyMandatoryFields();

        executePost(requestUser);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get(USER_PATH_BY_EMAIL + StringUtils.generateRandomEmail()).prettyPeek()
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @DisplayName("Should Not create a User if Email is already in use")
    void shouldNotCreateUserWithDuplicatedEmail(){

        CustomerDto requestUser = CustomerDtoMock.onlyMandatoryFields();
        CustomerDto duplicatedUser = CustomerDtoMock.onlyMandatoryFields();
        duplicatedUser.setEmail(requestUser.getEmail());

        executePost(requestUser);

        given()
                .body(duplicatedUser)
                .contentType(ContentType.JSON)
                .when()
                .post(USERS_PATH).prettyPeek()
                .then()
                .statusCode(500);
    }

    @DisplayName("Check Requests without mandatory fields")
    @ParameterizedTest(name = "#{index} - Should not Create a new User")
    @MethodSource("invalidUserDtoPayloads")
    void testWithExplicitLocalMethodSource(CustomerDto requestUser) {
        given()
                .body(requestUser)
                .contentType(ContentType.JSON)
                .when()
                .post(USERS_PATH).prettyPeek()
                .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("Should Activate a new User Account")
    void shouldSuccessfullyActivateUserAccount(){

        CustomerDto requestUser = CustomerDtoMock.onlyMandatoryFields();

        Customer newCustomer = given()
                .body(requestUser)
                .contentType(ContentType.JSON)
                .when()
                .post(USERS_PATH).prettyPeek()
                .then()
                .extract().response()
                .getBody().as(Customer.class);

        String activationKey = newCustomer.getActivationKey();

        given()
                .contentType(ContentType.JSON)
                .when()
                .get(USER_ACTIVATION_PATH + activationKey).prettyPeek()
                .then()
                .statusCode(Response.Status.OK.getStatusCode());

        Customer activatedCustomer = given()
                .contentType(ContentType.JSON)
                .when()
                .get(USERS_PATH + "/" + newCustomer.getId()).prettyPeek()
                .then()
                .extract().response()
                .getBody().as(Customer.class);

        assertTrue(activatedCustomer.isActive());
        assertNull(activatedCustomer.getActivationKey());
    }

    @Test
    @DisplayName("Should not Activate an active User Account")
    void shouldNotActivateUserAccountAlreadyActive(){

        CustomerDto requestUser = CustomerDtoMock.onlyMandatoryFields();

        Customer newCustomer = given()
                .body(requestUser)
                .contentType(ContentType.JSON)
                .when()
                .post(USERS_PATH).prettyPeek()
                .then()
                .extract().response()
                .getBody().as(Customer.class);

        String activationKey = newCustomer.getActivationKey();

        given()
                .contentType(ContentType.JSON)
                .when()
                .get(USER_ACTIVATION_PATH + activationKey).prettyPeek()
                .then()
                .statusCode(Response.Status.OK.getStatusCode());

        given()
                .contentType(ContentType.JSON)
                .when()
                .get(USER_ACTIVATION_PATH + activationKey).prettyPeek()
                .then()
                .statusCode(500);
    }

    @Test
    @DisplayName("Should not Activate a new User Account with invalid activationKey")
    void shouldNotActivateUserAccountWithInvalidKey(){

        CustomerDto requestUser = CustomerDtoMock.onlyMandatoryFields();

        executePost(requestUser);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get(USER_ACTIVATION_PATH + UUID.randomUUID().toString()).prettyPeek()
                .then()
                .statusCode(500);
    }

    @Test
    @DisplayName("Should Update the User's account info")
    void shouldSuccessfullyUpdateUser(){

        CustomerDto newUser = CustomerDtoMock.onlyMandatoryFields();

        Customer createCustomer = given()
                .body(newUser)
                .contentType(ContentType.JSON)
                .when()
                .post(USERS_PATH).prettyPeek()
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .extract().response()
                .getBody().as(Customer.class);

        newUser.setLastName("updated");
        newUser.setFirstName("updated");

        Customer updatedCustomer = given()
                .body(newUser)
                .contentType(ContentType.JSON)
                .when()
                .put(USERS_PATH + "/" + createCustomer.getId()).prettyPeek()
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().response()
                .getBody().as(Customer.class);

        assertEquals("updated", updatedCustomer.getFirstName());
        assertEquals("updated", updatedCustomer.getLastName());

    }

    static Stream<CustomerDto> invalidUserDtoPayloads(){

        CustomerDto noPassword = CustomerDtoMock.withoutPassword();
        CustomerDto noFirstName = CustomerDtoMock.withoutFirstName();
        CustomerDto noEmail = CustomerDtoMock.withoutEmail();

        return Stream.of(noPassword, noFirstName, noEmail);
    }

    private void executePost(CustomerDto customerDto){
        given()
                .body(customerDto)
                .contentType(ContentType.JSON)
                .when()
                .post(USERS_PATH).prettyPeek();
    }

}
