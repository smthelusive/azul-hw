package smthelusive.book;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class GuestBookTest {

    @Test
    void guestRoleViewBooks() {
        given().when()
                .get("/api/v1/books")
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    void guestRoleGetSingleBook() {
        given().when()
                .get("/api/v1/books/1")
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    void guestRoleCreateBook() {
        given().when()
                .header("Content-Type","application/json" )
                .header("Accept","application/json" )
                .body(new File("src/test/resources/book_payload.json"))
                .post("/api/v1/books")
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    void guestRoleUpdateBook() {
        given().when()
                .header("Content-Type","application/json" )
                .header("Accept","application/json" )
                .body(new File("src/test/resources/book_payload.json"))
                .put("/api/v1/books/2")
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    void guestRoleDeleteBook() {
        given().when()
                .delete("/api/v1/books/3")
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }
}
