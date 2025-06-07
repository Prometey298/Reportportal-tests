package com.reportportal.ui;

import com.reportportal.pages.DashboardPage;
import com.reportportal.pages.LoginPage;
import com.reportportal.utils.ConfigLoader;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;

/**
 * Тестовый класс для проверки создания виджета в ReportPortal.
 * Наследует базовый функционал из BaseUITest.
 */
@Epic("UI Tests")        // Аннотация Allure для группировки в отчетах
@Feature("Widget Management")  // Аннотация Allure для функциональной группировки
@Story("Create new widget")    // Аннотация Allure для пользовательской истории
public class WidgetCreationTest extends BaseUITest {

    /**
     * Тест добавления нового виджета на дашборд.
     * @throws IOException при проблемах с сохранением скриншота
     */
    @Test
    public void testAddWidgetToDashboard() throws IOException {
        // Загрузка учетных данных из конфигурации
        String username = ConfigLoader.getProperty("default.username");
        String password = ConfigLoader.getProperty("default.password");
        String reportPortalUrl = ConfigLoader.getProperty("reportportal.url");

        try {
            // === 1. АВТОРИЗАЦИЯ ===
            LoginPage loginPage = new LoginPage(driver);
            // Открываем страницу логина
            loginPage.open(reportPortalUrl);
            // Выполняем вход с полученными учетными данными
            loginPage.loginAs(username, password);

            // === 2. ОТКРЫТИЕ ДАШБОРДА ===
            DashboardPage dashboardPage = new DashboardPage(driver);
            // Открываем меню дашбордов
            dashboardPage.openDashboardsMenu();
            // Открываем первый доступный дашборд
            dashboardPage.openFirstDashboard();

            // === 3. СОЗДАНИЕ ВИДЖЕТА ===
            // Нажимаем кнопку добавления нового виджета
            dashboardPage.clickAddNewWidget();
            // Выбираем тип виджета (по умолчанию или из конфига)
            dashboardPage.selectWidgetType();
            // Переходим к следующему шагу
            dashboardPage.clickNextStep();
            // Выбираем фильтр для виджета
            dashboardPage.selectTaskProgressFilter();
            // Подтверждаем выбор
            dashboardPage.clickNextStep();
            // Получаем сгенерированное имя виджета
            String widgetName = dashboardPage.getWidgetName();
            // Завершаем процесс добавления
            dashboardPage.completeWidgetAdding();

            // === 4. ПРОВЕРКА РЕЗУЛЬТАТА ===
            // Проверяем, что виджет появился на дашборде
            boolean isWidgetAdded = dashboardPage.isWidgetPresent(widgetName);
            // Утверждение, которое упадет, если виджет не добавлен
            Assertions.assertTrue(isWidgetAdded, "Widget was not added");

        } catch (Exception e) {
            // === ОБРАБОТКА ОШИБОК ===
            // Делаем скриншот при возникновении исключения
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            // Сохраняем скриншот в папку target/screenshots
            FileUtils.copyFile(screenshot, new File("target/screenshots/error.png"));
            // Пробрасываем исключение дальше (тест будет отмечен как failed)
            throw e;
        }
    }
}