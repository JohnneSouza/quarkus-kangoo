package com.easy.ecomm.resources;

import com.easy.ecomm.mock.UserDtoMock;
import com.easy.ecomm.model.User;
import com.easy.ecomm.model.dto.UserDto;
import com.easy.ecomm.testcontainers.PostgreSqlTestContainer;
import com.easy.ecomm.utils.StringUtils;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.ws.rs.core.Response;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@QuarkusTestResource(PostgreSqlTestContainer.class)
@TestTransaction
class UserResourcesTest {

    private static final String USERS_PATH = "/users";
    private static final String USER_PATH_BY_EMAIL = USERS_PATH + "/email/";

    @DisplayName("Should Create a new User")
    void shouldSuccessfullyCreateUser(){

        UserDto newUser = UserDtoMock.onlyMandatoryFields();

        User responseUser = given()
                .body(newUser)
                .contentType(ContentType.JSON)
                .when()
                .post(USERS_PATH).prettyPeek()
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .extract().response()
                .getBody().as(User.class);

        assertFalse(responseUser.isActive());
        assertNull(responseUser.getPassword());
        assertTrue(responseUser.getId() > 0);
        assertEquals(newUser.getEmail(), responseUser.getEmail());;
        assertEquals(newUser.getFirstName(), responseUser.getFirstName());
        assertEquals(newUser.getLastName(), responseUser.getLastName());

    }

    @Test
    @DisplayName("Should Find a User by Email")
    void shouldSuccessFindUserByEmail(){

        UserDto requestUser = UserDtoMock.onlyMandatoryFields();

        executePost(requestUser);

        User responseUser = given()
                .contentType(ContentType.JSON)
                .when()
                .get(USER_PATH_BY_EMAIL + requestUser.getEmail()).prettyPeek()
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().response()
                .getBody().as(User.class);

        assertEquals(requestUser.getEmail(), responseUser.getEmail());
    }

    @Test
    @DisplayName("Should Not Find a User by Email")
    void shouldNotFindUserByEmail(){

        UserDto requestUser = UserDtoMock.onlyMandatoryFields();

        executePost(requestUser);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get(USER_PATH_BY_EMAIL + StringUtils.generateRandomEmail()).prettyPeek()
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @DisplayName("Check Requests without mandatory fields")
    @ParameterizedTest(name = "#{index} - Should not Create a new User")
    @MethodSource("invalidUserDtoPayloads")
    void testWithExplicitLocalMethodSource(UserDto requestUser) {
        given()
                .body(requestUser)
                .contentType(ContentType.JSON)
                .when()
                .post(USERS_PATH).prettyPeek()
                .then()
                .statusCode(400);
    }

    static Stream<UserDto> invalidUserDtoPayloads(){

        UserDto noPassword = UserDtoMock.withoutPassword();
        UserDto noFirstName = UserDtoMock.withoutFirstName();
        UserDto noEmail = UserDtoMock.withoutEmail();

        return Stream.of(noPassword, noFirstName, noEmail);
    }

    private void executePost(UserDto userDto){
        given()
                .body(userDto)
                .contentType(ContentType.JSON)
                .when()
                .post(USERS_PATH).prettyPeek();
    }

}
