package main.kotlin

class Student_short : StudentInfo {
    var fullName: String? = null
        set(value) {
            field = value
        }
        get() {
            return field
        }
    var contact: String? = null
        set(value) {
            field = value
        }
        get() {
            return field
        }

    constructor(student: Student) {
        id = id
        fullName = student.getFullName()
        git = student.getGitLink()
        contact = student.getContactInfo()
    }

    constructor(input: String) {
        id = id_student
        fullName = input.split(" ").getOrNull(0)
        git = input.split(" ").getOrNull(1)
        contact = input.split(" ").getOrNull(2)
    }

    override fun toString(): String {
        return """
            ID: $id
            ФИО: $fullName
            GitHub: $git
            Контакт: $contact
        """.trimIndent()
    }
}