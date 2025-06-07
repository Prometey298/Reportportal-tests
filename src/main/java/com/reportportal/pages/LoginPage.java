package com.reportportal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Page Object класс для страницы авторизации Report Portal.
 */
public class LoginPage {

    // Драйвер для взаимодействия с браузером
    private final WebDriver driver;

    // Локаторы элементов страницы
    private final By usernameField = By.name("login"); // Поле ввода логина
    private final By passwordField = By.name("password"); // Поле ввода пароля
    private final By loginButton = By.cssSelector("button[type='submit']"); // Кнопка входа

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Открывает страницу авторизации по указанному URL.
     *
     * @param url адрес страницы логина
     */
    public void open(String url) {
        driver.get(url); // Переход по указанному URL
    }

    /**
     * Вводит указанное имя пользователя в поле логина.
     * @param username имя пользователя для ввода
     */
    public void enterUsername(String username) {
        WebElement userInput = driver.findElement(usernameField);
        userInput.click(); // Установка фокуса на поле
        userInput.clear(); // Очистка поля
        userInput.sendKeys(username); // Ввод логина
    }

    /**
     * Вводит указанный пароль в соответствующее поле.
     * @param password пароль для ввода
     */
    public void enterPassword(String password) {
        WebElement passInput = driver.findElement(passwordField);
        passInput.clear(); // Очистка поля
        passInput.sendKeys(password); // Ввод пароля
    }

    /**
     * Нажимает кнопку входа в систему.
     */
    public void clickLogin() {
        driver.findElement(loginButton).click(); // Клик по кнопке входа
    }

    /**
     * Выполняет полный процесс авторизации.
     *
     * @param username имя пользователя
     * @param password пароль
     */
    public void loginAs(String username, String password) {
        enterUsername(username); // Ввод логина
        enterPassword(password); // Ввод пароля
        clickLogin(); // Нажатие кнопки входа
    }
}