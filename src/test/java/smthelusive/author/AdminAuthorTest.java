package smthelusive.author;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import smthelusive.GenericTestSetup;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class AdminAuthorTest extends GenericTestSetup {

    @Test
    void adminRoleViewAuthors() {
        given().auth().preemptive()
                .basic(ADMIN_ROLE_USER, ADMIN_ROLE_PASSWORD)
                .when()
                .get(AUTHOR_ENDPOINT)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void adminRoleGetSingleAuthor() {
        given().auth().preemptive()
                .basic(ADMIN_ROLE_USER, ADMIN_ROLE_PASSWORD)
                .when()
                .get(AUTHOR_ENDPOINT_ID, 1)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("bio", is("Super horror author"))
                .body("firstName", is("Stephen"))
                .body("lastName", is("King"));
    }


    @Test
    void adminRoleCreateAuthor() {
        given().auth().preemptive()
                .basic(ADMIN_ROLE_USER, ADMIN_ROLE_PASSWORD)
                .when()
                .header("Content-Type","application/json" )
                .header("Accept","application/json" )
                .body(new File("src/test/resources/author_payload.json"))
                .post(AUTHOR_ENDPOINT)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("firstName", is("Joshua"))
                .body("lastName", is("Bloch"));
    }

    @Test
    void adminRoleUpdateGenre() {
        given().auth().preemptive()
                .basic(ADMIN_ROLE_USER, ADMIN_ROLE_PASSWORD)
                .when()
                .header("Content-Type","application/json" )
                .header("Accept","application/json" )
                .body(new File("src/test/resources/author_payload.json"))
                .put(AUTHOR_ENDPOINT_ID, 2)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("firstName", is("Joshua"))
                .body("lastName", is("Bloch"));
    }

    @Test
    void adminRoleDeleteGenre() {
        given().auth().preemptive()
                .basic(ADMIN_ROLE_USER, ADMIN_ROLE_PASSWORD)
                .when()
                .delete(AUTHOR_ENDPOINT_ID, 3)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
}
