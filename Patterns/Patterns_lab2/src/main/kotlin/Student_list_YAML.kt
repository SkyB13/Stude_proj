package main.kotlin

import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

class Student_list_YAML : StudentManager.Student_Strategy {
    private var filePath = "Stud.yaml"
    val students: MutableList<Student> = mutableListOf()

    init {
        loadFromFile()
    }

    constructor() {
        this.filePath = filePath
        loadFromFile()
    }

    private fun loadFromFile() {
        val file = File(filePath)
        try {
            val yaml = Yaml()
            val data: List<Map<String, Any>> = yaml.load(file.inputStream()) ?: emptyList()
            data.forEach { studentMap ->
                students.add(Student(
                    studentMap["lastname"]?.toString() ?: "",
                    studentMap["firstname"]?.toString() ?: "",
                    studentMap["middlename"]?.toString() ?: "",
                    studentMap["phone"]?.toString(),
                    studentMap["telegram"]?.toString(),
                    studentMap["email"]?.toString(),
                    studentMap["github"]?.toString()
                ).apply { id = studentMap["id"]?.toString()?.toIntOrNull() ?: 0 })
            }
        } catch (e: FileNotFoundException) {
            println("Файл не найден: ${e.message}")
        } catch (e: IOException) {
            println("Ошибка чтения файла: ${e.message}")
        } catch (e: Exception) {
            println("Ошибка при загрузке данных: ${e.message}")
        }
    }

    fun saveToFile() {
        val file = File(filePath)
        val yaml = Yaml(DumperOptions().apply {
            defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
        })
        val data = students.map { student ->
            mapOf(
                "id" to student.id,
                "lastname" to student.lastName,
                "firstname" to student.firstName,
                "middlename" to student.middleName,
                "phone" to student.phone,
                "telegram" to student.telegram,
                "email" to student.email,
                "github" to student.git
            )
        }
        file.writeText(yaml.dump(data))
    }

    override fun loadStudents(): List<Student> {
        return students
    }

    override fun saveStudents(students: List<Student>) {
        this.students.clear()
        this.students.addAll(students)
        saveToFile()
    }

    override fun addStudent(student: Student) {
        val castStudent = student.apply { id = ++StudentInfo.id_student }
        students.add(castStudent)
        saveToFile()
    }

    override fun removeStudent(id: Int) {
        students.removeIf { it.id == id }
        saveToFile()
    }

    override fun updateStudent(id: Int, student: Student) {
        val index = students.indexOfFirst { it.id == id }
        if (index >= 0) {
            students[index] = student.apply { this.id = id }
        }
        saveToFile()
    }
}
