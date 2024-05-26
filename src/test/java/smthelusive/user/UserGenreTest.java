package smthelusive.user;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class UserGenreTest {
    @Test
    void userRoleViewGenres() {
        given().auth().preemptive()
                .basic("bob", "bob")
                .when()
                .get("/api/v1/genres")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void userRoleGetSingleGenre() {
        given().auth().preemptive()
                .basic("bob", "bob")
                .when()
                .get("/api/v1/genres/1")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("name", is("horror"));
    }

    @Test
    void userRoleCreateGenre() {
        given().auth().preemptive()
                .basic("mary", "mary")
                .when()
                .header("Content-Type","application/json" )
                .header("Accept","application/json" )
                .body(new File("src/test/resources/genre_payload.json"))
                .post("/api/v1/genres/create")
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    void userRoleUpdateGenre() {
        given().auth().preemptive()
                .basic("mary", "mary")
                .when()
                .header("Content-Type","application/json" )
                .header("Accept","application/json" )
                .body(new File("src/test/resources/genre_payload.json"))
                .put("/api/v1/genres/update/2")
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    void userRoleDeleteGenre() {
        given().auth().preemptive()
                .basic("mary", "mary")
                .when()
                .delete("/api/v1/genres/delete/3")
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
