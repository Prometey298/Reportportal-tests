package com.reportportal.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Класс-страница для взаимодействия с разделом Dashboard в Report Portal.
 * Реализует основные действия: открытие меню, создание нового Dashboard,
 * проверка его появления на странице.
 *
 * Реализован по паттерну Page Object.
 */
public class DashboardPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Локаторы для элементов страницы
    private By addDashboardBtn     = By.xpath("//button[contains(., 'Add New Dashboard')]");
    private By dashboardNameInput  = By.cssSelector("input[placeholder='Enter dashboard name']");
    private By dashboardDescInput  = By.cssSelector("textarea[placeholder='Enter dashboard description']");
    private By addButton           = By.xpath("//button[text()='Add']");

    /**
     * Конструктор страницы Dashboard.
     * Инициализирует драйвер и явное ожидание.
     *
     * @param driver WebDriver, передаваемый из теста
     */
    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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
     * Нажимает кнопку "Add New Dashboard".
     */
    public void clickAddDashboard() {
        wait.until(ExpectedConditions.elementToBeClickable(addDashboardBtn)).click();
    }

    /**
     * Заполняет поля "name" и "description" нового Dashboard.
     *
     * @param name имя дашборда
     * @param desc описание дашборда
     */
    public void fillDashboardFields(String name, String desc) {
        WebElement nameElem = wait.until(ExpectedConditions.visibilityOfElementLocated(dashboardNameInput));
        nameElem.sendKeys(name); // Вводим имя дашборда
        driver.findElement(dashboardDescInput).sendKeys(desc); // Вводим описание
    }

    /**
     * Подтверждает создание Dashboard, нажимая кнопку "Add".
     */
    public void submit() {
        driver.findElement(addButton).click();
    }

    /**
     * Проверяет, отображается ли дашборд с указанным именем на странице.
     *
     * @param name имя дашборда для поиска
     * @return true, если дашборд найден, иначе false
     */
    public boolean dashboardExists(String name) {
        By nameLocator = By.xpath("//span[contains(text(), '" + name + "')]");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(nameLocator)).isDisplayed();
    }
}
