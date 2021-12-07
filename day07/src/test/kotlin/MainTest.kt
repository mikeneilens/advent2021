import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

val sampleData = "16,1,2,0,4,2,7,1,2,14"
class MainTest {
    @Test
    fun `part one with sample data`() {
        assertEquals(37,partOne(sampleData))
    }
    @Test
    fun `part one with puzzlie input`() {
        assertEquals(344297,partOne(puzzleInput))
    }
    @Test
    fun `part two with sample data`() {
        assertEquals(168,partTwo(sampleData))
    }
    @Test
    fun `part two with puzzle input`() {
        assertEquals(168,partTwo(puzzleInput))
    }
}