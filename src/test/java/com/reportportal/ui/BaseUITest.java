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
            String yandexPath = ConfigLoader.getProperty("yandex.browser.path");
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
}