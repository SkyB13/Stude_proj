import main.kotlin.DatabaseManager

fun main() {
    val databaseManager = DatabaseManager()

    try {
        val students = databaseManager.executeSelectQuery()

        println("Students:")
        students.forEachIndexed { index, student ->
            println("${index + 1}. $student")
        }
    } catch (e: Exception) {
        println("Error occurred: ${e.message}")
    }
}
