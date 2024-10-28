package main.kotlin
import java.time.LocalDate
import java.util.UUID
import java.util.regex.Pattern

class Student {
    var id: UUID = UUID.randomUUID()
        private set

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
    var git: String? = null
        set(value) {
            if (isValidGit(value)) field = value
        }
        get() {
            return field
        }

    companion object {
        private fun isValidPhone(phone: String?): Boolean {
            return phone?.matches(Regex("^\\+?\\d{11}$")) ?: true
        }
        private fun isValidEmail(email: String?): Boolean {
            return email?.matches(Regex("^[a-zA-Z0-9.%_+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$")) ?: true
        }
        private fun isValidGit(github: String?): Boolean {
            return github?.matches(Regex("^https://github\\.com/[a-zA-Z0-9_-]+/?\$")) ?: true
        }
        private fun isValidNames(value: String): Boolean {
            return value.matches(Regex("[А-Я]{1}[а-я]*"))
        }
        private fun isValidTelegram(value: String?): Boolean {
            return value?.matches(Regex("""\@{1}.*""")) ?: true
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
            Email: ${email}
            Git: ${git}
        """.trimIndent()
    }

    fun validate(): Boolean {
        return hasGit() && hasAnyContact()
    }

    //Hash-task
    private fun hasGit(): Boolean {
        return !git.isNullOrEmpty()
    }

    private fun hasAnyContact(): Boolean {
        return !phone.isNullOrEmpty() || !email.isNullOrEmpty() || telegram.isNullOrEmpty()
    }

    fun setContacts(Phone: String? = null, Telegram: String? = null, Mail: String? = null) {
        if (Phone != null) phone = Phone
        if (Telegram != null) telegram = Telegram
        if (Mail != null) email = Mail
    }

    constructor(LastName:String, FirstName:String, MiddleName:String) {
        lastName = LastName
        firstName = FirstName
        middleName = MiddleName
    }

    constructor(LastName: String, FirstName: String, MiddleName: String, Phone: String? = null, Telegram: String? = null, Email: String? = null, GitHub:String? = null) {
        lastName = LastName
        firstName = FirstName
        middleName = MiddleName
        phone = Phone
        telegram = Telegram
        email = Email
        git = GitHub
    }

    //Hash-task
    constructor(hashStudent: HashMap<String, Any?>) {
        lastName = hashStudent["lastname"].toString()
        firstName = hashStudent["firstName"].toString()
        middleName = hashStudent["middleName"].toString()
        phone = hashStudent.getOrDefault("phone", null).toString()
        telegram = hashStudent.getOrDefault("telegram", null).toString()
        email = hashStudent.getOrDefault("email", null).toString()
        git = hashStudent.getOrDefault("github", null).toString()
    }
}
