import main.kotlin.*
import main.kotlin.Student_list_YAML
import main.kotlin.JsonStudentStorageStrategy
import main.kotlin.Student_list_txt

fun main() {
    // Работа с YAML файлом
    val yamlStudents = Student_list_YAML("students.yaml")

    println("Студенты из YAML:")
    yamlStudents.students.forEach { println(it) }

    // Добавление нового студента в YAML
    val student_yaml = Student("Иванов", "Иван", "Иванович", "1234567890", null, null, null)
    student_yaml.id = 1
    yamlStudents.addStudent(student_yaml)


    // Сохранение изменений в YAML
    yamlStudents.saveToFile()

    println("\n")

    // Работа с JSON файлом
    val jsonStrategy = JsonStudentStorageStrategy()
    val jsonManager = StudentManager(jsonStrategy)

    println("Студенты из JSON:")
    jsonManager.loadStudents().forEach { println(it) }

    // Добавление нового студента в JSON
    jsonManager.addStudent(Student_list_json(1, "Петров", "Пётр", "Петрович", "0987654321", null, null, null))

    println("\n")

    // Работа с TXT файлом
    val txtStudents = Student_list_txt()

    println("Студенты из TXT:")
    txtStudents.loadStudents().forEach { println(it) }

    // Добавление нового студента в TXT
    val student_txt = Student("Иванов", "Иван", "Иванович", "1234567890", null, null, null)
    student_txt.id = 1
    yamlStudents.addStudent(student_txt)

    println("\n")

    // Демонстрация дополнительных функций YAML
    println("Студент по ID из YAML:")
    yamlStudents.getStudentById(1)?.let { println(it) }

    println("\nСтраница 1 из 2 студентов в YAML:")
    val shortList = yamlStudents.get_k_n_student_short_list(1, 2)
    shortList.data.forEach { println(it.joinToString(", ")) }

    println("\nСортировка студентов в YAML:")
    yamlStudents.sortStudents()
    yamlStudents.students.forEach { println(it) }

    println("\nКоличество студентов в YAML:")
    println(yamlStudents.get_student_short_count())

    println("\nОбновление студента в YAML:")
    val updatedStudent = Student(
        // ID
        "Иванов", // Фамилия
        "Иван", // Имя
        "Иванович", // Отчество
        "1234567890", // Телефон
        "ivanov_ii@university.ru", // E-mail (обновлено)
        null, // Telegram (оставляем null)
        null // GitHub (оставляем null)
    )
    yamlStudents.updateStudent(1, updatedStudent)
    yamlStudents.students.forEach { println(it) }


    println("\nУдаление студента из YAML:")
    yamlStudents.removeStudentById(1)
    yamlStudents.students.forEach { println(it) }
}
