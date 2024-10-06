import java.time.LocalDate
import java.util.*

fun main() {
    // Тестирование нового конструктора
    val personFromString = Person.parseFromString("John Doe,30,1990-01-01")
    println(personFromString)

    // Тестирование стандартного конструктора
    val personWithParams = Person("Jane Smith", 25, LocalDate.of(1995, 6, 15))
    println(personWithParams)
}