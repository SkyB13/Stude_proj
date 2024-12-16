package main.kotlin

open class Data_list<T> (val data: List<T>) {
    private var choice: MutableList<Int> = mutableListOf()

    fun select(index: Int) {
        if (index < data.size) choice.add(index)
    }

    fun getSelected(): MutableList<Int> { return choice }

    open fun getNames(): Array<String> {
        return arrayOf("1", "2")
    }

    open fun getDataRows(): MutableList<MutableList<Any?>> {
        return mutableListOf(mutableListOf<Any?>("1", "2", 123))
    }

    fun getTable(): Data_table {
        var Names = getNames()
        var Args = getDataRows()
        Args[0] = Names.toMutableList()
        return Data_table(Args)
    }
}