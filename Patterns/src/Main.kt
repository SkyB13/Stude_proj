import java.time.LocalDate
import java.util.*
import java.util.regex.Pattern


fun main() {
    val student = Student()

    try {
        // Устанавливаем данные через сеттеры
        student.firstName = "Иван"
        student.lastName = "Иванов"
        student.middleName = "Петрович"
        student.birthDate = LocalDate.of(2000, 1, 1)
        student.telPhone = "+79991234567"    // Устанавливаем телефон через сеттер
        //student.telegram = "@ivanov13"         // Устанавливаем Telegram через сеттер
        student.email = "ivanov@mail.ru"     // Устанавливаем email через сеттер
        student.git = "https://github.com/ivanov"  // Устанавливаем ссылку на Git через сеттер

        // Выводим данные студента
        println(student)

    } catch (e: IllegalArgumentException) {
        println("Ошибка: ${e.message}")
    }
}
