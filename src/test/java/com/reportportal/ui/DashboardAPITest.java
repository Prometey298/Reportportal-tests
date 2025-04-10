package com.reportportal.ui;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Epic("Report Portal API Tests")
@Feature("Dashboard Management")
public class DashboardAPITest {

    private static final String BASE_URL = "https://demo.reportportal.io/api/v1";
    private static final String API_TOKEN = "vkfkvkofobdf_Du3wsQAESe250_qldzZ28oHz5e1D82qsntEJefZqmREssRvn612SYTv4x0x2-XTX";
    private static final String PROJECT_NAME = "default_personal";

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @Story("Create New Dashboard")
    @DisplayName("Create new dashboard with valid data")
    void testCreateDashboard() {
        String dashboardName = "TestDashboard_" + System.currentTimeMillis();

        String requestBody = String.format("""
        {
            "description": "API Test Dashboard",
            "name": "%s"
        }
        """, dashboardName);

        // Отправка POST с логированием
        Response response = given()
                .auth().oauth2(API_TOKEN)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam("projectName", PROJECT_NAME)
                .body(requestBody)
                .when()
                .post("/{projectName}/dashboard")
                .then()
                .log().status()  // Логирование статуса
                .log().body()    // Логирование тела ответа
                .statusCode(201)
                .body("id", notNullValue())
                .extract().response();

        int dashboardId = response.path("id");
        System.out.println("Created dashboard ID: " + dashboardId);  // Дополнительный вывод ID

        // Проверка через GET с логированием
        given()
                .auth().oauth2(API_TOKEN)
                .pathParam("projectName", PROJECT_NAME)
                .when()
                .get("/{projectName}/dashboard")
                .then()
                .log().status()  // Логирование статуса
                .statusCode(200)
                .body("content.name", hasItem(dashboardName));
    }
}