package smthelusive.book;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import smthelusive.GenericTestSetup;

import java.io.File;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class GuestBookTest extends GenericTestSetup {

    @Test
    void guestRoleViewBooks() {
        given().when()
                .get(BOOK_ENDPOINT)
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    void guestRoleGetSingleBook() {
        given().when()
                .get(BOOK_ENDPOINT_ID, 1)
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    void guestRoleCreateBook() {
        given().when()
                .header("Content-Type","application/json" )
                .header("Accept","application/json" )
                .body(new File("src/test/resources/book_payload.json"))
                .post(BOOK_ENDPOINT)
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    void guestRoleUpdateBook() {
        given().when()
                .header("Content-Type","application/json" )
                .header("Accept","application/json" )
                .body(new File("src/test/resources/book_payload.json"))
                .put(BOOK_ENDPOINT_ID, 2)
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    void guestRoleDeleteBook() {
        given().when()
                .delete(BOOK_ENDPOINT_ID, 3)
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }
}
