package main.kotlin

import com.google.gson.Gson
import com.google.gson.GsonBuilder
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

class JsonStudentStorageStrategy : Student_Strategy<Student_list_json> {
    private val filePath = "Student_json_superclass.json"
    private val gson = Gson()

    override fun loadStudents(): List<Student_list_json> {
        try {
            val file = File(filePath)
            if (!file.exists()) return emptyList()

            val gson = GsonBuilder().setPrettyPrinting().create()
            val json = file.readText()
            return gson.fromJson(json, object : TypeToken<List<Student_list_json>>() {}.type)
        } catch (e: Exception) {
            println("Ошибка при загрузке студентов: ${e.message}")
            return emptyList()
        }
    }

    override fun saveStudents(students: List<Any>) {
        val json = gson.toJson(students)
        File(filePath).writeText(json)
    }

    override fun addStudent(student: Student_list_json) {
        val newStudent = student as Student_list_json
        val gson = GsonBuilder().setPrettyPrinting().create()

        try {
            val file = File(filePath)
            var studentsList: MutableList<Student_list_json>

            if (file.exists()) {
                val json = file.readText()
                studentsList = gson.fromJson(json, object : TypeToken<MutableList<Student_list_json>>() {}.type)
            } else {
                studentsList = mutableListOf()
            }

            studentsList.add(newStudent)
            val jsonOutput = gson.toJson(studentsList)
            file.writeText(jsonOutput)
        } catch (e: Exception) {
            println("Ошибка при добавлении студента: ${e.message}")
        }
    }

    override fun removeStudent(id: Int) {
        val students = loadStudents().filter { it.id != id }
        saveStudents(students)
    }

    override fun updateStudent(id: Int, student: Student_list_json) {
        val students = loadStudents().map { s ->
            if (s.id == id) student else s
        }
        saveStudents(students)
    }
}


