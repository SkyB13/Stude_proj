import java.time.LocalDate
import java.util.UUID
import java.util.regex.Pattern

class Student(
    val id: UUID,
    val birthDate: LocalDate,
    val lastName: String,
    val firstName: String,
    val middleName: String? = null,
    var phone: String? = null,
    var telegram: String? = null,
    var email: String? = null,
    var git: String? = null
) {
    init {
        require(lastName.isNotBlank()) { "Фамилия не может быть пустой" }
        require(firstName.isNotBlank()) { "Имя не может быть пустым" }
        validatePhone(phone)
        validateEmail(email)
        validateGit(git)
    }

    private fun validatePhone(phone: String?) {
        if (!phone.isNullOrEmpty() && !Pattern.matches("\\+\\d{11}", phone)) {
            throw IllegalArgumentException("Неверный формат телефона. Пример: +79991234567")
        }
    }

    private fun validateEmail(email: String?) {
        if (!email.isNullOrEmpty() && !Pattern.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", email)) {
            throw IllegalArgumentException("Неверный формат email")
        }
    }

    private fun validateGit(git: String?) {
        if (!git.isNullOrEmpty() && !Pattern.matches("https://github\\.com/[a-zA-Z0-9_-]+", git)) {
            throw IllegalArgumentException("Неверный формат ссылки на GitHub")
        }
    }

    var telPhone: String? = null
        set(value) {
            validatePhone(value)
            field = value
        }
        get() = field

    fun setTelegram(value: String?) {
        telegram = value
    }

    fun setEmail(value: String?) {
        email?.let { validateEmail(it) }
        email = value
    }

    fun setGit(value: String?) {
        git?.let { validateGit(it) }
        git = value
    }

    override fun toString(): String {
        return """
            Student(id=$id, birthDate=$birthDate, lastName='$lastName', firstName='$firstName', middleName=${middleName ?: ""}, 
             phone=${phone ?: ""}, telegram=${telegram ?: ""}, email=${email ?: ""}, git=${git ?: ""})
        """.trimIndent()
    }

    companion object {
        fun isValidPhone(phone: String?): Boolean {
            return phone == null || Pattern.matches("\\+\\d{11}", phone)
        }

        fun validateEmail(email: String?) {
            if (!email.isNullOrEmpty() && !Pattern.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", email)) {
                throw IllegalArgumentException("Неверный формат email")
            }
        }

        fun validateGit(git: String?) {
            if (!git.isNullOrEmpty() && !Pattern.matches("https://github\\.com/[a-zA-Z0-9_-]+", git)) {
                throw IllegalArgumentException("Неверный формат ссылки на GitHub")
            }
        }

        fun validate(student: Student): Boolean {
            return student.git != null || (student.phone != null && isValidPhone(student.phone)) ||
                    (student.email != null && validateEmail(student.email) == Unit) ||
                    (student.telegram != null)
        }
    }

    fun setContacts(phone: String? = this.phone, telegram: String? = this.telegram,
                    email: String? = this.email, git: String? = this.git) {
        validatePhone(phone)
        validateEmail(email)
        validateGit(git)

        this.phone = phone
        this.telegram = telegram
        this.email = email
        this.git = git
    }
}
