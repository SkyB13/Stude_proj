package main.kotlin

import java.io.File

class Student_list_txt : StudentManager.Student_Strategy {
    private val filePath = "Student_txt_superclass.txt"

    override fun loadStudents(): List<Student> {
        return File(filePath).readLines().mapNotNull { line ->
            try {
                val fields = line.split(',')
                if (fields.size >= 3) {
                    Student(fields[0], fields[1], fields[2])
                } else {
                    null
                }
            } catch (e: Exception) {
                println("Error parsing student: $line")
                null
            }
        }
    }

    override fun saveStudents(students: List<Student>) {
        File(filePath).writeText(students.joinToString("\n") { "${it.id},${it.lastName},${it.firstName}" })
    }

    override fun addStudent(student: Student) {
        File(filePath).appendText("\n${student.id},${student.lastName},${student.firstName}")
    }

    override fun removeStudent(id: Int) {
        val students = loadStudents()
        val filtered = students.filter { it.id != id }
        saveStudents(filtered)
    }

    override fun updateStudent(id: Int, student: Student) {
        val students = loadStudents().map { s ->
            if (s.id == id) student else s
        }
        saveStudents(students)
    }
}
