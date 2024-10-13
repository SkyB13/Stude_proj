// Суперкласс PersonInfo для хранения общей информации
open class PersonInfo(
    val surname: String,
    val initials: String,
    val contact: String
) {
    override fun toString(): String {
        return "$surname $initials, $contact"
    }
}

// Класс Student наследует от PersonInfo
class Student(
    val id: Int,
    surname: String,
    initials: String,
    contact: String
) : PersonInfo(surname, initials, contact) {
    override fun toString(): String {
        return "Student(id=$id, surname='$surname', initials='$initials', contact='$contact')"
    }
}

// Класс Student_short наследует от PersonInfo
class Student_short : PersonInfo {
    val id: Int

    // Конструктор, принимающий объект класса Student
    constructor(student: Student) : super(student.surname, student.initials, student.contact) {
        this.id = student.id
    }

    // Конструктор, принимающий ID и строку с информацией
    constructor(id: Int, info: String) : super(
        surname = info.split(",")[0].trim(),
        initials = info.split(",")[1].trim(),
        contact = info.split(",")[2].trim()
    ) {
        this.id = id
    }

    override fun toString(): String {
        return "Student_short(id=$id, surname='$surname', initials='$initials', contact='$contact')"
    }
}
