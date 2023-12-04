package dev.martinclaus.adventofcode.day1

import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream
import kotlin.test.assertEquals

class TrebuchetTest {
    private val calibrator = Trebuchet()

    @ParameterizedTest
    @ArgumentsSource(CalibrationTestProvider::class)
    fun `check that calibration works`(input: String, expected: Int) {
        val result = calibrator.calibrate(input)

        assertEquals(expected, result)
    }
}

class CalibrationTestProvider : ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext): Stream<out Arguments> {
        return Stream.of(
            getTestCase(
                input = """
                    two1nine
                    eightwothree
                    abcone2threexyz
                    xtwone3four
                    4nineeightseven2
                    zoneight234
                    7pqrstsixteen
                """.trimIndent(),
                expected = 281
            ),
            getTestCase(
                input = """
                    1abc2
                    pqr3stu8vwx
                    a1b2c3d4e5f
                    treb7uchet
                """.trimIndent(),
                expected = 142
            ),
            getTestCase("123", 13),
            getTestCase("1", 11),
            getTestCase("", 0),
            getTestCase("8threesevenfourgbgteight5twonenjr", 81),
            getTestCase("8threesevenfourgbgteight5twonenjrseven", 87),
        )
    }

    private fun getTestCase(input: String, expected: Int) = Arguments.of(input, expected)
}