package dev.martinclaus.adventofcode.day10

import dev.martinclaus.adventofcode.printSolution
import dev.martinclaus.adventofcode.readLines

fun main() {
    val input = "pipe-maze.txt".readLines()
    printSolution(
        10,
        "Pipe Maze",
        "How many steps along the loop does it take to get from the starting position to the point farthest from the starting position? ${
            partI(
                input
            )
        }",
        "How many tiles are enclosed by the loop? ${partII(input)}",
    )
}

fun partI(input: List<String>): Int {
    return findPath(input).size / 2
}

private val pipesWithNorthEnd = setOf('|', 'L', 'J')

fun partII(input: List<String>): Int {
    val path = findPath(input)

    val count = input.mapIndexed { y, row ->
        var isInside = false
        val notFirstLine = y != 0
        val notLastLine = y <= input.size - 1
        row.mapIndexed { x, column ->
            val inLoop = Point(x, y) in path
            if (inLoop && notFirstLine && notLastLine && column in pipesWithNorthEnd) {
                isInside = !isInside
            }

            if (isInside && !inLoop) {
                1
            } else {
                0
            }
        }
    }.flatten().sum()

    return count
}

private fun findPath(input: List<String>): MutableSet<Point> {
    val grid = input.mapIndexed { y, row ->
        row.mapIndexed { x, column ->
            Point(x, y) to column
        }
    }.flatten().toMap()

    val start = grid.entries.first { it.value == 'S' }.key
    val unexplored = mutableListOf(start)
    val explored = mutableSetOf<Point>()
    while (unexplored.isNotEmpty()) {
        val currentPoint = unexplored.removeFirst()
        explored.add(currentPoint)
        val currentPipe = grid[currentPoint]
        pipes[currentPipe]!!.forEach { direction ->
            val nextPoint = currentPoint.move(direction)
            val alreadyVisited = nextPoint !in explored
            val isInGrid = grid.containsKey(nextPoint)
            if (alreadyVisited && isInGrid && direction.reverse() in pipes[grid[nextPoint]]!!) {
                unexplored.add(nextPoint)
            }
        }
    }
    return explored
}

private val pipes = mapOf(
    '.' to emptySet(),
    'S' to setOf(Direction.N, Direction.E, Direction.S, Direction.W),
    '|' to setOf(Direction.N, Direction.S),
    '-' to setOf(Direction.E, Direction.W),
    'L' to setOf(Direction.N, Direction.E),
    'J' to setOf(Direction.N, Direction.W),
    '7' to setOf(Direction.W, Direction.S),
    'F' to setOf(Direction.E, Direction.S),
)

enum class Direction {
    N, E, S, W;

    fun reverse() = when (this) {
        N -> S
        E -> W
        S -> N
        W -> E
    }
}

data class Point(val x: Int, val y: Int) {
    fun move(direction: Direction, distance: Int = 1) = when (direction) {
        Direction.E -> Point(x + distance, y)
        Direction.W -> Point(x - distance, y)
        Direction.N -> Point(x, y - distance)
        Direction.S -> Point(x, y + distance)
    }
}