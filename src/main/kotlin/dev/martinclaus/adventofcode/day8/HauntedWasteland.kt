package dev.martinclaus.adventofcode.day8

import dev.martinclaus.adventofcode.printSolution
import dev.martinclaus.adventofcode.readLines

/**
 * @see <a href="https://adventofcode.com/2023/day/8">Advent of Code 2023 Day 8</a>
 */
fun main() {
    val input = "haunted-wasteland.txt".readLines()
    printSolution(
        8,
        "Haunted Wasteland",
        "How many steps are required to reach ZZZ? ${partI(input)}",
        "How many steps does it take before you're only on nodes that end with Z? ${partII(input)}",
    )
}


private val pattern = "([A-Z0-9]+)".toRegex().toPattern()

fun partI(lines: List<String>): Int {
    val steps = lines.first()
    val map = lines.drop(2).associate { line ->
        val (from, left, right) = pattern.matcher(line).results().map { it.group() }.toList()
        from to (left to right)
    }

    var current = "AAA"
    var count = 0
    while (current != "ZZZ") {
        steps.forEach {
            val (left, right) = map[current]!!
            current = if (it == 'R') right else left
        }
        count += steps.length
    }

    return count
}

fun partII(lines: List<String>): Long {
    val steps = lines.first()
    val map = lines.drop(2).associate { line ->
        val (from, left, right) = pattern.matcher(line).results().map { it.group() }.toList()
        from to (left to right)
    }
    val counts = map.keys.filter { it.endsWith("A") }.map { startingPoint ->
        var current = startingPoint
        var count = 0L
        while (!current.endsWith("Z")) {
            steps.forEach {
                val (left, right) = map[current]!!
                current = if (it == 'R') right else left
            }
            count += steps.length
        }
        count
    }
    return counts.reduce { acc, i -> leastCommonMultiple(acc, i) }
}

private fun leastCommonMultiple(a: Long, b: Long): Long {
    return a * b / greatestCommonDivisor(a, b)
}

private tailrec fun greatestCommonDivisor(a: Long, b: Long): Long {
    return if (b == 0L) a
    else greatestCommonDivisor(b, a % b)
}