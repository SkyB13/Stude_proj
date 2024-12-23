package main.kotlin

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.util.*

class Students_list_DB private constructor() {
    companion object {

        @Volatile
        private var INSTANCE: Students_list_DB? = null

        fun getInstance(): Students_list_DB = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Students_list_DB().also { INSTANCE = it }
        }
    }
    private lateinit var connection: Connection

    init {
        try {
            connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/Students",
                "postgres",
                "2025"
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun executeQuery(query: String): ResultSet? {
        return try {
            val stmt = connection.createStatement()
            stmt.executeQuery(query)
        } catch (e: Exception) {
            // e.printStackTrace()
            null
        }
    }

    // Получение студента по ID
    fun getStudentById(id: Int) {
        val res = executeQuery("SELECT * FROM student WHERE id = ${id};")
        if (res != null) {
            while (res.next()) {
                for (stud in 1..res.metaData.columnCount) print("${res.getString(stud)}\t")
                println()
            }
        }
    }

    // Получение подсписка студентов
    fun get_k_n_Student_Short(k: Int, n: Int): MutableList<Student_short> {
        val res = executeQuery("Select * FROM student WHERE id > ${k * n} ORDER BY id LIMIT ${n};")
        val studList = mutableListOf<Student_short>()
        res?.let {
            while (it.next()) {
                val input = (2..it.metaData.columnCount).joinToString(" ") { index -> it.getString(index) }
                studList.add(Student_short(Student(input)))
            }
        }
        return studList
    }

    fun addStudent(student: Student) {
        val input = buildString {
            append("'${student.lastName}', '${student.firstName}', '${student.middleName}'")
            append(", ${student.phone?.let { "'$it'" } ?: "NULL"}")
            append(", ${student.telegram?.let { "'$it'" } ?: "NULL"}")
            append(", ${student.email?.let { "'$it'" } ?: "NULL"}")
            append(", ${student.git?.let { "'$it'" } ?: "NULL"}")
        }
        executeQuery("INSERT INTO student (lastName, firstName, middleName, phone, telegram, email, git) VALUES ($input);")
    }

    fun deleteStudent(id: Int) {
        executeQuery("DELETE FROM student WHERE id=${id};")
    }

    fun getCount(): Int {
        val res = executeQuery("SELECT COUNT(*) FROM student;")
        return if (res?.next() == true) res.getInt("count")
        else 0
    }

    fun replaceStudent(id: Int, student: Student) {
        val input = buildString {
            append("'${student.lastName}', '${student.firstName}', '${student.middleName}'")
            append(", ${student.phone?.let { "'$it'" } ?: "NULL"}")
            append(", ${student.telegram?.let { "'$it'" } ?: "NULL"}")
            append(", ${student.email?.let { "'$it'" } ?: "NULL"}")
            append(", ${student.git?.let { "'$it'" } ?: "NULL"}")
        }

        executeQuery("UPDATE student SET (lastName, firstName, middleName, phone, telegram, email, git) = ($input) WHERE id = $id;")
    }
}

fun main() {
    val db = Students_list_DB.getInstance()
    val student1 = Student(
        _lastName = "Сидоренко",
        _firstName = "Евгений",
        _middleName = "Евгеньевич",
        _phone = "+78005553535",
        _telegram = "@chenya",
        _email = "chenya.sidr@mail.ru",
        _git = "https://github.com/stud_Sidr"
    )

    //db.addStudent(student1)
    db.deleteStudent(1)
    //println(db.getCount())
    //db.replaceStudent(8, student1)
}
