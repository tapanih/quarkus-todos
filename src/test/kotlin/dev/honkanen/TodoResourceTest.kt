package dev.honkanen

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.junit.jupiter.api.Test

@QuarkusTest
class TodoResourceTest {

    @Test
    fun `get todos`() {
        given()
            .`when`().get("/todos")
            .then()
            .statusCode(200)
    }

    @Test
    fun `add todo`() {
        given()
            .body("title=Do the dishes")
            .header("Content-Type", "application/x-www-form-urlencoded")
            .`when`().post("/todos")
            .then()
            .statusCode(200)
    }
}