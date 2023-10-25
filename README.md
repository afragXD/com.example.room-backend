# Roommy backend
Тестовое серверное приложение на **ktor**
___
## Почему [Ktor](https://ktor.io/)?
#### Котлин и корутины
**Ktor** построен с нуля с использованием **Kotlin и Coroutines** :heart_eyes:. Вы можете использовать лаконичный мультиплатформенный язык, а также мощь асинхронного программирования с интуитивно понятным императивным потоком.
#### Легкий и гибкий
**Ktor** позволяет вам использовать только то, что вам нужно, и структурировать приложение так, как вам нужно. Кроме того, вы также можете легко расширить Ktor с помощью собственного плагина.
#### Создан и поддерживается JetBrains
Предоставлено компанией **JetBrains**, создателями IntelliJ IDEA, Kotlin и многих других. Ktor используется не только клиентами, но и внутри компании JetBrains.
___
## Основной функционал
- Регистрация пользователей
  - Регистрация по данным
```kotlin
  data class RegisterReceiveRemote(
    val email:String,
    val password:String,
    val username:String,
    val gender: Boolean,
)
```
- Авторизация пользователей
  - Авторизация по данным
```kotlin
  data class LoginReceiveRemote(
    var email: String,
    var password:String,
)
```
- Выдача JWT-токенов
  - Токены выдаются на 24 часа
- Изменение основной информации пользователей
  - username, avatar, password
- Использование статики для хранения аватарок пользователей
- Хранение данных пользователя в бд
___
### Использованные средства разработки
#### IDE
- ![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)

#### Описание стека
- ЯП:
![flutter](https://img.shields.io/badge/Kotlin-0095D5?&style=for-the-badge&logo=kotlin&logoColor=white)
- Фреймворки/технологии:
- ![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
- [Ktor](https://ktor.io/)
- Exposed
