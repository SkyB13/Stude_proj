package main.kotlin

interface Student_Strategy<T : Any> {
    fun loadStudents(): List<T>
    fun saveStudents(students: List<Any>)
    fun addStudent(student: T)
    fun removeStudent(id: Int)
    fun updateStudent(id: Int, student: T)
}

class StudentManager<T : Any>(private val strategy: Student_Strategy<T>) {
    fun loadStudents(): List<T> = strategy.loadStudents()
    fun saveStudents(students: List<T>) = strategy.saveStudents(students)
    fun addStudent(student: T) = strategy.addStudent(student)
    fun removeStudent(id: Int) = strategy.removeStudent(id)
    fun updateStudent(id: Int, student: T) = strategy.updateStudent(id, student)
}
