package main.kotlin

import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

class Students_list_YAML(private val filePath: String) {
    val students: MutableList<Student> = mutableListOf()

    init {
        loadFromFile()
    }

    private fun loadFromFile() {
        val file = File(filePath)
        try {
            val yaml = Yaml()
            val data: List<Map<String, Any?>> = yaml.load(file.inputStream()) ?: emptyList()
            data.forEach { studentMap ->
                students.add(Student(
                    studentMap["lastname"]?.toString() ?: "",
                    studentMap["firstname"]?.toString() ?: "",
                    studentMap["middlename"]?.toString() ?: "",
                    studentMap["phone"]?.toString(),
                    studentMap["telegram"]?.toString(),
                    studentMap["email"]?.toString(),
                    studentMap["github"]?.toString()
                ))
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
}
