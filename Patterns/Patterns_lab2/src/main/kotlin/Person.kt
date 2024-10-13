// Импорты
import java.time.LocalDate
import java.util.*

class Person(
    val name: String,
    val age: Int,
    val birthDate: LocalDate
) {
    companion object {
        fun parseFromString(input: String): Person {
            try {
                val parts = input.split(',')

                if (parts.size != 3) {
                    throw ParsingException("Неверный формат входной строки")
                }

                val name = parts[0].trim()
                val ageStr = parts[1].trim()
                val birthDateStr = parts[2].trim()

                val age = ageStr.toIntOrNull() ?: throw ParsingException("Возраст должен быть числом")

                val birthDate = LocalDate.parse(birthDateStr)

                if (age < 0 || age > 150) {
                    throw ValidationException("Возраст должен быть от 0 до 150 лет")
                }

                return Person(name, age, birthDate)
            } catch (e: Exception) {
                throw ParsingException("Ошибка при парсинге строки")
            }
        }
    }

    // Вспомогательный метод для получения фамилии
    private fun getSurname(): String {
        return name.split(" ")[1]
    }

    // Вспомогательный метод для получения инициалов
    private fun getInitials(): String {
        val parts = name.split(" ")
        return "${parts[0][0]}. ${parts[1][0]}."
    }

    // Основной метод для возвращения краткой информации
    fun getInfo(): String {
        val surname = getSurname()
        val initials = getInitials()
        val contact = "email: john.doe@example.com" // Пример контактного средства
        return "$surname $initials, $contact"
    }

    override fun toString(): String {
        return "Person (name: '$name', age: $age, birthDate: $birthDate)"
    }

}

class ParsingException(message: String) : Exception(message)
class ValidationException(message: String) : Exception(message)

