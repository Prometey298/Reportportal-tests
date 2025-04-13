package com.reportportal.pages;

import com.reportportal.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;

public class DashboardPage {
    private final WebDriver driver;

    // Локаторы для элементов страницы
    private final By firstDashboardLink = By.xpath("//a[contains(@class,'dashboardTable__name')]");
    private final By addWidgetBtn = By.xpath("//span[contains(text(),'Add new widget')]");
    private final By widgetTypeSelector = By.xpath("//div[contains(text(),'Launch statistics chart')]");
    private final By nextStepBtn = By.xpath("//span[contains(text(),'Next step')]");
    private final By taskProgressFilter = By.xpath("//span[contains(text(),'Task Progress')]");
    private final By widgetNameInput = By.cssSelector("input[placeholder='Enter widget name']");
    private final By addWidgetFinalBtn = By.xpath("//button[contains(text(),'Add')]");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Открывает раздел "Dashboards" через боковое меню.
     */
    public void openDashboardsMenu() {
        By dashboardMenuLocator = By.cssSelector("a[href*='dashboard']");
        WaitUtils.waitUntilClickable(driver, dashboardMenuLocator, 15);
        WebElement dashboardsIcon = driver.findElement(dashboardMenuLocator);
        dashboardsIcon.click();
    }

    /**
     * Открывает первый дашборд из списка
     */
    public void openFirstDashboard() {
        WaitUtils.waitUntilClickable(driver, firstDashboardLink, 15);
        driver.findElement(firstDashboardLink).click();
    }

    /**
     * Нажимает кнопку добавления нового виджета
     */
    public void clickAddNewWidget() {
        WaitUtils.waitUntilClickable(driver, addWidgetBtn, 15);
        driver.findElement(addWidgetBtn).click();
    }

    /**
     * Выбирает тип виджета "Launch statistics chart"
     */
    public void selectWidgetType() {
        WaitUtils.waitUntilClickable(driver, widgetTypeSelector, 15);
        driver.findElement(widgetTypeSelector).click();
    }

    /**
     * Нажимает кнопку "Next Step"
     */
    public void clickNextStep() {
        WaitUtils.waitUntilClickable(driver, nextStepBtn, 15);
        WebElement nextStep = driver.findElement(nextStepBtn);

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", nextStep);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", nextStep);
    }

    /**
     * Выбирает фильтр "Task Progress"
     */
    public void selectTaskProgressFilter() {
        WaitUtils.waitUntilClickable(driver, taskProgressFilter, 15);
        driver.findElement(taskProgressFilter).click();

        // Прокручиваем к кнопке Next Step (даже если она еще не видна)
        WebElement nextStep = driver.findElement(nextStepBtn);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});",
                nextStep
        );
    }

    /**
     * Извлекает сгенерированное название виджета из поля "Widget name".
     *
     * @return значение атрибута value в <input>
     */
    public String getWidgetName() {
        WaitUtils.waitUntilVisible(driver, widgetNameInput, 15);
        WebElement nameInput = driver.findElement(widgetNameInput);
        return nameInput.getAttribute("value");
    }

    /**
     * Завершает добавление виджета
     */
    public void completeWidgetAdding() {
        WaitUtils.waitUntilClickable(driver, addWidgetFinalBtn, 15);
        driver.findElement(addWidgetFinalBtn).click();
    }

    /**
     * Проверяет наличие виджета с конкретным названием на странице
     *
     * @param widgetName имя виджета, которое должно быть на странице
     * @return true, если виджет с таким именем найден
     */
    public boolean isWidgetPresent(String widgetName) {
        By widgetByName = By.xpath("//div[contains(text(),'" + widgetName + "')]");
        try {
            WaitUtils.waitUntilVisible(driver, widgetByName, 15);
            WebElement widget = driver.findElement(widgetByName);
            return widget.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}