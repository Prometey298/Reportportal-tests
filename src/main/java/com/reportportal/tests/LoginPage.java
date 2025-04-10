package com.reportportal.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Класс-страница для авторизации в Report Portal.
 * Реализован по паттерну Page Object.
 *
 * Содержит методы для открытия страницы логина, ввода логина и пароля,
 * а также нажатия на кнопку входа.
 */
public class LoginPage {

    // Экземпляр WebDriver, через который будет производиться взаимодействие с браузером
    private WebDriver driver;

    // Локаторы для элементов на странице входа
    private By usernameField = By.name("login");
    private By passwordField = By.name("password");
    private By loginButton   = By.cssSelector("button[type='submit']");

    /**
     * Конструктор страницы логина.
     *
     * @param driver WebDriver, передаваемый из теста
     */
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Открывает стартовую страницу Report Portal.
     */
    public void open() {
        driver.get("https://demo.reportportal.io");
    }

    /**
     * Вводит имя пользователя в поле логина.
     *
     * @param username логин (например, "default")
     */
    public void enterUsername(String username) {
        WebElement userInput = driver.findElement(usernameField);
        userInput.clear();               // очищаем поле перед вводом
        userInput.sendKeys(username);   // вводим логин
    }

    /**
     * Вводит пароль в поле пароля.
     *
     * @param password пароль (например, "1q2w3e")
     */
    public void enterPassword(String password) {
        WebElement passInput = driver.findElement(passwordField);
        passInput.clear();               // очищаем поле перед вводом
        passInput.sendKeys(password);   // вводим пароль
    }

    /**
     * Кликает на кнопку входа в систему.
     */
    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    /**
     * Выполняет полный цикл авторизации: ввод логина, пароля и нажатие кнопки "Войти".
     *
     * @param username логин
     * @param password пароль
     */
    public void loginAs(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }
}
