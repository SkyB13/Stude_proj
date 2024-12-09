package main.kotlin

class StudentManager(private var strategy: Student_Strategy) {

    interface Student_Strategy {
        fun loadStudents(): List<Student>
        fun saveStudents(students: List<Student>)
        fun addStudent(student: Student)
        fun removeStudent(id: Int)
        fun updateStudent(id: Int, student: Student)
    }

    fun setStrategy(strategy: Student_Strategy) {
        this.strategy = strategy
    }

    fun loadStudents(): List<Student> {
        return strategy.loadStudents()
    }

    fun saveStudents(students: List<Student>) {
        strategy.saveStudents(students)
    }

    fun addStudent(student: Student) {
        strategy.addStudent(student)
    }

    fun removeStudent(id: Int) {
        strategy.removeStudent(id)
    }

    fun updateStudent(id: Int, student: Student) {
        strategy.updateStudent(id, student)
    }

    companion object {
        fun createJsonStrategy(): Student_Strategy {
            return JsonStudentStorageStrategy()
        }

        fun createTxtStrategy(): Student_Strategy {
            return Student_list_txt()
        }

        fun createYamlStrategy(): Student_Strategy {
            return Student_list_YAML()
        }
    }
}
