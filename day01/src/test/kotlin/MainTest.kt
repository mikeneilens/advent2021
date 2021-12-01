import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

val sampleData = """199
200
208
210
200
207
240
269
260
263""".trim().split("\n").map{it.toInt()}

class MainTest {

    @Test
    fun`current value being bigger than previous increments the increases count`() {
        val current = 10
        val previous = 5
        val increases = 2
        val expectedResult = AccumulatedResult (current, increases + 1)
        assertEquals(expectedResult, compareCurrentToPrevious(AccumulatedResult(previous, increases), current))
    }
    @Test
    fun`current value not being bigger than previous doesnt change the increases count`() {
        val current = 10
        val previous = 10
        val increases = 2
        val expectedResult = AccumulatedResult (current, increases)
        assertEquals(expectedResult, compareCurrentToPrevious(AccumulatedResult(previous, increases), current))
    }
    @Test
    fun`comparing current to previous when there is no inrease`() {
        val current = 10
        val previous = 10
        val increases = 3
        val expectedResult = AccumulatedResult (current, increases)
        assertEquals(expectedResult, compareCurrentToPrevious(AccumulatedResult(previous, increases), current))
    }
    @Test
    fun `find increases wtih sample data`() {
        assertEquals(7, partOne(sampleData))
    }
    @Test
    fun `part one`() {
        assertEquals(1676, partOne(puzzleInput))
    }
}
