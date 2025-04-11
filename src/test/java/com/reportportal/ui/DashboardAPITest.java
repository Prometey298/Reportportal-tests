package com.reportportal.ui;

import com.reportportal.tests.utils.ConfigLoader;
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

/**
 * API тест для проверки создания дашборда в Report Portal.
 *
 * Используется:
 * - RestAssured для отправки запросов;
 * - Allure аннотации для отчётности;
 * - JUnit 5 для написания теста.
 */
@Epic("Report Portal API Tests")        // Глобальный раздел в Allure
@Feature("Dashboard Management")        // Фича — управление дашбордами
public class DashboardAPITest {

    // Базовый URL API
    private static final String BASE_URL = ConfigLoader.getProperty("api.base.url");


    // 🔑 API токен, полученный из профиля проекта (должен быть актуальным!)
    private static final String API_TOKEN = ConfigLoader.getProperty("api.token");

    // Имя проекта, в котором создаётся Dashboard
    private static final String PROJECT_NAME = ConfigLoader.getProperty("project.name");

    /**
     * Устанавливает базовый URL до выполнения всех тестов.
     */
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    /**
     * Тест проверяет возможность создать дашборд через API,
     * а затем убедиться, что он присутствует в списке дашбордов.
     */
    @Test
    @Story("Create New Dashboard")                      // История в Allure отчете
    @DisplayName("Create new dashboard with valid data") // Название теста в Allure
    void testCreateDashboard() {
        // Уникальное имя дашборда
        String dashboardName = "TestDashboard_" + System.currentTimeMillis();

        // Тело POST-запроса
        String requestBody = String.format("""
        {
            "description": "API Test Dashboard",
            "name": "%s"
        }
        """, dashboardName);

        // === Шаг 1: Создание Dashboard через POST ===
        Response response = given()
                .auth().oauth2(API_TOKEN)                          // Авторизация через Bearer токен
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam("projectName", PROJECT_NAME)
                .body(requestBody)
                .when()
                .post("/{projectName}/dashboard")                 // Endpoint: /v1/{projectName}/dashboard
                .then()
                .log().status()                                   // Лог статуса ответа
                .log().body()                                     // Лог тела ответа
                .statusCode(201)                                  // Проверка, что дашборд создан
                .body("id", notNullValue())                       // Проверка, что вернулся ID
                .extract().response();

        // Сохраняем ID нового дашборда (не обязательно, но полезно)
        int dashboardId = response.path("id");
        System.out.println("✅ Created dashboard ID: " + dashboardId);

        // === Шаг 2: Проверка, что дашборд существует в списке (GET) ===
        given()
                .auth().oauth2(API_TOKEN)
                .pathParam("projectName", PROJECT_NAME)
                .when()
                .get("/{projectName}/dashboard")                  // Получаем список всех дашбордов
                .then()
                .log().status()
                .statusCode(200)
                .body("content.name", hasItem(dashboardName));    // Проверка, что наш дашборд есть в списке
    }
}
