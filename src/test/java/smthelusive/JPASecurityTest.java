package smthelusive;
import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class JPASecurityTest {

    @Test
    void shouldAccessPublicWhenAnonymous() {
        given()
                .auth().preemptive().basic("alice", "super-password")
                .when()
                .get("/api/v1/books")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
}
