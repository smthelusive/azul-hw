package smthelusive.genre;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class AdminGenreTest {
    @Test
    void adminRoleViewGenres() {
        given().auth().preemptive()
                .basic("alice", "alice")
                .when()
                .get("/api/v1/genres")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void adminRoleGetSingleGenre() {
        given().auth().preemptive()
                .basic("alice", "alice")
                .when()
                .get("/api/v1/genres/1")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("name", is("horror"));
    }

    @Test
    void adminRoleCreateGenre() {
        given().auth().preemptive()
                .basic("alice", "alice")
                .when()
                .header("Content-Type","application/json" )
                .header("Accept","application/json" )
                .body(new File("src/test/resources/genre_payload.json"))
                .post("/api/v1/genres")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("name", is("adventure"));
    }

    @Test
    void adminRoleUpdateGenre() {
        given().auth().preemptive()
                .basic("alice", "alice")
                .when()
                .header("Content-Type","application/json" )
                .header("Accept","application/json" )
                .body(new File("src/test/resources/genre_payload.json"))
                .put("/api/v1/genres/2")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("name", is("adventure"));
    }

    @Test
    void adminRoleDeleteGenre() {
        given().auth().preemptive()
                .basic("alice", "alice")
                .when()
                .delete("/api/v1/genres/3")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
}
