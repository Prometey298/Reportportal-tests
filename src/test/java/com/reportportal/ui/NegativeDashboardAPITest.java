package com.reportportal.ui;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Epic("Report Portal API Tests")
@Feature("Dashboard Management")
public class NegativeDashboardAPITest {

    private static final String BASE_URL = "https://demo.reportportal.io/api/v1";
    private static final String API_TOKEN = "vkfkvkofobdf_Du3wsQAESe250_qldzZ28oHz5e1D82qsntEJefZqmREssRvn612SYTv4x0x2-XTX";
    private static final String PROJECT_NAME = "default_personal";

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @Story("Create Dashboard with Invalid Data")
    @DisplayName("Create dashboard without required 'name' parameter")
    void testCreateDashboardWithoutName() {
        String invalidRequestBody = """
        {
            "description": "Negative Test Dashboard"
        }
        """;

        // POST запрос с логированием
        given()
                .auth().oauth2(API_TOKEN)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam("projectName", PROJECT_NAME)
                .body(invalidRequestBody)
                .when()
                .post("/{projectName}/dashboard")
                .then()
                .log().status()    // Логирование статуса
                .log().body()      // Логирование тела ответа
                .statusCode(400)
                .body("message", containsString("Field 'name' should not be null"));

        // GET проверка с логированием
        given()
                .auth().oauth2(API_TOKEN)
                .pathParam("projectName", PROJECT_NAME)
                .when()
                .get("/{projectName}/dashboard")
                .then()
                .log().status()    // Логирование статуса
                .statusCode(200)
                .body("content.description", not(hasItem("Negative Test Dashboard")));
    }
}