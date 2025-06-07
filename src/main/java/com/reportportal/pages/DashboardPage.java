package com.reportportal.pages;

import com.reportportal.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;

public class DashboardPage {
    private final WebDriver driver;

    // Локаторы элементов страницы дашбордов
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
     * Открывает раздел дашбордов через боковое меню.
     */
    public void openDashboardsMenu() {
        By dashboardMenuLocator = By.cssSelector("a[href*='dashboard']");
        WaitUtils.waitUntilClickable(driver, dashboardMenuLocator, 15);
        WebElement dashboardsIcon = driver.findElement(dashboardMenuLocator);
        dashboardsIcon.click();
    }

    /**
     * Открывает первый дашборд из списка.
     */
    public void openFirstDashboard() {
        WaitUtils.waitUntilClickable(driver, firstDashboardLink, 15);
        driver.findElement(firstDashboardLink).click();
    }

    /**
     * Нажимает кнопку добавления нового виджета.
     */
    public void clickAddNewWidget() {
        WaitUtils.waitUntilClickable(driver, addWidgetBtn, 15);
        driver.findElement(addWidgetBtn).click();
    }

    /**
     * Выбирает тип виджета "Launch statistics chart".
     */
    public void selectWidgetType() {
        WaitUtils.waitUntilClickable(driver, widgetTypeSelector, 15);
        driver.findElement(widgetTypeSelector).click();
    }

    /**
     * Нажимает кнопку "Next Step" с дополнительными проверками.
     * Выполняет прокрутку к элементу и клик через JavaScript при необходимости.
     */
    public void clickNextStep() {
        WaitUtils.waitUntilClickable(driver, nextStepBtn, 15);
        WebElement nextStep = driver.findElement(nextStepBtn);

        // Прокрутка к элементу
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", nextStep);

        // Дополнительное ожидание после прокрутки
        WaitUtils.waitUntilClickable(driver, nextStepBtn, 15);

        // Клик через JavaScript для избежания проблем с перекрытием элементов
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", nextStep);
    }

    /**
     * Выбирает фильтр "Task Progress".
     * После выбора фильтра прокручивает страницу к кнопке Next Step.
     */
    public void selectTaskProgressFilter() {
        WaitUtils.waitUntilClickable(driver, taskProgressFilter, 15);
        driver.findElement(taskProgressFilter).click();

        // Плавная прокрутка к кнопке Next Step
        WebElement nextStep = driver.findElement(nextStepBtn);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});",
                nextStep
        );
    }

    /**
     * Получает автоматически сгенерированное название виджета.
     *
     * @return значение поля ввода названия виджета
     */
    public String getWidgetName() {
        WaitUtils.waitUntilVisible(driver, widgetNameInput, 15);
        WebElement nameInput = driver.findElement(widgetNameInput);
        return nameInput.getAttribute("value");
    }

    /**
     * Завершает процесс добавления виджета.
     * Ожидает кликабельности кнопки добавления перед взаимодействием.
     */
    public void completeWidgetAdding() {
        WaitUtils.waitUntilClickable(driver, addWidgetFinalBtn, 15);
        driver.findElement(addWidgetFinalBtn).click();
    }

    /**
     * Проверяет наличие виджета с указанным именем на странице.
     *
     * @param widgetName имя виджета для поиска
     * @return true если виджет отображается, false в противном случае
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