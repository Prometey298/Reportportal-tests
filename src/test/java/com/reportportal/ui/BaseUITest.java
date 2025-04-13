package com.reportportal.ui;

import com.reportportal.utils.ConfigLoader;
import com.reportportal.utils.WebDriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Базовый класс для всех UI-тестов.
 * Содержит общую логику инициализации и завершения работы WebDriver.
 */
public class BaseUITest {
    // Логгер для записи событий
    private static final Logger logger = LoggerFactory.getLogger(BaseUITest.class);

    // Экземпляр WebDriver, доступный для всех наследующих классов
    protected WebDriver driver;

    /**
     * Метод выполняется перед каждым тестом.
     * Инициализирует WebDriver в зависимости от настроек.
     */
    @BeforeEach
    public void setUp() {
        // Получаем тип браузера из конфигурации
        String browser = ConfigLoader.getProperty("browser");

        // Создаем экземпляр WebDriver через фабрику
        driver = WebDriverFactory.createDriver(browser);

        // Максимизируем окно браузера
        driver.manage().window().maximize();

        // Специальная проверка для Yandex Browser
        if ("yandex".equalsIgnoreCase(browser)) {
            try {
                // Проверяем наличие Yandex Browser по указанному пути
                String yandexPath = getYandexBrowserPath();
                Assertions.assertTrue(
                        new File(yandexPath).exists(),
                        "Yandex Browser not found at specified path: " + yandexPath
                );
            } catch (Exception e) {
                // Логируем ошибку и перебрасываем исключение
                logger.error("Error during Yandex Browser setup", e);
                throw e;
            }
        }
    }

    /**
     * Метод выполняется после каждого теста.
     * Закрывает WebDriver и освобождает ресурсы.
     */
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            try {
                // Закрываем браузер
                driver.quit();
            } catch (Exception e) {
                // Логируем ошибку, если не удалось закрыть драйвер
                logger.error("Error during driver quit", e);
            }
        }
    }

    /**
     * Возвращает путь к исполняемому файлу Yandex Browser в зависимости от ОС.
     * @return Путь к Yandex Browser
     * @throws RuntimeException Если путь не найден для текущей ОС
     */
    private String getYandexBrowserPath() {
        // Определяем текущую операционную систему
        String os = System.getProperty("os.name").toLowerCase();
        String key = "yandex.browser.path";

        // Выбираем правильный ключ конфигурации в зависимости от ОС
        if (os.contains("win")) {
            // Windows путь
            key += ".windows";
        } else if (os.contains("mac")) {
            // MacOS путь
            key += ".mac";
        } else if (os.contains("nux") || os.contains("nix") || os.contains("aix")) {
            // Linux/Unix путь
            key += ".linux";
        }

        // Получаем путь из конфигурации
        String path = ConfigLoader.getProperty(key);

        // Проверяем, что путь существует
        if (path == null || path.isEmpty()) {
            throw new RuntimeException("Yandex Browser path not found for OS: " + os);
        }

        return path;
    }
}