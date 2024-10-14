import java.time.LocalDate
import java.util.*
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

// Метод для чтения данных из файла и возврата массива объектов Student
fun read_from_txt(filePath: String): Array<Student> {
    val students = mutableListOf<Student>()

    try {
        val file = File(filePath)

        if (!file.exists()) {
            throw FileNotFoundException("Файл по адресу $filePath не найден")
        }

        file.forEachLine { line ->
            val parts = line.split(",")

            if (parts.size != 4) {
                throw ParsingException("Некорректный формат строки: $line")
            }

            val id = parts[0].trim().toIntOrNull() ?: throw ParsingException("ID должен быть числом")
            val surname = parts[1].trim()
            val initials = parts[2].trim()
            val contact = parts[3].trim()

            // Создаем объект Student и добавляем его в список
            val student = Student(id, surname, initials, contact)
            students.add(student)
        }

    } catch (e: FileNotFoundException) {
        throw Exception("Ошибка: файл не найден по адресу $filePath")
    } catch (e: IOException) {
        throw Exception("Ошибка ввода/вывода при чтении файла")
    } catch (e: ParsingException) {
        throw Exception("Ошибка парсинга данных: ${e.message}")
    }

    return students.toTypedArray() // Возвращаем массив студентов
}

fun main() {
    // Тестирование конструктора Person
    val person = Person.parseFromString("John Doe, 30, 1990-01-01")
    println(person.getInfo())

    // Тестирование стандартного конструктора Person
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

    // Тестирование ошибок
    try {
        val person = Person.parseFromString("John Doe, 30, 1990-01-01")
        println(person)

        // Неверный формат
        Person.parseFromString("Invalid format") // Выбросит ParsingException

        // Неверный возраст
        Person.parseFromString("Jane Smith, abc, 2000-01-01") // Выбросит ParsingException

        // Возраст вне допустимых границ
        Person.parseFromString("Bob Johnson, 200, 1980-01-01") // Выбросит ValidationException
    } catch (e: ParsingException) {
        println("Ошибка парсинга: ${e.message}")
    } catch (e: ValidationException) {
        println("Ошибка валидации: ${e.message}")
    }

    println("ЧТЕНИЕ ИЗ ФАЙЛА")
    try {
        val students = read_from_txt("students.txt")
        students.forEach { println(it) }
    } catch (e: Exception) {
        println(e.message)
    }
}
