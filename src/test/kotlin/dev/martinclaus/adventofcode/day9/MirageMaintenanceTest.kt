package dev.martinclaus.adventofcode.day9

import dev.martinclaus.adventofcode.readLines
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream
import java.util.stream.Stream.of

class MirageMaintenanceTest {
    @ParameterizedTest
    @ArgumentsSource(PartIArgumentsProvider::class)
    fun `check part I`(input: List<String>, expected: Int) {
        val actual = partI(input)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @ArgumentsSource(PartIIArgumentsProvider::class)
    fun `check part II`(input: List<String>, expected: Int) {
        val actual = partII(input)

        assertEquals(expected, actual)
    }
}

class PartIArgumentsProvider : ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext): Stream<out Arguments> = of(
        Arguments.of(
            """
            0 3 6 9 12 15
            1 3 6 10 15 21
            10 13 16 21 30 45
            """.trimIndent().lines(),
            114
        ),
        Arguments.of(
            "mirage-maintenance.txt".readLines(),
            1641934234
        )
    )
}

class PartIIArgumentsProvider : ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext): Stream<out Arguments> = of(
        Arguments.of(
            """
            0 3 6 9 12 15
            1 3 6 10 15 21
            10 13 16 21 30 45
            """.trimIndent().lines(),
            2
        ),
        Arguments.of(
            "mirage-maintenance.txt".readLines(),
            975
        )
    )
}