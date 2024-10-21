import java.time.LocalDate
import java.util.UUID
import java.util.regex.Pattern

class Student {
    var id: UUID = UUID.randomUUID()
        private set

    var birthDate: LocalDate = LocalDate.now()
        set(value) {
            field = value
        }

    var lastName: String = ""
        set(value) {
            if (value.isBlank()) throw IllegalArgumentException("Поле [Фамилия] не может быть пустым")
            field = value
        }

    var firstName: String = ""
        set(value) {
            if (value.isBlank()) throw IllegalArgumentException("Поле [Имя] не может быть пустым")
            field = value
        }

    var middleName: String? = null
        set(value) {
            field = value
        }

    var telPhone: String = ""
        set(value) {
            if (!isValidPhone(value)) {
                throw IllegalArgumentException("Неверный формат номера телефонв")
            }
            field = value
        }

    var email: String? = null
        set(value) {
            validateEmail(value)
            field = value
        }

    var git: String? = null
        set(value) {
            validateGit(value)
            field = value
        }

    var telegram: String? = null
        set(value) {
            validateTelegram(value)
            field = value
        }

    override fun toString(): String {
        return """
            Student:
            id: $id, 
            birthDate: $birthDate, 
            lastName: '$lastName', 
            firstName: '$firstName', 
            middleName: '${middleName ?: ""}', 
            phone: '$telPhone', 
            telegram: '${telegram ?: ""}', 
            email: '${email ?: ""}', 
            git: '${git ?: ""}')
        """.trimIndent()
    }

    companion object {
        fun isValidPhone(phone: String?): Boolean {
            val cleanedPhone = phone?.replace("[^+\\d]".toRegex(), "")
            return cleanedPhone != null && "^\\+?\\d{1,3}?[-\\.\\s]?\\(?(\\d{3})\\)?[-\\.\\s]?(\\d{3})[-\\.\\s]?(\\d{4,9})$".toRegex()
                .matches(cleanedPhone)
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

        fun validateTelegram(telegram: String?) {
            if (!telegram.isNullOrEmpty() && !Pattern.matches("[a-zA-Z0-9_]+", telegram)) {
                throw IllegalArgumentException("Неверный формат Telegram")
            }
        }
    }
}
