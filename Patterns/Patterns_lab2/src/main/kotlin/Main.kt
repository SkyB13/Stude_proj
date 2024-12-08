import main.kotlin.*
import main.kotlin.Student_list_YAML
import main.kotlin.JsonStudentStorageStrategy
import main.kotlin.Student_list_txt

fun main() {
    // ������ � YAML ������
    val yamlStudents = Student_list_YAML("students.yaml")

    println("�������� �� YAML:")
    yamlStudents.students.forEach { println(it) }

    // ���������� ������ �������� � YAML
    val student_yaml = Student("������", "����", "��������", "1234567890", null, null, null)
    student_yaml.id = 1
    yamlStudents.addStudent(student_yaml)


    // ���������� ��������� � YAML
    yamlStudents.saveToFile()

    println("\n")

    // ������ � JSON ������
    val jsonStrategy = JsonStudentStorageStrategy()
    val jsonManager = StudentManager(jsonStrategy)

    println("�������� �� JSON:")
    jsonManager.loadStudents().forEach { println(it) }

    // ���������� ������ �������� � JSON
    jsonManager.addStudent(Student_list_json(1, "������", "ϸ��", "��������", "0987654321", null, null, null))

    println("\n")

    // ������ � TXT ������
    val txtStudents = Student_list_txt()

    println("�������� �� TXT:")
    txtStudents.loadStudents().forEach { println(it) }

    // ���������� ������ �������� � TXT
    val student_txt = Student("������", "����", "��������", "1234567890", null, null, null)
    student_txt.id = 1
    yamlStudents.addStudent(student_txt)

    println("\n")

    // ������������ �������������� ������� YAML
    println("������� �� ID �� YAML:")
    yamlStudents.getStudentById(1)?.let { println(it) }

    println("\n�������� 1 �� 2 ��������� � YAML:")
    val shortList = yamlStudents.get_k_n_student_short_list(1, 2)
    shortList.data.forEach { println(it.joinToString(", ")) }

    println("\n���������� ��������� � YAML:")
    yamlStudents.sortStudents()
    yamlStudents.students.forEach { println(it) }

    println("\n���������� ��������� � YAML:")
    println(yamlStudents.get_student_short_count())

    println("\n���������� �������� � YAML:")
    val updatedStudent = Student(
        // ID
        "������", // �������
        "����", // ���
        "��������", // ��������
        "1234567890", // �������
        "ivanov_ii@university.ru", // E-mail (���������)
        null, // Telegram (��������� null)
        null // GitHub (��������� null)
    )
    yamlStudents.updateStudent(1, updatedStudent)
    yamlStudents.students.forEach { println(it) }


    println("\n�������� �������� �� YAML:")
    yamlStudents.removeStudentById(1)
    yamlStudents.students.forEach { println(it) }
}
