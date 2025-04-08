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

public class LoginTest {

    @Test
    public void loginAndCreateDashboardTest() {
        System.setProperty("webdriver.chrome.driver", "D:\\Users\\Dima\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--incognito");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // 1. Логин
            LoginPage loginPage = new LoginPage(driver);
            loginPage.open();
            loginPage.loginAs("default", "1q2w3e");

            // 2. Переходим к дашбордам
            DashboardPage dashboardPage = new DashboardPage(driver);
            dashboardPage.openDashboardsMenu();

            // 3. Создаём дашборд
            dashboardPage.clickAddDashboard();
            dashboardPage.fillDashboardFields("My First Dashboard", "Created by Selenium");
            dashboardPage.submit();

            // 4. Проверка
            boolean isCreated = dashboardPage.dashboardExists("My First Dashboard");
            Assertions.assertTrue(isCreated, "Dashboard не появился в списке!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}