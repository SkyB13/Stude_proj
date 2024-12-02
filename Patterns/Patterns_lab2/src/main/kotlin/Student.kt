package main.kotlin
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

class Student : StudentInfo {
    var lastName: String = ""
        set(value) {
            if (isValidNames(value)) field = value
            else field = ""
        }
        get() {
            return field
        }
    var firstName: String = ""
        set(value) {
            if (isValidNames(value)) field = value
            else field = ""
        }
        get() {
            return field
        }
    var middleName: String = ""
        set(value) {
            if (isValidNames(value)) field = value
            else field = ""
        }
        get() {
            return field
        }
    var phone: String? = null
        set(value) {
            if (isValidPhone(value)) field = value
        }
        get() {
            return field
        }
    var telegram: String? = null
        set(value) {
            if (isValidTelegram(value)) field = value
        }
        get() {
            return field
        }
    var email: String? = null
        set(value) {
            if (isValidEmail(value)) field = value
        }
        get() {
            return field
        }

    companion object {
        fun readFile(path: String): MutableList<Student> {
            val file = File(path)
            var result = mutableListOf<Student>()
            var text: List<String> = listOf()

            try {
                text = file.readLines()
                println(text)
            }
            catch (e: FileNotFoundException) {
                println("Файл не найден")
            }
            catch (e: IOException) {
                println("Ошибка чтения файла")
            }

            for (line in text) result.add(Student(line))
            return result
        }

        fun writeFile(path: String, sList: MutableList<Student>) {
            val file = File(path)
            var text = ""

            for (student in sList) text += (student.toString() + "\n")
            file.writeText(text)
        }
    }

    override fun toString(): String {
        return """
            ID: $id
            Фамилия: $lastName
            Имя: $firstName
            Отчество: ${middleName}
            Телефон: ${phone}
            Telegram: ${telegram}
            E-mail: ${email}
            GitHub: ${git}
        """.trimIndent()
    }

    fun validate(): Boolean {
        return hasGit() && hasAnyContact()
    }

    private fun hasGit(): Boolean {
        return !git.isNullOrEmpty()
    }

    private fun hasAnyContact(): Boolean {
        return !phone.isNullOrEmpty() || !email.isNullOrEmpty() || telegram.isNullOrEmpty()
    }

    fun setContacts(Phone: String? = null, Telegram: String? = null, Mail: String? = null) {
        if (Phone != null && isValidPhone(Phone)) phone = Phone
        if (Telegram != null && isValidTelegram(Telegram)) telegram = Telegram
        if (Mail != null && isValidEmail(Mail)) email = Mail
    }

    constructor(LastName: String, FirstName: String, MiddleName: String) {
        id = id_student
        lastName = LastName
        firstName = FirstName
        middleName = MiddleName
    }

    constructor(LastName: String, FirstName: String, MiddleName: String, Phone: String? = null, Telegram: String? = null, Email: String? = null, GitHub:String? = null) {
        id = id_student
        lastName = LastName
        firstName = FirstName
        middleName = MiddleName
        phone = Phone
        telegram = Telegram
        email = Email
        git = GitHub
    }

    constructor(hashStudent: HashMap<String, Any?>) {
        id = id_student
        lastName = hashStudent["lastname"].toString()
        firstName = hashStudent["firstName"].toString()
        middleName = hashStudent["middleName"].toString()
        phone = hashStudent.getOrDefault("phone", null).toString()
        telegram = hashStudent.getOrDefault("telegram", null).toString()
        email = hashStudent.getOrDefault("email", null).toString()
        git = hashStudent.getOrDefault("github", null).toString()
    }

    // Конструктор принимающий строку и парсит её
    constructor(input:String): this (input.split(" ")[0],input.split(" ")[1],input.split(" ")[2],input.split(" ").getOrNull(3),input.split(" ").getOrNull(4),input.split(" ").getOrNull(5),input.split(" ").getOrNull(6))
    {

    }

    // Метод GetInfo
    fun getInfo(): String {
        val fullname = getFullName()
        val gitHubLink = getGitLink()
        val contactInfo = getContactInfo()
        return "$fullname; $gitHubLink; $contactInfo"
    }

    fun getFullName(): String {
        return lastName + " " + firstName[0] + "." + middleName[0] + ". "
    }

    fun getGitLink(): String {
        return git?.let { "GitHub: $it" } ?: ""
    }

    fun getContactInfo(): String {
        return listOfNotNull(
            phone?.let { "Телефон: $it" },
            telegram?.let { "Телеграмм: $it" },
            email?.let { "Почта: $it" }
        ).joinToString(", ")
    }
}