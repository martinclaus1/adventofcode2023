package dev.martinclaus.adventofcode.day9

import dev.martinclaus.adventofcode.printSolution
import dev.martinclaus.adventofcode.readLines

fun main() {
    val input = "mirage-maintenance.txt".readLines()
    printSolution(
        9,
        "Mirage Maintenance",
        "What is the sum of these extrapolated values? ${partI(input)}",
        "What is the sum of these extrapolated values? ${partII(input)}",
    )
}

fun partI(lines: List<String>): Int = lines.sumOf {
    buildHistory(it).extrapolateNextElement()
}

fun partII(lines: List<String>): Int = lines.sumOf {
    buildHistory(it).extrapolatePreviousElement()
}

private fun buildHistory(it: String): List<List<Int>> {
    val values = it.split(" ").map { value -> value.toInt() }
    val target = mutableListOf<List<Int>>()
    target.add(values)
    var next = values
    while (next.any { c -> c != 0 }) {
        next = next.zipWithNext { a, b -> b - a }
        target.add(next)
    }
    return target
}

private fun List<List<Int>>.extrapolateNextElement() = sumOf { lastItem -> lastItem.last() }

private fun List<List<Int>>.extrapolatePreviousElement() =
    map { item -> item.first() }.reversed().reduce { acc, i -> i - acc }