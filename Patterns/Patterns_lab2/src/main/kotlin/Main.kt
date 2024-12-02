package main.kotlin

import java.io.File

fun main() {
//    // Работа с файлом YAML
//    val studentsListYAML = Students_list_YAML("stud.yaml")
//
//    // Вывод содержимого файла YAML
//    println("Содержимое файла stud.yaml:")
//    studentsListYAML.students.forEach { student ->
//        println(student)
//    }
//
//    // Пример добавления нового студента
//    val newStudentYAML = Student("Иванов", "Иван", "Иванович", "+79001234567", "@ivanov", "ivanov@mail.ru", "https://github.com/ivanov")
//    studentsListYAML.addStudent(newStudentYAML)
//    studentsListYAML.saveToFile() // Сохранение изменений в файл YAML
//
//    // Получение студента по ID
//    val studentYAML = studentsListYAML.getStudentById(1) // Замените "1" на реальный ID
//    println("Студент с ID 1 (из YAML):")
//    println(studentYAML)
//
//
//
//    // Работа с файлом TXT
//    val txtFilePath = "Students_3.txt" // Убедитесь, что файл находится по этому пути
//    val studentsListTXT = Students_list_txt(txtFilePath)
//
//    // Проверка на существование файла
//    if (File(txtFilePath).exists()) {
//        // Вывод содержимого файла TXT
//        println("Содержимое файла Students_3.txt:")
//        studentsListTXT.getStudentShortList().forEach { student ->
//            println(student)
//        }
//
//        // Пример добавления нового студента в TXT
//        val newStudentTXT = Student("Петрова", "Анна", "Сергеевна", "+79001234568", "@petrov", "petrova@mail.ru", "https://github.com/petrova")
//        studentsListTXT.addStudent(newStudentTXT)
//        studentsListTXT.saveToFile() // Сохранение изменений в файл TXT
//
//        // Вывод краткого списка студентов из TXT файла
//        println("Краткий список студентов из TXT файла:")
//        studentsListTXT.get_k_n_student_short_list(1, 5).data.forEach {
//            println(it)
//        }
//    } else {
//        println("Файл не найден: $txtFilePath")
//    }


    // Работа с JSON файлом
    val jsonFilePath = "Student_3_js.json"
    val studentsListJSON = StudentsListJSON(jsonFilePath)

    // Проверка на существование файла
    if (File(jsonFilePath).exists()) {
        // Вывод содержимого файла JSON
        println("Содержимое файла Student_3_js.json:")
        studentsListJSON.getStudentShortList().forEach { student ->
            println(student)
        }

        // Пример добавления нового студента в JSON
        val newStudentJSON = Student_list_json(
            id = 0, // ID будет автоматически установлен при добавлении
            lastName = "Петрова",
            firstName = "Анна",
            middleName = "Сергеевна",
            phone = "+79001234568",
            telegram = "@petrov",
            email = "petrova@mail.ru",
            git = "https://github.com/petrova"
        )
        studentsListJSON.addStudent(newStudentJSON)
        studentsListJSON.saveToFile() // Сохранение изменений в файл JSON

        // Вывод обновленного списка после добавления
        println("\nОбновленный список после добавления:")
        studentsListJSON.getStudentShortList().forEach { student ->
            println(student)
        }
    } else {
        println("Файл не найден: $jsonFilePath")
    }
}
