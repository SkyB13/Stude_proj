class Student_short(
    val id: Int,
    val surname: String,
    val initials: String,
    val contact: String
) {
    // Конструктор, принимающий объект класса Student
    constructor(student: Student) : this(
        id = student.id,
        surname = student.surname,
        initials = student.initials,
        contact = student.contact
    )

    // Конструктор, принимающий ID и строку с информацией
    constructor(id: Int, info: String) : this(
        id = id,
        surname = info.split(",")[0].trim(),
        initials = info.split(",")[1].trim(),
        contact = info.split(",")[2].trim()
    )

    override fun toString(): String {
        return "Student_short(id=$id, surname='$surname', initials='$initials', contact='$contact')"
    }
}

// Предположим, у нас есть класс Student для использования в первом конструкторе
class Student(
    val id: Int,
    val surname: String,
    val initials: String,
    val contact: String
)