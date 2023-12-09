package dev.martinclaus.adventofcode.day6

import dev.martinclaus.adventofcode.readLines
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

class BoatRaceTest {
    @ParameterizedTest
    @ArgumentsSource(PartIArgumentsProvider::class)
    fun `check part I`(lines: List<String>, expected: Int) {
        val actual = partI(lines)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @ArgumentsSource(PartIIArgumentsProvider::class)
    fun `check part II`(lines: List<String>, expected: Int) {
        val actual = partII(lines)

        assertEquals(expected, actual)
    }

}

class PartIArgumentsProvider : ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext): Stream<out Arguments> = Stream.of(
        Arguments.of(
            """
            Time:      7  15   30
            Distance:  9  40  200
            """.trimIndent().lines(),
            288
        ),
        Arguments.of(
            "boat-race.txt".readLines(),
            170000
        )
    )
}

class PartIIArgumentsProvider : ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext): Stream<out Arguments> = Stream.of(
        Arguments.of(
            """
            Time:      7  15   30
            Distance:  9  40  200
            """.trimIndent().lines(),
            71503
        ),
        Arguments.of(
            "boat-race.txt".readLines(),
            20537782
        )
    )
}