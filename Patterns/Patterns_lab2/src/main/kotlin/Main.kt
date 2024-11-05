import main.kotlin.Data_table
import main.kotlin.Data_list_student_short
import main.kotlin.Student
import main.kotlin.Student_short

fun main() {
    val student1 = Student(
        "Спиридонов",
        "Данил",
        "Александрович",
        "+79887564587",
        "@streleckiio",
        "streleckiio@example.com",
        "https://github.com/SoldDA"
    )

    val student2 = Student(
        "Иванова",
        "Виктория",
        "Александровна"
    )

    println("Информация о студентах:")
    println(student1.toString() + "\n")
    println(student2.toString() + "\n")
    student2.setContacts("12345678910", "@kashpo", "kashpo@mail.ru")
    println(student2.toString() + "\n")

    // laba_2

    val studentString = "Спиридонов Данил Александрович 79833256191 @danya_sp streleckiy.io@mail.ru https://github.com/SolDA"

    val student = Student(studentString)
    println(student.toString() + "\n")
    println(student.getInfo())
    println("\nРазделение на составляющие:")
    println("Full Name: ${student.getFullName()}")
    println("GitHub Link: ${student.getGitLink()}")
    println("Contact Info: ${student.getContactInfo()}\n")

    var Danil_short = Student_short(student1)
    println(Danil_short.toString())

    println("\nЗапись и чтение")
    var studentList = Student.readFile("D:/Pattern/Lab2/src/input.txt")
    for (stud in studentList) println(student)
    Student.writeFile("D:/Pattern/Lab2/src/output.txt", studentList)

    println("\nData_Table\n")
    var elem = Data_table(mutableListOf(mutableListOf(15, 23, 8), mutableListOf(16, 24, 9), mutableListOf("One", "Two", "Three")))
    println(elem.getElementByNumber(0, 1))
    println(elem.getRows())
    println(elem.getColumns())
    var new_list_student = Data_list_student_short(mutableListOf(Student_short(student1), Student_short(student2), Student_short(studentString)))
    new_list_student.select(1)
    new_list_student.select(2)
    var new_table_student = new_list_student.getTable()
    for (i in 0..new_table_student.getRows() - 1) {
        for (j in 0..new_table_student.getColumns() - 1) {
            print(new_table_student.getElementByNumber(i, j))
            println(" ")
        }
        println()
    }
}