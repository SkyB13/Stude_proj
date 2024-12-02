package main.kotlin

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

// Класс для описания студента
data class Student_list_json(
    var id: Int,
    val lastName: String,
    val firstName: String,
    val middleName: String?,
    val phone: String?,
    val telegram: String?,
    val email: String?,
    val git: String?
)

// Упрощённая версия класса Student
data class StudentShort(
    val id: Int,
    val initials: String
)

// Список данных
data class DataList<T>(
    val items: List<T>
)

class StudentsListJSON(private val filePath: String) {
    private val gson = Gson()
    private var students: MutableList<Student_list_json> = mutableListOf() // Изменено на Student_list_json

    init {
        readFromFile()
    }

    // Чтение всех значений из файла
    private fun readFromFile() {
        val file = File(filePath)
        if (file.exists()) {
            val json = file.readText()
            val listType = object : TypeToken<MutableList<Student_list_json>>() {}.type // Изменено на Student_list_json
            students = gson.fromJson(json, listType) ?: mutableListOf()
        }
    }

    // Запись всех значений в файл
    private fun writeToFile() {
        val json = gson.toJson(students)
        File(filePath).writeText(json)
    }

    fun saveToFile() {
        val file = File(filePath)
        val text = students.joinToString("\n") { it.toString() }
        file.writeText(text)
    }

    // Получить объект Student по ID
    fun getStudentById(id: Int): Student_list_json? {
        return students.find { it.id == id }
    }

    // Получить список k по счёту n объектов класса StudentShort
    fun getKthNStudentsShortList(k: Int, n: Int): DataList<StudentShort> {
        val startIndex = (k - 1) * n
        val endIndex = (startIndex + n).coerceAtMost(students.size)
        val shortList = students.subList(startIndex, endIndex)
            .map { StudentShort(it.id, "${it.lastName} ${it.firstName[0]}.${it.middleName?.get(0) ?: ""}.") }
        return DataList(shortList)
    }

    // Сортировка по фамилии и инициалам
    fun sortStudents() {
        students.sortWith(compareBy({ it.lastName }, { it.firstName }, { it.middleName }))
        writeToFile()
    }

    // Добавить объект Student в список
    fun addStudent(student: Student_list_json) { // Изменено на Student_list_json
        val newId = (students.maxOfOrNull { it.id } ?: 0) + 1
        val newStudent = student.copy(id = newId)
        students.add(newStudent)
        writeToFile()
    }

    // Заменить элемент списка по ID
    fun replaceStudentById(id: Int, newStudent: Student_list_json): Boolean { // Изменено на Student_list_json
        val index = students.indexOfFirst { it.id == id }
        return if (index != -1) {
            students[index] = newStudent.copy(id = id)
            writeToFile()
            true
        } else {
            false
        }
    }

    // Удалить элемент списка по ID
    fun deleteStudentById(id: Int): Boolean {
        val removed = students.removeIf { it.id == id }
        if (removed) writeToFile()
        return removed
    }

    // Получить количество элементов
    fun getStudentShortCount(): Int {
        return students.size
    }

    fun getStudentShortList(): MutableList<Student_list_json> {
        return students
    }
}
