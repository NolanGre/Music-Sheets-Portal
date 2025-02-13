# MusicSheets

MusicSheets - это веб-приложение для хранения и обмена нотными записями. Пользователи могут загружать свои ноты,
делиться ими с другими музыкантами, оставлять комментарии и отмечать понравившиеся произведения.

## Технологии

- Java 21
- Spring Boot
- Spring Security с JWT аутентификацией
- Spring Data JPA
- PostgreSQL
- Redis
- Maven
- Amazon S3

## Функционал

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

**Request Headers:** Content-Type: multipart/form-data

**Request Body (multipart/form-data):**

| Поле | Тип         | Обязательное | Описание                          |
|------|-------------|--------------|-----------------------------------|
| data | JSON (text) | ✅            | Объект с данными пользователя     |
| file | File        | ✅            | Аватар пользователя (изображение) |

**Допустимые форматы изображений:** `.jpg, .jpeg, .png`

**Формат JSON в поле `data`:**

```json
{
  "login": "string",
  "password": "string",
  "username": "string"
}
```

**Пример cURL-запроса:**

```bash
curl -X POST "http://localhost:8080/api/v1/register" \
-H "Content-Type: multipart/form-data" \
-F "data={\"login\": \"user123\", \"password\": \"securepass\", \"username\": \"John Doe\"};type=application/json" \
-F "file=@path/to/avatar.jpg"
```

**Response:** 201 Created

```json
{
  "username": "string",
  "avatarUrl": "string",
  "token": "string"
}
```

**Response header:**

```
Location: /api/v1/users/{userId}
```

---

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
  "username": "string",
  "avatarUrl": "string",
  "token": "string"
}
```

---

### Нотные записи

#### Получение нот

```http
GET /api/v1/sheets/{sheetId}
```

**Response:** 200 OK

```json
{
  "id": "number",
  "title": "string",
  "author": "string",
  "description": "string",
  "genre": "string",
  "fileUrl": "string",
  "likesCount": "number",
  "commentsCount": "number",
  "publisher": {
    "id": "number",
    "username": "string",
    "avatarUrl": "string"
  },
  "creatingDate": "string (ISO 8601)",
  "modifyingDate": "string (ISO 8601)"
}
```

---

#### Создание нот

```http
POST /api/v1/sheets
```

**Request Headers:** Content-Type: multipart/form-data

**Request Body (multipart/form-data):**

| Поле | Тип         | Обязательное | Описание                      |
|------|-------------|--------------|-------------------------------|
| data | JSON (text) | ✅            | Строка JSON с данными о нотах |
| file | File        | ✅            | Файл с нотами (изображение)   |

**Допустимые форматы файла:** `.jpg, .jpeg, .png`

**Формат JSON в поле `data`:**

```json
{
  "title": "string",
  "author": "string",
  "description": "string",
  "genre": "string (one of enum)"
}
```

**Пример cURL-запроса:**

```bash
curl -X POST "http://localhost:8080/api/v1/sheets" \
  -H "Content-Type: multipart/form-data" \
  -F "data={\"title\":\"Symphony No. 5\",\"author\":\"Beethoven\",\"description\":\"Famous symphony\",\"genre\":\"CLASSICAL\"};type=application/json" \
  -F "file=@path/to/sheet.pdf"
