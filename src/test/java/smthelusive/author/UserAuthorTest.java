package smthelusive.author;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import smthelusive.GenericTestSetup;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class UserAuthorTest extends GenericTestSetup {
    @Test
    void userRoleViewAuthors() {
        given().auth().preemptive()
                .basic(USER_ROLE_USER, USER_ROLE_PASSWORD)
                .when()
                .get(AUTHOR_ENDPOINT)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void userRoleGetSingleAuthor() {
        given().auth().preemptive()
                .basic(USER_ROLE_USER, USER_ROLE_PASSWORD)
                .when()
                .get(AUTHOR_ENDPOINT_ID, 1)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("bio", is("Super horror author"))
                .body("firstName", is("Stephen"))
                .body("lastName", is("King"));
    }


    @Test
    void userRoleCreateAuthor() {
        given().auth().preemptive()
                .basic(USER_ROLE_USER, USER_ROLE_PASSWORD)
                .when()
                .header("Content-Type","application/json" )
                .header("Accept","application/json" )
                .body(new File("src/test/resources/author_payload.json"))
                .post(AUTHOR_ENDPOINT)
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
                .body(new File("src/test/resources/author_payload.json"))
                .put(AUTHOR_ENDPOINT_ID, 2)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    void userRoleDeleteGenre() {
        given().auth().preemptive()
                .basic(USER_ROLE_USER, USER_ROLE_PASSWORD)
                .when()
                .delete(AUTHOR_ENDPOINT_ID, 3)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
