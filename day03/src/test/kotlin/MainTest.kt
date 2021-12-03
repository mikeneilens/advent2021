import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {
    val sampleData = """
        00100
        11110
        10110
        10111
        10101
        01111
        00111
        11100
        10000
        11001
        00010
        01010
    """.trimIndent().split("\n")
    @Test
    fun `most common value in first column of sample data is 1`() {
        assertEquals('1',mostCommonBitInColumn(sampleData,0))
    }
    @Test
    fun `most common value in 2nd column of sample data is 0`() {
        assertEquals('0',mostCommonBitInColumn(sampleData,1))
    }
    @Test
    fun `10110 as int is 22`() {
        assertEquals(22, "10110".binToInt())
    }
    @Test
    fun `gamma of sample data is 10110`() {
        assertEquals("10110",gamma(sampleData))
    }
    @Test
    fun `invert of 10110 is 01001`() {
        assertEquals("01001",invert("10110"))
    }
    @Test
    fun `partOne using sample data is 198 `() {
        assertEquals(198,partOne(sampleData))
    }
    @Test
    fun `partOne using puzzle input is 2583164 `() {
        assertEquals(2583164,partOne(puzzleInput))
    }
    @Test
    fun `oxygen generator rating of sampleData is 23`() {
        assertEquals("10111",oxyGenRating(sampleData))
    }
    @Test
    fun `scrubber rating of sampleData is 10`() {
        assertEquals("01010",scrubberRating(sampleData))
    }
    @Test
    fun `part two using sample data is 230`() {
        assertEquals(230, partTwo(sampleData))
    }
    @Test
    fun `part two using puzzle input is 2784375`() {
        assertEquals(2784375, partTwo(puzzleInput))
    }
}