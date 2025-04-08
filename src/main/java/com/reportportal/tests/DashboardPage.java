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

    private By addDashboardBtn = By.xpath("//button[contains(., 'Add New Dashboard')]");
    private By dashboardNameInput = By.cssSelector("input[placeholder='Enter dashboard name']");
    private By dashboardDescInput = By.cssSelector("textarea[placeholder='Enter dashboard description']");
    private By addButton = By.xpath("//button[text()='Add']");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openDashboardsMenu() {
        WebElement dashboardsIcon = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a[href*='dashboard']")));
        dashboardsIcon.click();
    }

    public void clickAddDashboard() {
        wait.until(ExpectedConditions.elementToBeClickable(addDashboardBtn)).click();
    }

    public void fillDashboardFields(String name, String desc) {
        WebElement nameElem = wait.until(ExpectedConditions.visibilityOfElementLocated(dashboardNameInput));
        nameElem.sendKeys(name);
        driver.findElement(dashboardDescInput).sendKeys(desc);
    }

    public void submit() {
        driver.findElement(addButton).click();
    }

    public boolean dashboardExists(String name) {
        By nameLocator = By.xpath("//span[contains(text(), '" + name + "')]");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(nameLocator)).isDisplayed();
    }
}