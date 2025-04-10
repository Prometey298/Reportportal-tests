package com.reportportal.ui;

import com.reportportal.tests.DashboardPage;
import com.reportportal.tests.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * UI тест, проверяющий:
 * 1. Авторизацию в Report Portal.
 * 2. Переход в раздел Dashboards.
 * 3. Создание нового Dashboard.
 * 4. Проверку его наличия в списке.
 *
 * Использует Page Object классы: LoginPage и DashboardPage.
 */
public class LoginTest {

    @Test
    public void loginAndCreateDashboardTest() {
        // Указываем путь к ChromeDriver (проверь, что он актуален)
        System.setProperty("webdriver.chrome.driver", "D:\\Users\\Dima\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        // Настройки браузера
        ChromeOptions options = new ChromeOptions();

        // Отключаем предложения сохранить пароль и другие автоматизации
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        // Опции браузера для тестирования
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--incognito"); // режим инкогнито (чистый профиль)

        // Инициализация WebDriver и WebDriverWait
        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // === 1. Авторизация ===
            LoginPage loginPage = new LoginPage(driver);
            loginPage.open(); // открываем Report Portal
            loginPage.loginAs("default", "1q2w3e"); // логинимся

            // === 2. Открытие раздела Dashboards ===
            DashboardPage dashboardPage = new DashboardPage(driver);
            dashboardPage.openDashboardsMenu();

            // === 3. Создание нового Dashboard ===
            String dashboardName = "My First Dashboard";
            dashboardPage.clickAddDashboard();
            dashboardPage.fillDashboardFields(dashboardName, "Created by Selenium");
            dashboardPage.submit();

            // === 4. Проверка наличия Dashboard ===
            boolean isCreated = dashboardPage.dashboardExists(dashboardName);
            Assertions.assertTrue(isCreated, "Dashboard не появился в списке!");

        } catch (Exception e) {
            // В случае любой ошибки тест упадет и покажет исключение
            throw new RuntimeException(e);
        } finally {
            // Важно: закрываем браузер, даже если тест упал
            driver.quit();
        }
    }
}
