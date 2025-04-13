package com.reportportal.api;

import com.reportportal.utils.ConfigLoader;
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
 * API тесты для работы с дашбордами в Report Portal.
 * Наследует базовую конфигурацию из BaseAPITest.
 */
@Epic("Report Portal API Tests")        // Аннотация Allure для группировки тестов в отчете
@Feature("Dashboard Management")        // Аннотация Allure для обозначения тестируемой функциональности
public class DashboardAPITest extends BaseAPITest {

    /**
     * Тест создания нового дашборда и проверки его наличия в списке.
     *
     * Шаги теста:
     * 1. Создание дашборда через POST-запрос
     * 2. Проверка успешного создания (код ответа 201)
     * 3. Проверка наличия созданного дашборда в общем списке
     */
    @Test
    @Story("Create New Dashboard")                      // Аннотация Allure для User Story
    @DisplayName("Create new dashboard with valid data") // Аннотация для читаемого имени теста
    void testCreateDashboard() {
        // Генерация уникального имени дашборда с использованием текущего времени
        String dashboardName = "TestDashboard_" + System.currentTimeMillis();

        // Формирование тела запроса в формате JSON
        String requestBody = String.format("""
        {
            "description": "API Test Dashboard",
            "name": "%s"
        }
        """, dashboardName);

        // === ШАГ 1: СОЗДАНИЕ ДАШБОРДА ===
        Response response = getAuthenticatedRequest()
                .body(requestBody)                      // Установка тела запроса
                .when()
                .post("/{projectName}/dashboard")       // Отправка POST-запроса
                .then()
                .log().status()                        // Логирование статуса ответа
                .log().body()                          // Логирование тела ответа
                .statusCode(201)                       // Проверка кода ответа (201 Created)
                .body("id", notNullValue())            // Проверка наличия ID в ответе
                .extract().response();                 // Извлечение полного ответа

        // Получение ID созданного дашборда из ответа
        int dashboardId = response.path("id");
        System.out.println("✅ Created dashboard ID: " + dashboardId);

        // === ШАГ 2: ПРОВЕРКА НАЛИЧИЯ ДАШБОРДА В СПИСКЕ ===
        given()
                .auth().oauth2(API_TOKEN)              // Установка авторизации
                .pathParam("projectName", PROJECT_NAME) // Подстановка имени проекта
                .when()
                .get("/{projectName}/dashboard")       // Отправка GET-запроса
                .then()
                .log().status()                        // Логирование статуса
                .statusCode(200)                       // Проверка кода ответа (200 OK)
                .body("content.name", hasItem(dashboardName)); // Проверка наличия имени в списке
    }
}