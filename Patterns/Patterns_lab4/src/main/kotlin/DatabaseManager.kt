package main.kotlin

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

class DatabaseManager {

    private val connectionString = "jdbc:postgresql://localhost:5432/Students"
    private val username = "postgres"
    private val password = "admin"

    fun executeSelectQuery(): List<Student> {
        var connection: Connection? = null
        var statement: Statement? = null
        var resultSet: ResultSet? = null

        try {
            Class.forName("org.postgresql.Driver")
            connection = DriverManager.getConnection(connectionString, username, password)

            statement = connection.createStatement()
            val query = "SELECT * FROM Students"
            resultSet = statement.executeQuery(query)

            val students = mutableListOf<Student>()
            while (resultSet?.next() == true) {
                students.add(
                    Student(
                        //resultSet.getInt("id"),
                        resultSet.getString("surname"),
                        resultSet.getString("name"),
                        resultSet.getString("phone"),
                        resultSet.getString("telegram"),
                        resultSet.getString("mail"),
                        resultSet.getString("git")
                    )
                )
            }

            return students
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException(e)
        } finally {
            closeResources(resultSet, statement, connection)
        }
    }


    private fun closeResources(vararg resources: AutoCloseable?) {
        resources.forEach { resource ->
            try {
                resource?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
