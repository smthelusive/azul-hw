package smthelusive.genre;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import smthelusive.GenericTestSetup;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class UserGenreTest extends GenericTestSetup {
    @Test
    void userRoleViewGenres() {
        given().auth().preemptive()
                .basic(USER_ROLE_USER, USER_ROLE_PASSWORD)
                .when()
                .get(GENRE_ENDPOINT)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void userRoleGetSingleGenre() {
        given().auth().preemptive()
                .basic(USER_ROLE_USER, USER_ROLE_PASSWORD)
                .when()
                .get(GENRE_ENDPOINT_ID, 1)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("name", is("horror"));
    }

    @Test
    void userRoleCreateGenre() {
        given().auth().preemptive()
                .basic(USER_ROLE_USER, USER_ROLE_PASSWORD)
                .when()
                .header("Content-Type","application/json" )
                .header("Accept","application/json" )
                .body(new File("src/test/resources/genre_payload.json"))
                .post(GENRE_ENDPOINT)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    void userRoleUpdateGenre() {
        given().auth().preemptive()
                .basic(USER_ROLE_USER, USER_ROLE_PASSWORD)
                .when()
                .header("Content-Type","application/json" )
                .header("Accept","application/json" )
                .body(new File("src/test/resources/genre_payload.json"))
                .put(GENRE_ENDPOINT_ID, 2)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    void userRoleDeleteGenre() {
        given().auth().preemptive()
                .basic(USER_ROLE_USER, USER_ROLE_PASSWORD)
                .when()
                .delete(GENRE_ENDPOINT_ID, 3)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
