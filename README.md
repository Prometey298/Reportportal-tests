# 🚀 ReportPortal Automated Tests

![Java](https://img.shields.io/badge/Java-17%2B-blue)
![Maven](https://img.shields.io/badge/Maven-3.6%2B-orange)
![Selenium](https://img.shields.io/badge/Selenium-4.31-green)
![Allure](https://img.shields.io/badge/Allure-2.27-red)

Автоматизированные UI и API тесты для ReportPortal с интеграцией Allure отчетов

---

## 🛠️ Предварительные требования

1. **Java 17+**  
   [Установить JDK 17](https://learn.microsoft.com/en-us/java/openjdk/download#openjdk-17)
2. **Maven 3.6+**  
   [Установить Apache Maven](https://maven.apache.org/)
3. **Браузеры**
    - Chrome 
    - Яндекс

---

## ⚙️ Настройка проекта

1. Клонировать репозиторий:
   ```bash
   git clone https://github.com/Prometey298/Reportportal-tests
   cd Reportportal-tests

2. Обновите config.properties (src/main/resources):
   - Укажите актуальный API-токен в api.token (как создать API key написано в ТестовоеЗадание_AQA_Java_.pdf).
   - Проверьте путь к Яндекс браузеру (yandex.browser.path). 
   - Убедитесь, что reportportal.url и api.base.url корректны
   - Измените параметр browser в config.properties на chrome или yandex.

## 🚦 Запуск тестов
1. Все тесты (UI + API)
    ```bash
    mvn clean test
2. Только UI-тест
   - Добавления нового виджета в существующий Dashboard:   
    ```bash
    mvn test -Dtest=WidgetCreationTest
  - тест пройдёт удачно, если существует Dashboard и есть фильтр "Task Progress"
3. Только API-тесты (предварительно нужно Generate API key, как требуется в задании)
    - Тест для создания нового Dashboard и проверки его наличия в списке:
    ```bash
    mvn test -Dtest=DashboardAPITest
  - Тест проверяет корректность обработки ошибки при попытке создать Dashboard без обязательного параметра "name":
    ```bash
    mvn test -Dtest=NegativeDashboardAPITest
## 📊 Генерация отчетов Allure
- После выполнения тестов сгенерируйте отчет:
    ```bash
    mvn allure:report
**Отчет будет сохранен в target/site/allure-maven-plugin**.

## 🔍 Дополнительно
- **📝 Логирование**: Логи тестов выводятся в консоль в формате:
    ```bash
    ГГГГ-ММ-ДД ЧЧ:мм:сс - УРОВЕНЬ - Сообщение
- **📸 Скриншоты при ошибках**: При ошибках UI-тестов скриншоты сохраняются в **target/screenshots/error.png.**
- **ReportPortal**: Для первого запуска выполните::
    ```bash
    mvn clean install
