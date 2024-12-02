package main.kotlin

import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

class Students_list_txt(private val filePath: String) {
    private val students: MutableList<Student> = mutableListOf()

    init {
        loadFromFile()
    }

    private fun loadFromFile() {
        val file = File(filePath)
        try {
            file.readLines().forEach { line ->
                students.add(Student(line))
            }
        } catch (e: FileNotFoundException) {
            println("Файл не найден: ${e.message}")
        } catch (e: IOException) {
            println("Ошибка чтения файла: ${e.message}")
        }
    }

    fun saveToFile() {
        val file = File(filePath)
        val text = students.joinToString("\n") { it.toString() }
        file.writeText(text)
    }

    fun getStudentById(id: Int): Student? {
        return students.find { it.id == id }
    }

    fun get_k_n_student_short_list(k: Int, n: Int): Data_table {
        val shortList = students.drop((k - 1) * n).take(n).map { student ->
            listOf(student.lastName, student.firstName, student.middleName)
        }
        return Data_table(shortList)
    }

    fun sortStudents() {
        students.sortWith(compareBy(
            { it.lastName },
            { it.firstName[0] },
            { it.middleName.takeIf { it.isNotEmpty() }?.get(0) ?: ' ' }
        ))
    }

    fun addStudent(student: Student) {
        student.id = ++StudentInfo.id_student
        students.add(student)
    }

    fun replaceStudentById(id: Int, student: Student) {
        val index = students.indexOfFirst { it.id == id }
        if (index >= 0) {
            students[index] = student.apply { this.id = id } // Устанавливаем ID для нового студента
        }
    }

    fun removeStudentById(id: Int) {
        students.removeIf { it.id == id }
    }

    fun get_student_short_count(): Int {
        return students.size
    }

    fun getStudentShortList(): List<Student> {
        return students
    }
}
