// Импорты
import java.time.LocalDate
import java.util.*

//создание класса
class Person(
    val name: String,
    val age: Int,
    val birthDate: LocalDate
) {
    companion object {
        //Добавление парса
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
            } 
            //"Отлов" исключений
            catch (e: Exception) {
                throw ParsingException("Ошибка при парсинге строки")
            }
        }
    }

    override fun toString(): String {
        return "Person (name: '$name', age: $age, birthDate: $birthDate)"
    }


}

class ParsingException(message: String) : Exception(message)
class ValidationException(message: String) : Exception(message)

