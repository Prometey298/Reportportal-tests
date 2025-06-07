package com.reportportal.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.HashMap;
import java.util.Map;

public class WebDriverFactory {

    /**
     * Создает экземпляр WebDriver для указанного браузера.
     *
     * @param browser название браузера ("chrome" или "yandex")
     * @return настроенный экземпляр WebDriver
     * @throws RuntimeException если браузер не поддерживается или возникла ошибка при создании драйвера
     */
    public static WebDriver createDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "yandex":
                return createYandexDriver();
            case "chrome":
            default:
                return createChromeDriver();
        }
    }

    /**
     * Создает стандартный ChromeDriver с общими настройками.
     *
     * @return экземпляр ChromeDriver
     */
    private static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup(); // Автоматическая настройка ChromeDriver
        ChromeOptions options = getCommonChromeOptions(); // Получаем общие настройки
        return new ChromeDriver(options); // Создаем драйвер с настройками
    }

    /**
     * Создает WebDriver для Yandex Browser с учетом специфичных настроек.
     *
     * @return экземпляр ChromeDriver, настроенный для работы с Yandex Browser
     * @throws RuntimeException если путь к Yandex Browser не указан или ОС не поддерживается
     */
    private static WebDriver createYandexDriver() {
        // Определяем операционную систему
        String os = System.getProperty("os.name").toLowerCase();
        String yandexPath;

        // Выбираем путь к Yandex Browser в зависимости от ОС
        if (os.contains("win")) {
            yandexPath = ConfigLoader.getProperty("yandex.browser.path.windows");
        } else if (os.contains("mac")) {
            yandexPath = ConfigLoader.getProperty("yandex.browser.path.mac");
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            yandexPath = ConfigLoader.getProperty("yandex.browser.path.linux");
        } else {
            throw new RuntimeException("Unsupported operating system: " + os);
        }

        // Проверяем что путь указан
        if (yandexPath == null || yandexPath.isEmpty()) {
            throw new RuntimeException("Path to Yandex Browser is not specified for OS: " + os);
        }

        // Настраиваем ChromeDriver для Yandex с указанием версии
        WebDriverManager.chromedriver()
                .driverVersion(ConfigLoader.getProperty("yandex.browser.version"))
                .setup();

        // Получаем общие настройки и указываем путь к Yandex Browser
        ChromeOptions options = getCommonChromeOptions();
        options.setBinary(yandexPath);

        return new ChromeDriver(options);
    }

    /**
     * Создает общие настройки для Chrome/Yandex браузеров.
     *
     * @return объект ChromeOptions с общими настройками
     */
    private static ChromeOptions getCommonChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();

        // Отключаем сохранение паролей и автозаполнение
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("autofill.profile_enabled", false);

        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--incognito"); // Режим инкогнито
        return options;
    }
}