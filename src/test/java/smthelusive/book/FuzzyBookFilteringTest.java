package smthelusive.book;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import smthelusive.GenericTestSetup;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

@QuarkusTest
public class FuzzyBookFilteringTest extends GenericTestSetup {
    @Test
    void filterAuthorViewBooks() {
        given().auth().preemptive()
                .basic(USER_ROLE_USER, USER_ROLE_PASSWORD)
                .when()
                .get(BOOK_ENDPOINT + "?author=arthur")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("size()", is(1));
    }

    @Test
    void filterGenreViewBooks() {
        given().auth().preemptive()
                .basic(USER_ROLE_USER, USER_ROLE_PASSWORD)
                .when()
                .get(BOOK_ENDPOINT + "?genre=detective")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("size()", is(1));
    }

    @Test
    void filterTitleViewBooks() {
        given().auth().preemptive()
                .basic(USER_ROLE_USER, USER_ROLE_PASSWORD)
                .when()
                .get(BOOK_ENDPOINT + "?title=sherl")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("size()", is(1));
    }

    @Test
    void filterAllViewBooks() {
        given().auth().preemptive()
                .basic(USER_ROLE_USER, USER_ROLE_PASSWORD)
                .when()
                .get(BOOK_ENDPOINT + "?title=sherlock&genre=detective&author=konan")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("size()", is(1));
    }
}
