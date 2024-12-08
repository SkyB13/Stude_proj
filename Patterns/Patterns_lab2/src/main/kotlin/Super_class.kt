package main.kotlin

import com.google.gson.Gson
import java.io.File
import kotlin.reflect.KClass

abstract class Super_class<T : Any>(protected val filePath: String) {
    protected abstract val studentClass: KClass<T>

    protected val gson = Gson()
    protected var students: MutableList<T> = mutableListOf()

    init {
        loadFromFile()
    }

    abstract fun loadFromFile()

    protected fun writeToFile() {
        val json = gson.toJson(students)
        File(filePath).writeText(json)
    }

    fun getStudentById(id: Int): T? {
        return students.find { it::class.java.getDeclaredField("id").get(it) as Int == id }
    }

    fun getKthNStudentsShortList(k: Int, n: Int): DataList<StudentShort> {
        val startIndex = (k - 1) * n
        val endIndex = (startIndex + n).coerceAtMost(students.size)
        val shortList = students.subList(startIndex, endIndex)
            .map { createStudentShort(it) }
        return DataList(shortList)
    }

    fun sortStudents() {
        students.sortWith(compareBy(
            { it::class.java.getDeclaredField("lastName").get(it) as String },
            { (it::class.java.getDeclaredField("firstName").get(it) as String)[0] },
            { (it::class.java.getDeclaredField("middleName").get(it) as String?)?.get(0) ?: ' ' }
        ))
        writeToFile()
    }

    fun addStudent(student: T) {
        val newId = (students.maxOfOrNull { it::class.java.getDeclaredField("id").get(it) as Int } ?: 0) + 1
        val newStudent = createUpdatedStudent(student, newId)
        students.add(newStudent)
        writeToFile()
    }

    fun replaceStudentById(id: Int, newStudent: T): Boolean {
        val index = students.indexOfFirst { it::class.java.getDeclaredField("id").get(it) as Int == id }
        return if (index != -1) {
            students[index] = createUpdatedStudent(newStudent, id)
            writeToFile()
            true
        } else {
            false
        }
    }

    fun deleteStudentById(id: Int): Boolean {
        val removed = students.removeIf { it::class.java.getDeclaredField("id").get(it) as Int == id }
        if (removed) writeToFile()
        return removed
    }

    fun getStudentShortCount(): Int {
        return students.size
    }

    fun getStudentShortList(): MutableList<T> {
        return students
    }

    protected abstract fun createStudentShort(student: T): StudentShort
    protected abstract fun createUpdatedStudent(student: T, newId: Int): T
}
