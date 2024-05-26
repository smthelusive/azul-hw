package smthelusive.admin;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class AdminBookTest {
    @Test
    void adminRoleViewBooks() {
        given().auth().preemptive()
                .basic("alice", "alice")
                .when()
                .get("/api/v1/books")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void adminRoleGetSingleBook() {
        given().auth().preemptive()
                .basic("alice", "alice")
                .when()
                .get("/api/v1/books/1")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("title", is("Shining"))
                .body("annotation", is("Scary stuff"));
    }

    @Test
    void adminRoleCreateBook() {
        given().auth().preemptive()
                .basic("alice", "alice")
                .when()
                .header("Content-Type","application/json" )
                .header("Accept","application/json" )
                .body(new File("src/test/resources/book_payload.json"))
                .post("/api/v1/books/create")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("title", equalTo("NewBook"))
                .body("count", is(7))
                .body("price", is(17.0F));
    }

    @Test
    void adminRoleUpdateBook() {
        given().auth().preemptive()
                .basic("alice", "alice")
                .when()
                .header("Content-Type","application/json" )
                .header("Accept","application/json" )
                .body(new File("src/test/resources/book_payload.json"))
                .put("/api/v1/books/update/2")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("title", equalTo("NewBook"))
                .body("count", is(7))
                .body("price", is(17.0F));
    }

    @Test
    void adminRoleDeleteBook() {
        given().auth().preemptive()
                .basic("alice", "alice")
                .when()
                .delete("/api/v1/books/delete/3")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
}
