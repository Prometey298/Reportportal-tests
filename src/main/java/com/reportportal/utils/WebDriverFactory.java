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
        // Определяем операционную систему
        String os = System.getProperty("os.name").toLowerCase();
        String yandexPath;
        if (os.contains("win")) {
            yandexPath = ConfigLoader.getProperty("yandex.browser.path.windows");
        } else if (os.contains("mac")) {
            yandexPath = ConfigLoader.getProperty("yandex.browser.path.mac");
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            yandexPath = ConfigLoader.getProperty("yandex.browser.path.linux");
        } else {
            throw new RuntimeException("Unsupported operating system: " + os);
        }

        if (yandexPath == null || yandexPath.isEmpty()) {
            throw new RuntimeException("Path to Yandex Browser is not specified for OS: " + os);
        }

        // Устанавливаем ChromeDriver для Yandex Browser с указанием нужной версии
        WebDriverManager.chromedriver()
                .driverVersion(ConfigLoader.getProperty("yandex.browser.version"))
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
