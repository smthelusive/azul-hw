package smthelusive.book;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import smthelusive.GenericTestSetup;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class AdminBookTest extends GenericTestSetup {
    @Test
    void adminRoleViewBooks() {
        given().auth().preemptive()
                .basic(ADMIN_ROLE_USER, ADMIN_ROLE_PASSWORD)
                .when()
                .get(BOOK_ENDPOINT)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void adminRoleGetSingleBook() {
        given().auth().preemptive()
                .basic(ADMIN_ROLE_USER, ADMIN_ROLE_PASSWORD)
                .when()
                .get(BOOK_ENDPOINT_ID, 1)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("title", is("Shining"))
                .body("annotation", is("Scary stuff"));
    }

    @Test
    void adminRoleCreateBook() {
        given().auth().preemptive()
                .basic(ADMIN_ROLE_USER, ADMIN_ROLE_PASSWORD)
                .when()
                .header("Content-Type","application/json" )
                .header("Accept","application/json" )
                .body(new File("src/test/resources/book_payload.json"))
                .post(BOOK_ENDPOINT)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("title", equalTo("NewBook"))
                .body("count", is(7))
                .body("price", is(17.0F));
    }

    @Test
    void adminRoleUpdateBook() {
        given().auth().preemptive()
                .basic(ADMIN_ROLE_USER, ADMIN_ROLE_PASSWORD)
                .when()
                .header("Content-Type","application/json" )
                .header("Accept","application/json" )
                .body(new File("src/test/resources/book_payload.json"))
                .put(BOOK_ENDPOINT_ID, 2)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("title", equalTo("NewBook"))
                .body("count", is(7))
                .body("price", is(17.0F));
    }

    @Test
    void adminRoleDeleteBook() {
        given().auth().preemptive()
                .basic(ADMIN_ROLE_USER, ADMIN_ROLE_PASSWORD)
                .when()
                .delete(BOOK_ENDPOINT_ID, 3)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
}
