package main.kotlin

import java.io.File

class Student_list_txt : Student_Strategy<Student> {
    private val filePath = "Student_txt_superclass.txt"

    override fun loadStudents(): List<Student> {
        return File(filePath).readLines().mapNotNull { line ->
            try {
                // Assuming Student constructor takes id, name, and age
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

    override fun saveStudents(students: List<Any>) {
        File(filePath).writeText(students.joinToString("\n"))
    }

    override fun addStudent(student: Student) {
        File(filePath).appendText("\n${student.toString()}")
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


