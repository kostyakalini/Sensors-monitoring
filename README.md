# Sensors Monitoring Application

## Описание

Это приложение для мониторинга датчиков, которое состоит из следующих сервисов:

*   **monitor-sensors:** Сервис для управления датчиками.
*   **statistics-service:** Сервис для сбора и отображения статистики по датчикам.
*   **sensors-db:** База данных для хранения информации о датчиках.
*   **statistics-db:** База данных для хранения статистики.

## Функциональность

*   Мониторинг различных типов датчиков.
*   Сбор статистики по типам датчиков.
*   Предоставление API для управления датчиками и получения статистики.

## Требования

*   Docker
*   Docker Compose

## Инструкция по запуску

1.  **Клонируйте репозиторий:**

    ```bash
    git clone https://github.com/kostyakalini/Sensors-monitoring.git
    cd Sensors-monitoring
    ```

2.  **Создайте файлы `application-secrets.properties` и `monitor-sensors-cred.properties`:**

    *   Для сервиса `statistics-service` создайте файл `statistics-service/src/main/resources/application-secrets.properties` и укажите в нем секретные параметры (пароль от базы данных).

        Пример:

        ```properties
        spring.datasource.password=your_statistics_db_password
        ```

    *   Для сервиса `monitor-sensors` создайте файл `monitor-sensors/src/main/resources/monitor-sensors-cred.properties` и укажите в нем секретные параметры (имя пользователя и пароль для доступа к сервису).

        Пример:

        ```properties
        monitor.sensors.username=monitoring-statistics
        monitor.sensors.password=your_monitor_sensors_password
        ```

    **Внимание:** Не коммитьте эти файлы в публичный репозиторий! Убедитесь, что они добавлены в `.gitignore`.

3.  **Запустите приложение с помощью Docker Compose:**

    *   Скопируйте файл `compose-example.yaml` в корень проекта и переименуйте его в `docker-compose.yaml`.

        ```bash
        cp compose-example.yaml docker-compose.yaml
        ```

    *   Отредактируйте файл `docker-compose.yaml` и замените значения `YOUR_USERNAME` и `YOUR_PASSWORD` на актуальные значения для доступа к базам данных.

    *   Запустите приложение:

        ```bash
        docker-compose up --build
        ```

        Эта команда соберет образы и запустит все сервисы.

## Конфигурация

Конфигурация приложения находится в файлах `application.properties` (или `application.yml`) и `application-secrets.properties` в директории `src/main/resources/` каждого сервиса.

## API

Описание API доступно по адресу:

*   `monitor-sensors`: `http://localhost:8080/swagger-ui.html`
*   `statistics-service`: `http://localhost:8081/swagger-ui.html`

## Зависимости

*   Java 21
*   Spring Boot
*   PostgreSQL
*   Docker
*   Docker Compose
*   Swagger

## Авторы

*   kostyakalini

## Лицензия