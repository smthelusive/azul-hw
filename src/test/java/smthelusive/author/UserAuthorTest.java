package smthelusive.author;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class UserAuthorTest {
    @Test
    void userRoleViewAuthors() {
        given().auth().preemptive()
                .basic("bob", "bob")
                .when()
                .get("/api/v1/authors")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void userRoleGetSingleAuthor() {
        given().auth().preemptive()
                .basic("mary", "mary")
                .when()
                .get("/api/v1/authors/1")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("bio", is("Super horror author"))
                .body("firstName", is("Stephen"))
                .body("lastName", is("King"));
    }


    @Test
    void userRoleCreateAuthor() {
        given().auth().preemptive()
                .basic("bob", "bob")
                .when()
                .header("Content-Type","application/json" )
                .header("Accept","application/json" )
                .body(new File("src/test/resources/author_payload.json"))
                .post("/api/v1/authors/create")
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    void userRoleUpdateGenre() {
        given().auth().preemptive()
                .basic("bob", "bob")
                .when()
                .header("Content-Type","application/json" )
                .header("Accept","application/json" )
                .body(new File("src/test/resources/author_payload.json"))
                .put("/api/v1/authors/update/2")
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    void userRoleDeleteGenre() {
        given().auth().preemptive()
                .basic("bob", "bob")
                .when()
                .delete("/api/v1/authors/delete/3")
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
