package com.reportportal.ui;

import com.reportportal.tests.DashboardPage;
import com.reportportal.tests.LoginPage;
import com.reportportal.tests.utils.ConfigLoader;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;

public class WidgetCreationTest extends BaseTest {

    @Test
    public void testAddWidgetToDashboard() throws IOException {
        // Load settings
        String username = ConfigLoader.getProperty("default.username");
        String password = ConfigLoader.getProperty("default.password");
        String reportPortalUrl = ConfigLoader.getProperty("reportportal.url");

        try {
            // 1. Authorization
            LoginPage loginPage = new LoginPage(driver);
            loginPage.open(reportPortalUrl);
            loginPage.loginAs(username, password);

            // 2. Open first dashboard
            DashboardPage dashboardPage = new DashboardPage(driver);
            dashboardPage.openDashboardsMenu();
            dashboardPage.openFirstDashboard();

            // 3. Add new widget
            dashboardPage.clickAddNewWidget();
            dashboardPage.selectWidgetType();
            dashboardPage.clickNextStep();
            dashboardPage.selectTaskProgressFilter();
            dashboardPage.clickNextStep();
            String widgetName = dashboardPage.getWidgetName();
            dashboardPage.completeWidgetAdding();

            // 4. Verify widget is added
            boolean isWidgetAdded = dashboardPage.isWidgetPresent(widgetName);
            Assertions.assertTrue(isWidgetAdded, "Widget was not added");
        } catch (Exception e) {
            // Take screenshot on failure
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File("target/screenshots/error.png"));
            throw e;
        }
    }
}