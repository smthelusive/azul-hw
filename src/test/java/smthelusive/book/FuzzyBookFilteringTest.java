package smthelusive.book;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

@QuarkusTest
public class FuzzyBookFilteringTest {
    @Test
    void filterAuthorViewBooks() {
        given().auth().preemptive()
                .basic("mary", "mary")
                .when()
                .get("/api/v1/books?author=arthur")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("size()", is(1));
    }

    @Test
    void filterGenreViewBooks() {
        given().auth().preemptive()
                .basic("mary", "mary")
                .when()
                .get("/api/v1/books?genre=detective")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("size()", is(1));
    }

    @Test
    void filterTitleViewBooks() {
        given().auth().preemptive()
                .basic("mary", "mary")
                .when()
                .get("/api/v1/books?title=sherl")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("size()", is(1));
    }

    @Test
    void filterAllViewBooks() {
        given().auth().preemptive()
                .basic("mary", "mary")
                .when()
                .get("/api/v1/books?title=sherlock&genre=detective&author=konan")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("size()", is(1));
    }
}
