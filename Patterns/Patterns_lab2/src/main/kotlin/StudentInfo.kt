package main.kotlin

open class StudentInfo {
    var id: Int = -1
        set(value) {
            if (value > 0) field = value
        }
        get() {
            return field
        }
    var git: String? = null
        set(value) {
            field = value
        }
        get() {
            return field
        }
    companion object {
        var id_student = 0

        fun isValidPhone(phone: String?): Boolean {
            return phone?.matches(Regex("^\\+?\\d{11}$")) ?: true
        }
        fun isValidEmail(email: String?): Boolean {
            return email?.matches(Regex("^[a-zA-Z0-9.%_+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$")) ?: true
        }
        fun isValidGit(github: String?): Boolean {
            return github?.matches(Regex("^https://github\\.com/[a-zA-Z0-9_-]+/?\$")) ?: true
        }
        fun isValidNames(value: String): Boolean {
            return value.matches(Regex("[À-ß]{1}[à-ÿ]*"))
        }
        fun isValidTelegram(value: String?): Boolean {
            return value?.matches(Regex("""\@{1}.*""")) ?: true
        }
    }
    init {
        id_student++
    }
}