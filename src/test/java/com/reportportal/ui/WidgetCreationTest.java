package com.reportportal.ui;

import com.reportportal.tests.DashboardPage;
import com.reportportal.tests.LoginPage;
import com.reportportal.tests.utils.ConfigLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public class WidgetCreationTest {

    @Test
    public void testAddWidgetToDashboard() {
        // Загружаем настройки
        String username = ConfigLoader.getProperty("default.username");
        String password = ConfigLoader.getProperty("default.password");

        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--incognito");

        WebDriver driver = new ChromeDriver(options);

        try {
            // 1. Авторизация
            LoginPage loginPage = new LoginPage(driver);
            loginPage.open(ConfigLoader.getProperty("reportportal.url"));
            loginPage.loginAs(username, password);

            // 2. Открытие первого дашборда
            DashboardPage dashboardPage = new DashboardPage(driver);
            dashboardPage.openDashboardsMenu();
            dashboardPage.openFirstDashboard();

            // 3. Добавление нового виджета
            dashboardPage.clickAddNewWidget();
            dashboardPage.selectWidgetType();
            dashboardPage.clickNextStep();
            dashboardPage.selectTaskProgressFilter();
            dashboardPage.clickNextStep();
            String widgetName = dashboardPage.getWidgetName();
            dashboardPage.completeWidgetAdding();

            // 4. Проверка наличия виджета
            boolean isWidgetAdded = dashboardPage.isWidgetPresent(widgetName);
            Assertions.assertTrue(isWidgetAdded, "Виджет не был добавлен");
        } finally {
            driver.quit();
        }
    }
}