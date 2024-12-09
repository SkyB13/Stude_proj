package main.kotlin

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File

class JsonStudentStorageStrategy : StudentManager.Student_Strategy {
    private val filePath = "Student_json_superclass.json"
    private val gson = Gson()

    override fun loadStudents(): List<Student> {
        try {
            val file = File(filePath)
            if (!file.exists()) return emptyList()

            val gson = GsonBuilder().setPrettyPrinting().create()
            val json = file.readText()
            return gson.fromJson(json, object : TypeToken<List<Student>>() {}.type)
        } catch (e: Exception) {
            println("Ошибка при загрузке студентов: ${e.message}")
            return emptyList()
        }
    }

    override fun saveStudents(students: List<Student>) {
        val json = gson.toJson(students)
        File(filePath).writeText(json)
    }

    override fun addStudent(student: Student) {
        val newStudent = student.apply { id = ++StudentInfo.id_student }
        val gson = GsonBuilder().setPrettyPrinting().create()

        try {
            val file = File(filePath)
            var studentsList: MutableList<Student>

            if (file.exists()) {
                val json = file.readText()
                studentsList = gson.fromJson(json, object : TypeToken<MutableList<Student>>() {}.type)
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

    override fun updateStudent(id: Int, student: Student) {
        val students = loadStudents().map { s ->
            if (s.id == id) student.apply { this.id = id } else s
        }
        saveStudents(students)
    }
}
