package com.reportportal.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.HashMap;
import java.util.Map;

public class WebDriverFactory {
    public static WebDriver createDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "yandex":
                return createYandexDriver();
            case "chrome":
            default:
                return createChromeDriver();
        }
    }

    private static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = getCommonChromeOptions();
        return new ChromeDriver(options);
    }

    private static WebDriver createYandexDriver() {
        String yandexPath = ConfigLoader.getProperty("yandex.browser.path");
        if (yandexPath == null || yandexPath.isEmpty()) {
            throw new RuntimeException("Path to Yandex Browser is not specified in config.properties");
        }

        // Set up ChromeDriver for Yandex Browser
        WebDriverManager.chromedriver()
                .driverVersion("132.0.6834.83")  // ← Явно указываем версию
                .setup();

        ChromeOptions options = getCommonChromeOptions();
        options.setBinary(yandexPath);

        return new ChromeDriver(options);
    }

    private static ChromeOptions getCommonChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("autofill.profile_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--incognito");
        return options;
    }
}