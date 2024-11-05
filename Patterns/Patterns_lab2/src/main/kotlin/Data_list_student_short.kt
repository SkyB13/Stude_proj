package main.kotlin

class Data_list_student_short(List: List<Student_short>): Data_list<Student_short>(List) {
    override fun getNames(): Array<String> {
        return arrayOf("№", "ФИО", "GitHub", "Контакты")
    }

    override fun getDataRows(): MutableList<MutableList<Any?>> {
        var Args = mutableListOf<MutableList<Any?>>()
        Args.add(mutableListOf())
        var Counter = 1
        for (row in data) {
            Args.add(mutableListOf(Counter, row.fullName, row.git, row.contact))
            Counter++
        }
        return Args
    }
}