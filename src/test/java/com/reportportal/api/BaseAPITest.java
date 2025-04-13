package com.reportportal.api;

import com.reportportal.utils.ConfigLoader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

/**
 * Базовый класс для API-тестов, содержащий общую конфигурацию и методы для работы с API ReportPortal.
 * Обеспечивает:
 * - Загрузку конфигурационных параметров
 * - Настройку базового URL
 * - Подготовку аутентифицированных запросов
 */
public class BaseAPITest {

    /**
     * Базовый URL API, загружаемый из конфигурационных файлов.
     * Используется для всех API-запросов.
     */
    protected static final String BASE_URL = ConfigLoader.getProperty("api.base.url");

    /**
     * Токен для аутентификации в API.
     * Загружается из конфигурационных файлов.
     */
    protected static final String API_TOKEN = ConfigLoader.getProperty("api.token");

    /**
     * Название проекта в ReportPortal.
     * Загружается из конфигурационных файлов.
     */
    protected static final String PROJECT_NAME = ConfigLoader.getProperty("project.name");

    /**
     * Метод инициализации, выполняемый перед всеми тестами.
     * Устанавливает базовый URL для всех последующих API-запросов.
     */
    @BeforeAll
    public static void setup() {
        // Установка базового URI для всех запросов RestAssured
        RestAssured.baseURI = BASE_URL;
    }

    /**
     * Создает и возвращает подготовленный аутентифицированный HTTP-запрос.
     * Включает:
     * - OAuth2 аутентификацию с использованием API_TOKEN
     * - Установку заголовков Content-Type и Accept в JSON
     * - Подстановку параметра пути projectName
     *
     * @return подготовленный объект RequestSpecification для отправки запросов
     */
    protected static RequestSpecification getAuthenticatedRequest() {
        return RestAssured.given()
                // Установка OAuth2 аутентификации
                .auth().oauth2(API_TOKEN)
                // Установка заголовка Content-Type
                .contentType(ContentType.JSON)
                // Установка заголовка Accept
                .accept(ContentType.JSON)
                // Подстановка параметра пути projectName
                .pathParam("projectName", PROJECT_NAME);
    }
}