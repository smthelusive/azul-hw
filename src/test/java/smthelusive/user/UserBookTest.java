package smthelusive.user;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class UserBookTest {

    @Test
    void userRoleViewBooks() {
        given().auth().preemptive()
                .basic("bob", "bob")
                .when()
                .get("/api/v1/books")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void userRoleGetSingleBook() {
        given().auth().preemptive()
                .basic("mary", "mary")
                .when()
                .get("/api/v1/books/1")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("title", is("Shining"))
                .body("annotation", is("Scary stuff"));
    }

    @Test
    void userRoleCreateBook() {
        given().auth().preemptive()
                .basic("mary", "mary")
                .when()
                .header("Content-Type","application/json" )
                .header("Accept","application/json" )
                .body(new File("src/test/resources/book_payload.json"))
                .post("/api/v1/books/create")
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    void userRoleUpdateBook() {
        given().auth().preemptive()
                .basic("bob", "bob")
                .when()
                .header("Content-Type","application/json" )
                .header("Accept","application/json" )
                .body(new File("src/test/resources/book_payload.json"))
                .put("/api/v1/books/update/2")
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    void userRoleDeleteBook() {
        given().auth().preemptive()
                .basic("mary", "mary")
                .when()
                .delete("/api/v1/books/delete/3")
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
