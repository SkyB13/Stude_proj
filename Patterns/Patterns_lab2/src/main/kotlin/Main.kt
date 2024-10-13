import java.time.LocalDate
import java.util.*

fun main() {
    // Тестирование нового конструктора c разделением данных (getInfo, задание №3)
    val person = Person.parseFromString("John Doe, 30, 1990-01-01")
    println(person.getInfo())

    // Тестирование стандартного конструктора
    val personWithParams = Person("Jane Smith", 25, LocalDate.of(1995, 6, 15))
    println(personWithParams)

    try {
        val person = Person.parseFromString("John Doe, 30, 1990-01-01")
        println(person)

        // Тестирование ошибок
        Person.parseFromString("Invalid format") // Выбросит ParsingException

        Person.parseFromString("Jane Smith, abc, 2000-01-01") // Выбросит ParsingException

        Person.parseFromString("Bob Johnson, 200, 1980-01-01") // Выбросит ValidationException
    } catch (e: ParsingException) {
        println("Ошибка парсинга: ${e.message}")
    } catch (e: ValidationException) {
        println("Ошибка валидации: ${e.message}")
    }
}
