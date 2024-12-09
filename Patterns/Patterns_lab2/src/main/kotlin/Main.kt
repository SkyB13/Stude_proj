import main.kotlin.*
import main.kotlin.Student_list_txt
import main.kotlin.StudentManager

fun main() {
      val manager = StudentManager(Student_list_txt())
//    val student_txt = Student("Петров", "Иван", "Иванович", "1234567890", null, null, null)
//    manager.addStudent(student_txt)
//    println("Студенты из TXT: ")
//    manager.loadStudents().forEach { println(it) }
//
//    print("\n")
//    manager.setStrategy(JsonStudentStorageStrategy())
//    val student_json = Student("Петров", "Пётр", "Петрович", "0987654321", null, null, null)
//    manager.addStudent(student_json)
//    println("Студенты из JSON:")
//    manager.loadStudents().forEach { println(it) }
//
//    print("\n")
    manager.setStrategy(Student_list_YAML())
//    val student_yaml = Student("Сидоров", "Пётр", "Петрович", "0987654321", null, "sidor_pp@mail.ru", null)
//    manager.addStudent(student_yaml)
//    println("Студенты из YAML:")
//    manager.loadStudents().forEach { println(it) }

    val student_yaml_upd = Student("Сидоров", "Пётр", "Максимович", "0987654321", "@sidmaks", "sidor_pp@mail.ru", null)
    manager.updateStudent(2, student_yaml_upd)
    print("\n")
    println("Обновление студента из YAML:")
    manager.loadStudents().forEach { println(it) }
}
