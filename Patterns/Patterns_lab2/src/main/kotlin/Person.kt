// Импорты
import java.time.LocalDate
import java.util.*

class Person(
    val name: String,
    val age: Int,
    val birthDate: LocalDate
) {
    companion object {
        // Новый конструктор для парсинга строки
        fun parseFromString(input: String): Person {
            val (name, ageStr, birthDateStr) = input.split(',')
            val age = ageStr.toInt()
            val birthDate = LocalDate.parse(birthDateStr)
            return Person(name, age, birthDate)
        }
    }

    override fun toString(): String {
        return "Person(name='$name', age=$age, birthDate=$birthDate)"
    }
}


