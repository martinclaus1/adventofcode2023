package dev.martinclaus.adventofcode

import dev.martinclaus.adventofcode.day1.PuzzleCalibrator

fun main() {
    val calibrator = PuzzleCalibrator()
    val calibrationInput = calibrator::class.java.classLoader.getResource("calibration-puzzle.txt")?.readText()
    println("Day 1: Calibration result: ${calibrator.calibrate(calibrationInput.orEmpty())}")
}