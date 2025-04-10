package com.reportportal.tests.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Утилитный класс для работы с ожиданиями (explicit waits) в Selenium.
 *
 * Содержит обёртки над WebDriverWait для:
 * - ожидания появления элемента на странице;
 * - ожидания кликабельности элемента.
 *
 * Позволяет сократить дублирование кода в Page Object и тестах.
 */
public class WaitUtils {

    /**
     * Явно ожидает, пока элемент не станет видимым (displayed) на странице.
     *
     * @param driver  экземпляр WebDriver
     * @param locator локатор искомого элемента
     * @param seconds максимальное время ожидания (в секундах)
     */
    public static void waitUntilVisible(WebDriver driver, By locator, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Явно ожидает, пока элемент не станет кликабельным.
     *
     * @param driver  экземпляр WebDriver
     * @param locator локатор искомого элемента
     * @param seconds максимальное время ожидания (в секундах)
     */
    public static void waitUntilClickable(WebDriver driver, By locator, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
}
