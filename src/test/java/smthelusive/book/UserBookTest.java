package smthelusive.book;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import smthelusive.GenericTestSetup;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class UserBookTest extends GenericTestSetup {

    @Test
    void userRoleViewBooks() {
        given().auth().preemptive()
                .basic(USER_ROLE_USER, USER_ROLE_PASSWORD)
                .when()
                .get(BOOK_ENDPOINT)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void userRoleGetSingleBook() {
        given().auth().preemptive()
                .basic(USER_ROLE_USER, USER_ROLE_PASSWORD)
                .when()
                .get(BOOK_ENDPOINT_ID, 1)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("title", is("Shining"))
                .body("annotation", is("Scary stuff"));
    }

    @Test
    void userRoleCreateBook() {
        given().auth().preemptive()
                .basic(USER_ROLE_USER, USER_ROLE_PASSWORD)
                .when()
                .header("Content-Type","application/json" )
                .header("Accept","application/json" )
                .body(new File("src/test/resources/book_payload.json"))
                .post(BOOK_ENDPOINT)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    void userRoleUpdateBook() {
        given().auth().preemptive()
                .basic(USER_ROLE_USER, USER_ROLE_PASSWORD)
                .when()
                .header("Content-Type","application/json" )
                .header("Accept","application/json" )
                .body(new File("src/test/resources/book_payload.json"))
                .put(BOOK_ENDPOINT_ID, 2)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    void userRoleDeleteBook() {
        given().auth().preemptive()
                .basic(USER_ROLE_USER, USER_ROLE_PASSWORD)
                .when()
                .delete(BOOK_ENDPOINT_ID, 3)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
