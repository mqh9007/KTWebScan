package cn.mqh9007.ktwebscan

fun main(args: Array<String>) {

    val list = mutableListOf(1, 2, 3, 4)
    list.add(4)

    list.filter { it == 3 }.forEach(::println)

    list.map { it + 1000 }.forEach(::println)

    println(list.fold(0) { acc, i -> acc + i })

    println(mutableListOf(1 to "a", 2 to "b").toMap())

    val listT = listOf(
        listOf(1, 2, 3),
        listOf(1, 2, 3),
        listOf(1, 2, 3),
        listOf(1, 2, 3)
    )

    listT.flatMap { it.asIterable() }.forEach(::print)

}
