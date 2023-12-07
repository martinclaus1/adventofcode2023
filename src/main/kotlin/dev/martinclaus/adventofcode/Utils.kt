package dev.martinclaus.adventofcode

private val classLoader = object {}.javaClass.classLoader
fun readText(filename: String): String {
    return classLoader.getResource(filename)?.readText().orEmpty()
}