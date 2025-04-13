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
 * Негативный API тест, проверяющий обработку ошибок при создании дашбордов.
 * Наследует базовую конфигурацию из BaseAPITest.
 */
@Epic("Report Portal API Tests")        // Аннотация Allure для группировки тестов в отчете
@Feature("Dashboard Management")        // Аннотация Allure для обозначения тестируемой функциональности
public class NegativeDashboardAPITest extends BaseAPITest {

    /**
     * Тест проверяет корректность обработки ошибки при попытке создать дашборд
     * без обязательного параметра "name".
     *
     * Тест выполняет следующие проверки:
     * 1. Отправляет POST-запрос с невалидным телом (без поля "name")
     * 2. Проверяет, что API возвращает:
     *    - HTTP статус 400 (Bad Request)
     *    - Сообщение об ошибке с указанием на отсутствие обязательного поля
     * 3. Дополнительно проверяет через GET-запрос, что дашборд не был создан
     */
    @Test
    @Story("Create Dashboard with Invalid Data")       // Аннотация Allure для User Story
    @DisplayName("Create dashboard without required 'name' parameter") // Читаемое название теста
    void testCreateDashboardWithoutName() {
        // Тело запроса без обязательного поля "name"
        String invalidRequestBody = """
        {
            "description": "Negative Test Dashboard"
        }
        """;

        // === ШАГ 1: ОТПРАВКА НЕВАЛИДНОГО ЗАПРОСА ===
        getAuthenticatedRequest()
                .body(invalidRequestBody)              // Установка невалидного тела запроса
                .when()
                .post("/{projectName}/dashboard")      // Отправка POST-запроса
                .then()
                .log().status()                        // Логирование статуса ответа
                .log().body()                         // Логирование тела ответа
                .statusCode(400)                       // Проверка кода ответа (400 Bad Request)
                .body("message", containsString("Field 'name' should not be null")); // Проверка сообщения об ошибке

        // === ШАГ 2: ПРОВЕРКА ОТСУТСТВИЯ ДАШБОРДА В СПИСКЕ ===
        given()
                .auth().oauth2(API_TOKEN)            // Установка авторизации
                .pathParam("projectName", PROJECT_NAME) // Подстановка имени проекта
                .when()
                .get("/{projectName}/dashboard")      // Отправка GET-запроса
                .then()
                .log().status()                       // Логирование статуса
                .statusCode(200)                      // Проверка кода ответа (200 OK)
                .body("content.description", not(hasItem("Negative Test Dashboard"))); // Проверка отсутствия записи
    }
}