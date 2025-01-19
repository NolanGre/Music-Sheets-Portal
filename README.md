# MusicSheets

MusicSheets - это веб-приложение для хранения и обмена нотными записями. Пользователи могут загружать свои ноты, делиться ими с другими музыкантами, оставлять комментарии и отмечать понравившиеся произведения.

## Технологии

- Java 17
- Spring Boot 3.x
- Spring Security с JWT аутентификацией
- Spring Data JPA
- PostgreSQL
- Maven

## Функциональность

- Регистрация и аутентификация пользователей
- Управление нотными записями (создание, редактирование, удаление)
- Социальные функции (комментарии, лайки)
- Система ролей (пользователь, администратор)
- Защита API с помощью JWT токенов

## API Documentation

### Аутентификация

#### Регистрация
```http
POST /api/v1/register
```
**Request Body:**
```json
{
    "login": "string",
    "password": "string",
    "email": "string"
}
```
**Response:** 201 Created
```json
{
    "id": "number",
    "login": "string",
    "email": "string",
    "token": "string"
}
```

#### Вход
```http
POST /api/v1/login
```
**Request Body:**
```json
{
    "login": "string",
    "password": "string"
}
```
**Response:** 200 OK
```json
{
    "id": "number",
    "login": "string",
    "token": "string"
}
```

### Нотные записи

#### Получение ноты
```http
GET /api/v1/sheets/{sheetId}
```
**Response:** 200 OK
```json
{
    "id": "number",
    "title": "string",
    "content": "string",
    "genre": "string",
    "publisher": {
        "id": "number",
        "login": "string"
    },
    "comments": [
        {
            "id": "number",
            "content": "string",
            "author": {
                "id": "number",
                "login": "string"
            }
        }
    ],
    "likes": [
        {
            "id": "number",
            "user": {
                "id": "number",
                "login": "string"
            }
        }
    ]
}
```

#### Создание ноты
```http
POST /api/v1/sheets
```
**Request Body:**
```json
{
    "title": "string",
    "content": "string",
    "genre": "string"
}
```
**Response:** 201 Created

### Пользователи

#### Получение информации о пользователе
```http
GET /api/v1/users/{userId}
```
**Response:** 200 OK
```json
{
    "id": "number",
    "login": "string",
    "email": "string",
    "role": "string"
}
```

#### Обновление пользователя
```http
PUT /api/v1/users/{userId}
```
**Request Body:**
```json
{
    "login": "string",
    "email": "string",
    "password": "string"
}
```
**Response:** 200 OK

### Безопасность

Все endpoints, кроме `/api/v1/login` и `/api/v1/register`, требуют JWT токен в заголовке:
```http
Authorization: Bearer <token>
```

## Запуск проекта

1. Убедитесь, что у вас установлены:
   - Java 17 или выше
   - Maven
   - PostgreSQL

2. Создайте базу данных PostgreSQL

3. Настройте подключение к базе данных в `application.yaml`

4. Запустите приложение:
```bash
mvn spring-boot:run
```

## Лицензия

MIT
