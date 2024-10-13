import java.time.LocalDate
import java.util.*

fun main() {
    // Тестирование нового конструктора c разделением данных
    val person = Person.parseFromString("John Doe, 30, 1990-01-01")
    println(person.getInfo())

    // Тестирование стандартного конструктора
    val personWithParams = Person("Jane Smith", 25, LocalDate.of(1995, 6, 15))
    println(personWithParams)



    //вывод для проверки класса Student_short
    println("Тест. Задание №4")
    val student = Student(1, "Doe", "J. D.", "john.doe@example.com")
    val studentShort1 = Student_short(student)
    println(studentShort1)

    // Тестируем второй конструктор (с ID и строкой)
    val studentShort2 = Student_short(2, "Smith, A. S., alice.smith@example.com")
    println(studentShort2)



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
