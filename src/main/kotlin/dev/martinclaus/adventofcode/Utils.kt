package dev.martinclaus.adventofcode

private val classLoader = object {}.javaClass.classLoader
fun readText(filename: String): String {
    return classLoader.getResource(filename)?.readText().orEmpty()
}

fun String.readLines(): List<String> = classLoader.getResource(this)?.readText()?.lines().orEmpty()

fun printSolution(
    day: Int,
    description: String,
    partI: String,
    partII: String
) = println(buildString { append("Day $day: $description\nPart I: $partI\nPart II: $partII") })