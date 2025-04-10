package com.reportportal.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Локаторы для элементов страницы
    private By firstDashboardLink = By.xpath("//a[contains(@class,'dashboardTable__name')]");
    private By addWidgetBtn = By.xpath("//span[contains(text(),'Add new widget')]");
    private By widgetTypeSelector = By.xpath("//div[contains(text(),'Launch statistics chart')]");
    private By nextStepBtn = By.xpath("//span[contains(text(),'Next Step')]");
    private By taskProgressFilter = By.xpath("//div[contains(text(),'Task Progress')]");
    private By addWidgetFinalBtn = By.xpath("//span[contains(text(),'Add')]");
    private By widgetContainer = By.cssSelector(".widgetView__widget-container");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    /**
     * Открывает раздел "Dashboards" через боковое меню.
     */
    public void openDashboardsMenu() {
        WebElement dashboardsIcon = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a[href*='dashboard']"))); // Иконка "Dashboards"
        dashboardsIcon.click();
    }

    /**
     * Открывает первый дашборд из списка
     */
    public void openFirstDashboard() {
        wait.until(ExpectedConditions.elementToBeClickable(firstDashboardLink)).click();
    }

    /**
     * Нажимает кнопку добавления нового виджета
     */
    public void clickAddNewWidget() {
        wait.until(ExpectedConditions.elementToBeClickable(addWidgetBtn)).click();
    }

    /**
     * Выбирает тип виджета "Launch statistics chart"
     */
    public void selectWidgetType() {
        wait.until(ExpectedConditions.elementToBeClickable(widgetTypeSelector)).click();
    }

    /**
     * Нажимает кнопку "Next Step"
     */
    public void clickNextStep() {
        wait.until(ExpectedConditions.elementToBeClickable(nextStepBtn)).click();
    }

    /**
     * Выбирает фильтр "Task Progress"
     */
    public void selectTaskProgressFilter() {
        wait.until(ExpectedConditions.elementToBeClickable(taskProgressFilter)).click();
    }

    /**
     * Завершает добавление виджета
     */
    public void completeWidgetAdding() {
        wait.until(ExpectedConditions.elementToBeClickable(addWidgetFinalBtn)).click();
    }

    /**
     * Проверяет наличие виджета на странице
     */
    public boolean isWidgetPresent() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(widgetContainer)).isDisplayed();
    }
}