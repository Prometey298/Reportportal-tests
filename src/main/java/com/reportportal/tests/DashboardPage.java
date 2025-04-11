package com.reportportal.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

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
        WebElement nextStep = wait.until(ExpectedConditions.elementToBeClickable(nextStepBtn));

        // Скролл к кнопке
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", nextStep);

        // Пауза, чтобы перекрывающий элемент успел исчезнуть (иногда требуется)
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Клик через JavaScript
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", nextStep);
    }


    /**
     * Выбирает фильтр "Task Progress"
     */
    public void selectTaskProgressFilter() {
        wait.until(ExpectedConditions.elementToBeClickable(taskProgressFilter)).click();
    }

    /**
     * Извлекает сгенерированное название виджета из поля "Widget name".
     *
     * @return значение атрибута value в <input> (например, "Task Progress_224")
     */
    public String getWidgetName() {
        WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(widgetNameInput));

        // Берём текст не через getText(), а через getAttribute("value")
        // потому что это <input>, и Selenium хранит значение поля в value.
        return nameInput.getAttribute("value");
    }


    /**
     * Завершает добавление виджета
     */
    public void completeWidgetAdding() {
        wait.until(ExpectedConditions.elementToBeClickable(addWidgetFinalBtn)).click();
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
            WebElement widget = wait.until(ExpectedConditions.visibilityOfElementLocated(widgetByName));
            return widget.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

}