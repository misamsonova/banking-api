
# Banking API

## Описание

Banking API — это RESTful приложение для управления банковскими счетами и транзакциями. Приложение предоставляет API для создания счетов, выполнения транзакций и управления ими. Реализован функционал проверки и обработки ошибок, таких как недостаток средств, некорректные данные, и управление пользователями.

## Технологии

- **Java**: основной язык разработки.
- **Spring Boot**: для создания приложения с минимальной конфигурацией.
- **Spring REST**: для реализации RESTful API.
- **Swagger**: для документирования и тестирования API.
- **PostgreSQL**: реляционная база данных.
- **Maven**: инструмент управления зависимостями и сборки проекта.

## Структура проекта

- **config**: содержит классы конфигурации, такие как `DataLoader`, который загружает данные при запуске приложения.
- **controller**: содержит REST-контроллеры, такие как `AccountController`, который отвечает за обработку HTTP-запросов, связанных с аккаунтами.
- **dto**: хранит объекты для передачи данных (Data Transfer Objects), такие как `CreateAccountRequest`.
- **exception**: содержит кастомные исключения и глобальный обработчик ошибок `GlobalExceptionHandler`.
- **model**: хранит сущности базы данных, такие как `Account`, `Transaction` и `User`.
- **repository**: репозитории для работы с базой данных, такие как `AccountRepository`, `TransactionRepository` и `UserRepository`.
- **service**: содержит бизнес-логику, например, `AccountService`.

## Установка

### Требования

- Java 11+
- Maven
- PostgreSQL

### Шаги по установке

1. **Клонируйте репозиторий:**
   ```bash
   git clone https://github.com/yourusername/banking-api.git
   cd banking-api
   ```

2. **Создайте базу данных PostgreSQL:**
   В PostgreSQL создайте базу данных с именем `banking`.
   ```sql
   CREATE DATABASE banking;
   ```

3. **Настройте подключение к базе данных:**
   Откройте файл `src/main/resources/application.properties` и измените следующие строки, чтобы указать ваши параметры подключения к базе данных:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/banking
   spring.datasource.username=yourusername
   spring.datasource.password=yourpassword
   ```

4. **Соберите и запустите приложение:**
   Используйте Maven для сборки и запуска приложения:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

5. **Swagger-документация API:**
   После успешного запуска приложения, откройте [Swagger UI](http://localhost:8080/swagger-ui.html) для просмотра и тестирования доступных API.

## Использование API

### Эндпоинты

#### 1. Создание нового аккаунта

- **Метод**: `POST`
- **URL**: `/api/accounts`
- **Описание**: Создает новый банковский аккаунт.
- **Тело запроса**:
  ```json
  {
    "username": "string",
    "password": "string",
    "pin": "1234"
  }
  ```
- **Ответ**:
    - `201 Created` - если аккаунт успешно создан.
    - `400 Bad Request` - если данные запроса неверны.
    - **Примечание**: Проверка ввода включает проверку длины и формата PIN-кода.

#### 2. Выполнение транзакции

- **Метод**: `POST`
- **URL**: `/api/transactions`
- **Описание**: Выполняет транзакцию между двумя счетами.
- **Тело запроса**:
  ```json
  {
    "fromAccountId": 1,
    "toAccountId": 2,
    "amount": 100.0,
    "pin": "1234"
  }
  ```
- **Ответ**:
    - `201 Created` - транзакция успешно завершена.
    - `400 Bad Request` - ошибка в данных запроса (например, некорректная сумма).
    - `404 Not Found` - один из указанных счетов не найден.
    - `402 Payment Required` - недостаточно средств на счете отправителя.

### Ошибки

#### Обработчик глобальных исключений

Все исключения обрабатываются глобальным обработчиком ошибок `GlobalExceptionHandler`, который возвращает корректные HTTP-коды и описания ошибок:
- **AccountNotFoundException**: Возвращает `404 Not Found`, если указанный аккаунт не найден.
- **InsufficientFundsException**: Возвращает `402 Payment Required`, если на счете недостаточно средств.
- **InvalidAmountException**: Возвращает `400 Bad Request`, если сумма транзакции некорректна.
- **InvalidPinException** и **InvalidPinFormatException**: Возвращает `400 Bad Request`, если введен некорректный или неправильный PIN-код.

## Принятые решения при разработке

1. **Проверка вводимых данных**:  
   Важной частью разработки было обеспечение корректности вводимых данных. Были реализованы проверки на уровне контроллеров и сервисов:
    - Проверка формата PIN-кода.
    - Проверка положительных значений сумм для транзакций.
    - Проверка существования счетов перед выполнением транзакций.

2. **Обработка исключений**:  
   Для улучшения пользовательского опыта был создан глобальный обработчик ошибок `GlobalExceptionHandler`, который возвращает понятные сообщения об ошибках в формате JSON.

3. **Использование DTO (Data Transfer Objects)**:  
   Для лучшей организации передачи данных между клиентом и сервером использованы DTO, такие как `CreateAccountRequest`, для инкапсуляции данных запроса.

4. **Использование Swagger**:  
   Swagger был использован для автоматической генерации документации и упрощения тестирования API.

## Тестирование

API было протестировано с использованием Swagger UI и ручных запросов через Postman. Дополнительно предусмотрено добавление тестов с использованием JUnit для покрытия основных сценариев работы API.

## Заключение

Banking API предоставляет полный набор функций для работы с банковскими счетами и транзакциями. Использование современных технологий и принципов разработки обеспечило высокую надежность и масштабируемость приложения.

