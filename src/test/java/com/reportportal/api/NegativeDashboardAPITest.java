package com.reportportal.api;

import com.reportportal.utils.ConfigLoader;
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

/**
 * Негативный API тест, проверяющий, что дашборд не может быть создан без обязательного поля "name".
 *
 * Тест выполняет:
 * - POST запрос с некорректным телом (без "name");
 * - проверку, что API возвращает статус 400 и ожидаемое сообщение об ошибке;
 * - GET-запрос, подтверждающий, что дашборд не был создан.
 *
 * Используется:
 * - RestAssured для HTTP-запросов;
 * - Allure для отчётности;
 * - JUnit 5 для структуры тестов.
 */
@Epic("Report Portal API Tests")
@Feature("Dashboard Management")
public class NegativeDashboardAPITest {

    // Базовый URL API
    private static final String BASE_URL = ConfigLoader.getProperty("api.base.url");

    // 🔑 API токен, полученный из профиля проекта (должен быть актуальным!)
    private static final String API_TOKEN = ConfigLoader.getProperty("api.token");

    private static final String PROJECT_NAME = ConfigLoader.getProperty("project.name");

    /**
     * Установка базового URL до всех тестов.
     */
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    /**
     * Негативный тест: попытка создать дашборд без поля "name".
     * Ожидаем статус 400 и сообщение об ошибке.
     */
    @Test
    @Story("Create Dashboard with Invalid Data")
    @DisplayName("Create dashboard without required 'name' parameter")
    void testCreateDashboardWithoutName() {
        // JSON тело без поля "name"
        String invalidRequestBody = """
        {
            "description": "Negative Test Dashboard"
        }
        """;

        // === Шаг 1: Отправка POST-запроса ===
        given()
                .auth().oauth2(API_TOKEN)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam("projectName", PROJECT_NAME)
                .body(invalidRequestBody)
                .when()
                .post("/{projectName}/dashboard")
                .then()
                .log().status()  // лог статуса
                .log().body()    // лог тела ответа
                .statusCode(400) // ожидаем ошибку 400 Bad Request
                .body("message", containsString("Field 'name' should not be null")); // проверка сообщения

        // === Шаг 2: Убедимся, что дашборд не создался ===
        given()
                .auth().oauth2(API_TOKEN)
                .pathParam("projectName", PROJECT_NAME)
                .when()
                .get("/{projectName}/dashboard")
                .then()
                .log().status()
                .statusCode(200)
                .body("content.description", not(hasItem("Negative Test Dashboard"))); // убеждаемся, что нет такого описания
    }
}
