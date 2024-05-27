package smthelusive.book;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class MalformedBookTest {
    @Test
    void singleBookNotFound() {
        given().auth().preemptive()
                .basic("alice", "alice")
                .when()
                .get("/api/v1/books/100")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    void createBookMalformedRequest() {
        given().auth().preemptive()
                .basic("alice", "alice")
                .when()
                .header("Content-Type","application/json" )
                .header("Accept","application/json" )
                .body(new File("src/test/resources/book_malformed_payload.json"))
                .post("/api/v1/books/create")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    void updateBookInvalidReferencesRequest() {
        given().auth().preemptive()
                .basic("alice", "alice")
                .when()
                .header("Content-Type","application/json" )
                .header("Accept","application/json" )
                .body(new File("src/test/resources/book_invalid_references_payload.json"))
                .put("/api/v1/books/update/1")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    void deleteUnexistingBook() {
        given().auth().preemptive()
                .basic("alice", "alice")
                .when()
                .delete("/api/v1/books/delete/100")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
