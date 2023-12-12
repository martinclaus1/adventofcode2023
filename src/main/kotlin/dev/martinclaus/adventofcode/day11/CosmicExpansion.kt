package dev.martinclaus.adventofcode.day11

import dev.martinclaus.adventofcode.printSolution
import dev.martinclaus.adventofcode.readLines
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    val input = "cosmic-expansion.txt".readLines()
    printSolution(
        11,
        "Cosmic Expansion",
        "What is the sum of these lengths? ${partI(input)}",
        "How many regions are present within the grid in your puzzle input? ${partII(input)}",
    )
}

fun partI(input: List<String>): Long {
    return calculate(input)
}

private fun calculate(input: List<String>, galaxyFactor: Long = 1): Long {
    val galaxies = input.mapIndexed { y, row ->
        row.mapIndexedNotNull { x, column ->
            if (column == '#') {
                Point(x, y)
            } else {
                null
            }
        }
    }.flatten()

    val expandedColumns = (0..(input[0].length)).filter { x -> (0..(input.size)).none { y -> Point(x, y) in galaxies } }
    val expandedRows = (0..(input.size)).filter { y -> (0..(input[0].length)).none { x -> Point(x, y) in galaxies } }

    val examples =
        galaxies.flatMapIndexed { index: Int, point: Point -> galaxies.drop(index + 1).map { to -> point to to } }

    return examples.sumOf { (from, to) ->
        val additionalColumns = (min(from.x, to.x)..max(from.x, to.x)).count { it in expandedColumns }
        val additionalRows = (min(from.y, to.y)..max(from.y, to.y)).count { it in expandedRows }

        from.manhattanDistanceTo(to) + (additionalColumns + additionalRows) * galaxyFactor
    }
}

fun partII(input: List<String>, factor: Long = 1_000_000L - 1): Long {
    return calculate(input, factor)
}

data class Point(val x: Int, val y: Int) {
    /**
     * https://www.baeldung.com/cs/minimal-manhattan-distance
     */
    fun manhattanDistanceTo(other: Point) = abs(x - other.x) + abs(y - other.y)
}

