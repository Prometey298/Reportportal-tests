package com.reportportal.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class WaitUtils {

    /**
     * Ожидает появления и видимости элемента на странице.
     *
     * @param driver  Экземпляр WebDriver для взаимодействия с браузером
     * @param locator Локатор элемента (By) который нужно ожидать
     * @param seconds Максимальное время ожидания в секундах
     * @throws org.openqa.selenium.TimeoutException Если элемент не стал видимым за указанное время
     */
    public static void waitUntilVisible(WebDriver driver, By locator, int seconds) {
        // Создаем экземпляр WebDriverWait с указанным временем ожидания
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        // Ожидаем пока элемент не станет видимым на странице
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Ожидает пока элемент станет кликабельным (видимым и enabled).
     *
     * @param driver  Экземпляр WebDriver для взаимодействия с браузером
     * @param locator Локатор элемента (By) который нужно ожидать
     * @param seconds Максимальное время ожидания в секундах
     * @throws org.openqa.selenium.TimeoutException Если элемент не стал кликабельным за указанное время
     */
    public static void waitUntilClickable(WebDriver driver, By locator, int seconds) {
        // Создаем экземпляр WebDriverWait с указанным временем ожидания
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        // Ожидаем пока элемент не станет кликабельным
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
}