import main.kotlin.Student

fun main() {
    val student = Student(
        "Иванов",
        "Иван",
        "Петрович",
        "+799912цц34567",
        "@ivanovan",
        "ivanov@gmail.com",
        "https://github.com/ivanov"
    )
    println(student.toString())
    println("Проверка валидации:")
    println("Запись 1: ${student.validate()}")
}
