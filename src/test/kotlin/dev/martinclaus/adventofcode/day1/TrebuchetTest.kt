package dev.martinclaus.adventofcode.day1

import dev.martinclaus.adventofcode.readLines
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream
import kotlin.test.assertEquals

class TrebuchetTest {
    @ParameterizedTest
    @ArgumentsSource(PartIIArgumentsProvider::class)
    fun `check part II`(input: List<String>, expected: Int) {
        val actual = partII(input)

        assertEquals(expected, actual)
    }

}

class PartIIArgumentsProvider : ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext): Stream<out Arguments> = Stream.of(
        Arguments.of(
            """
            two1nine
            eightwothree
            abcone2threexyz
            xtwone3four
            4nineeightseven2
            zoneight234
            7pqrstsixteen
            """.trimIndent().lines(),
            281
        ),
        Arguments.of(
            "trebuchet.txt".readLines(),
            54094
        )
    )
}