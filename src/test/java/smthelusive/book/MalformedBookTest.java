package smthelusive.book;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import smthelusive.GenericTestSetup;

import java.io.File;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class MalformedBookTest extends GenericTestSetup {
    @Test
    void singleBookNotFound() {
        given().auth().preemptive()
                .basic(ADMIN_ROLE_USER, ADMIN_ROLE_PASSWORD)
                .when()
                .get(BOOK_ENDPOINT_ID, 100)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    void createBookMalformedRequest() {
        given().auth().preemptive()
                .basic(ADMIN_ROLE_USER, ADMIN_ROLE_PASSWORD)
                .when()
                .header("Content-Type","application/json" )
                .header("Accept","application/json" )
                .body(new File("src/test/resources/book_malformed_payload.json"))
                .post(BOOK_ENDPOINT)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    void updateBookInvalidReferencesRequest() {
        given().auth().preemptive()
                .basic(ADMIN_ROLE_USER, ADMIN_ROLE_PASSWORD)
                .when()
                .header("Content-Type","application/json" )
                .header("Accept","application/json" )
                .body(new File("src/test/resources/book_invalid_references_payload.json"))
                .put(BOOK_ENDPOINT_ID, 1)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    void deleteUnexistingBook() {
        given().auth().preemptive()
                .basic(ADMIN_ROLE_USER, ADMIN_ROLE_PASSWORD)
                .when()
                .delete(BOOK_ENDPOINT_ID, 100)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
