package main.kotlin

class Data_table(val data: List<List<Any?>>) {
    fun getElementByNumber(row: Int, column: Int): Any? {
        return data.getOrNull(row)?.getOrNull(column)
    }

    fun getRows(): Int {
        return data.size
    }

    fun getColumns(): Int {
        return data[0].size
    }
}