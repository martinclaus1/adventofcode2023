package dev.martinclaus.adventofcode

import dev.martinclaus.adventofcode.day1.Trebuchet
import dev.martinclaus.adventofcode.day2.ConundrumSet
import dev.martinclaus.adventofcode.day2.CubeConundrum

fun main() {
    val classLoader = object {}.javaClass.classLoader

    val trebuchet = Trebuchet()
    val calibrationInput = classLoader.getResource("calibration-puzzle.txt")?.readText()
    println("Day 1: Calibration result: ${trebuchet.calibrate(calibrationInput.orEmpty())}")

    val cubeConundrum = CubeConundrum()
    val conundrumInput = classLoader.getResource("cube-conundrums.txt")?.readText()
    println(
        "Day 2: Cube conundrum result: ${
            cubeConundrum.solve(
                conundrumInput.orEmpty(),
                ConundrumSet(12, 13, 14)
            )
        }, power: ${cubeConundrum.calculatePower(conundrumInput.orEmpty())}"
    )
}