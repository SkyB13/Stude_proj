package main.kotlin

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

// ����� ��� �������� ��������
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

// ���������� ������ ������ Student
data class StudentShort(
    val id: Int,
    val initials: String
)

// ������ ������
data class DataList<T>(
    val items: List<T>
)

class StudentsListJSON(private val filePath: String) {
    private val gson = Gson()
    private var students: MutableList<Student_list_json> = mutableListOf() // �������� �� Student_list_json

    init {
        readFromFile()
    }

    // ������ ���� �������� �� �����
    private fun readFromFile() {
        val file = File(filePath)
        if (file.exists()) {
            val json = file.readText()
            val listType = object : TypeToken<MutableList<Student_list_json>>() {}.type // �������� �� Student_list_json
            students = gson.fromJson(json, listType) ?: mutableListOf()
        }
    }

    // ������ ���� �������� � ����
    private fun writeToFile() {
        val json = gson.toJson(students)
        File(filePath).writeText(json)
    }

    fun saveToFile() {
        val file = File(filePath)
        val text = students.joinToString("\n") { it.toString() }
        file.writeText(text)
    }

    // �������� ������ Student �� ID
    fun getStudentById(id: Int): Student_list_json? {
        return students.find { it.id == id }
    }

    // �������� ������ k �� ����� n �������� ������ StudentShort
    fun getKthNStudentsShortList(k: Int, n: Int): DataList<StudentShort> {
        val startIndex = (k - 1) * n
        val endIndex = (startIndex + n).coerceAtMost(students.size)
        val shortList = students.subList(startIndex, endIndex)
            .map { StudentShort(it.id, "${it.lastName} ${it.firstName[0]}.${it.middleName?.get(0) ?: ""}.") }
        return DataList(shortList)
    }

    // ���������� �� ������� � ���������
    fun sortStudents() {
        students.sortWith(compareBy({ it.lastName }, { it.firstName }, { it.middleName }))
        writeToFile()
    }

    // �������� ������ Student � ������
    fun addStudent(student: Student_list_json) { // �������� �� Student_list_json
        val newId = (students.maxOfOrNull { it.id } ?: 0) + 1
        val newStudent = student.copy(id = newId)
        students.add(newStudent)
        writeToFile()
    }

    // �������� ������� ������ �� ID
    fun replaceStudentById(id: Int, newStudent: Student_list_json): Boolean { // �������� �� Student_list_json
        val index = students.indexOfFirst { it.id == id }
        return if (index != -1) {
            students[index] = newStudent.copy(id = id)
            writeToFile()
            true
        } else {
            false
        }
    }

    // ������� ������� ������ �� ID
    fun deleteStudentById(id: Int): Boolean {
        val removed = students.removeIf { it.id == id }
        if (removed) writeToFile()
        return removed
    }

    // �������� ���������� ���������
    fun getStudentShortCount(): Int {
        return students.size
    }

    fun getStudentShortList(): MutableList<Student_list_json> {
        return students
    }
}