```

**Response:** 201 Created

```json
{
  "id": "number",
  "title": "string",
  "author": "string",
  "description": "string",
  "genre": "string",
  "fileUrl": "string",
  "likesCount": "number",
  "commentsCount": "number",
  "publisher": {
    "id": "number",
    "username": "string",
    "avatarUrl": "string"
  },
  "creatingDate": "string (ISO 8601)",
  "modifyingDate": "string (ISO 8601)"
}
```

**Response header:**

```
Location: /api/v1/sheets/{sheetId}
```

---

#### Изменение нот

```http
PUT /api/v1/sheets/{sheetId}
```

**Request Headers:** Content-Type: multipart/form-data

**Request Body (multipart/form-data):**

| Поле | Тип         | Обязательное | Описание                      |
|------|-------------|--------------|-------------------------------|
| data | JSON (text) | ✅            | Строка JSON с данными о нотах |
| file | File        | ✅            | Файл с нотами (изображение)   |

**Допустимые форматы файла:** `.jpg, .jpeg, .png`

**Формат JSON в поле `data`:**

```json
{
  "title": "string",
  "author": "string",
  "description": "string",
  "genre": "string (one of enum)"
}
```

**Пример cURL-запроса:**

```bash
curl -X PUT "http://localhost:8080/api/v1/sheets/{sheetId}" \
-H "Content-Type: multipart/form-data" \
-F "data={\"title\":\"Symphony No. 5\",\"author\":\"Beethoven\",\"description\":\"Famous symphony\",\"genre\":\"CLASSICAL\",\"fileUrl\":\"path/to/sheet.pdf\"};type=application/json" \
-F "file=@path/to/sheet.jpg"
```

**Response:** 200 OK

```json
{
  "id": "number",
  "title": "string",
  "author": "string",
  "description": "string",
  "genre": "string",
  "fileUrl": "string",
  "likesCount": "number",
  "commentsCount": "number",
  "publisher": {
    "id": "number",
    "username": "string",
    "avatarUrl": "string"
  },
  "creatingDate": "string (ISO 8601)",
  "modifyingDate": "string (ISO 8601)"
}
```

---

#### Удаление нот

```http
DELETE /api/v1/sheets/{sheetId}
```

**Response:** 204 No Content

---

### Лайки

#### Проверка лайка

```http
GET /api/v1/sheets/{sheetId}/like
```

**Response:** 200 OK

```json
{
  "isLikeExist": "boolean"
}
```

---

#### Переключение лайка

```http
POST /api/v1/sheets/{sheetId}/like
```

**Response:** 200 OK

```json
{
  "isLikeExist": "boolean"
}
```

---

### Комментарии

#### Получение комментария по ID

```http
GET /api/v1/sheets/comments/{commentId}
```

**Response:** 200 OK

```json
{
  "id": "number",
  "sheetId": "number",
  "userId": "number",
  "text": "string",
  "creatingDate": "string (ISO 8601)",
  "modifyingDate": "string (ISO 8601)"
}
```

---

#### Получение всех комментариев к конкретным нотам

```http
GET /api/v1/sheets/{sheetId}/comments
```

**Response:** 200 OK

```json
[
  {
    "id": "number",
    "sheetId": "number",
    "userId": "number",
    "text": "string",
    "creatingDate": "string (ISO 8601)",
    "modifyingDate": "string (ISO 8601)"
  },
  ...
]
```

---

#### Создание комментария

``` http
POST /api/v1/sheets/{sheetId}/comments
```

**Request Body:**

```json
{
  "text": "string"
}
```

**Response:** 201 Created

```json
{
  "id": "number",
  "sheetId": "number",
  "userId": "number",
  "text": "string",
  "creatingDate": "string (ISO 8601)",
  "modifyingDate": "string (ISO 8601)"
}
```

**Response header**

```
Location: /api/v1/sheets/comments/{commentId}
```

---

#### Обновление комментария

```http
PUT /api/v1/sheets/comments/{commentId}
```

**Request Body:**

```json
{
  "text": "string"
}
```

**Response:** 200 OK

```json
{
  "id": "number",
  "sheetId": "number",
  "userId": "number",
  "text": "string",
  "creatingDate": "string (ISO 8601)",
  "modifyingDate": "string (ISO 8601)"
}
```

---

#### Удаление комментария

```http
DELETE /api/v1/sheets/comments/{commentId}
```

**Response:** 204 No Content

---

### Пользователи

#### Получение информации о пользователе

```http
GET /api/v1/users/{userId}
```

**Response:** 200 OK

```json
{
  "login": "string",
  "username": "string",
  "avatarUrl": "string",
  "creationDate": "string (ISO 8601)"
}
```

---

#### Обновление пользователя

```http
PUT /api/v1/users/{userId}
```

**Request Headers:** Content-Type: multipart/form-data

**Request Body (multipart/form-data):**

| Поле | Тип         | Обязательное | Описание                          |
|------|-------------|--------------|-----------------------------------|
| data | JSON (text) | ✅            | Объект с данными пользователя     |
| file | File        | ✅            | Аватар пользователя (изображение) |

**Допустимые форматы изображений:** `.jpg, .jpeg, .png`

**Формат JSON в поле `data`:**

```json
{
  "login": "string",
  "password": "string",
  "username": "string"
}
```

**Пример cURL-запроса:**

```bash
curl -X PUT "http://localhost:8080/api/v1/users/{userId}" \
-H "Content-Type: multipart/form-data" \
-F "data={\"login\": \"user123\", \"password\": \"securepass\", \"username\": \"John Doe\"};type=application/json" \
-F "file=@path/to/avatar.jpg"
```

**Response:** 200 OK

```json
{
  "login": "string",
  "username": "string",
  "avatarUrl": "string",
  "creationDate": "string (ISO 8601)"
}
```

---

#### Удаление пользователея

```http
DELETE /api/v1/users/{userId}
```

**Response:** 204 No Content

---

### Безопасность

Все endpoints, кроме `/api/v1/login`, `/api/v1/register` и `/api/v1/sheets` , требуют JWT токен в заголовке:

```http
Authorization: Bearer <token>
```

---

## Запуск проекта

1. Убедитесь, что у вас установлены:
    - Java 21 или выше
    - Maven
    - PostgreSQL
    - Redis

2. Создайте базы данных PostgreSQL и Redis

3. Настройте подключение к базам данных в `application.yaml`

4. Запустите приложение:

```bash
mvn spring-boot:run
```

---

## Лицензия

MIT
