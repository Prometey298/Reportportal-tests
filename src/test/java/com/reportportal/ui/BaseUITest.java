package com.reportportal.ui;

import com.reportportal.utils.ConfigLoader;
import com.reportportal.utils.WebDriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class BaseUITest {
    protected WebDriver driver;

    @BeforeEach
    public void setUp() {
        String browser = ConfigLoader.getProperty("browser");
        driver = WebDriverFactory.createDriver(browser);
        driver.manage().window().maximize();

        // Check browser path if Yandex is used
        if ("yandex".equalsIgnoreCase(browser)) {
            // Получаем путь для Yandex Browser в зависимости от ОС
            String yandexPath = getYandexBrowserPath();
            Assertions.assertTrue(
                    new File(yandexPath).exists(),
                    "Yandex Browser not found at specified path: " + yandexPath
            );
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.err.println("Error during driver quit: " + e.getMessage());
            }
        }
    }

    // Новый метод для получения пути к Yandex Browser в зависимости от ОС
    private String getYandexBrowserPath() {
        String os = System.getProperty("os.name").toLowerCase();
        String key = "yandex.browser.path";

        // Если ОС Windows, получаем путь для Windows
        if (os.contains("win")) {
            key += ".windows";
        }
        // Если ОС Mac, получаем путь для MacOS
        else if (os.contains("mac")) {
            key += ".mac";
        }
        // Для Linux и других систем
        else if (os.contains("nux") || os.contains("nix") || os.contains("aix")) {
            key += ".linux";
        }

        // Получаем путь по новому ключу
        String path = ConfigLoader.getProperty(key);
        if (path == null || path.isEmpty()) {
            throw new RuntimeException("Yandex Browser path not found for OS: " + os);
        }

        return path;
    }
}
