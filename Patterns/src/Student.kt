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
}
