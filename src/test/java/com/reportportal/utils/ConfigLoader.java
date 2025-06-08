package com.reportportal.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ConfigLoader {
    // Статический объект Properties для хранения загруженных параметров
    private static final Properties properties = new Properties();

    // Статический блок инициализации - выполняется при первой загрузке класса
    static {
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
            // Проверка наличия файла конфигурации
            if (input == null) {
                throw new RuntimeException("Unable to find config.properties");
            }
            // Загрузка параметров из файла
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    /**
     * Возвращает значение параметра конфигурации по ключу.
     * @param key имя параметра (ключ)
     * @return значение параметра
     * @throws RuntimeException если параметр не найден
     */
    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        // Проверка наличия запрошенного параметра
        if (value == null) {
            throw new RuntimeException("Property " + key + " not found in config");
        }
        return value;
    }
}