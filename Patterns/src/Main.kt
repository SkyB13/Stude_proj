import java.time.LocalDate
import java.util.*
import java.util.regex.Pattern

fun main() {
    val student1 = Student(UUID.randomUUID(), LocalDate.of(2000, 1, 1), "Иванов", "Иван", "Петрович", "+79991234567")
    val student2 = Student(UUID.randomUUID(), LocalDate.of(2001, 12, 31), "Сидоров", "Александр", phone = "+79876543210", telegram = "@sidorov")

    println(student1.toString())
    println(student2.toString())
}


