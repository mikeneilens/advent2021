import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {
    @Test
    fun `parse sampleData1`() {
        val map = sampleData1.parse()
        assertEquals(listOf("A","b"), map["start"] )
        assertEquals(listOf("start","c","b","end"), map["A"] )
        assertEquals(listOf("start","A","d","end"), map["b"] )
        assertEquals(listOf("A"), map["c"] )
        assertEquals(listOf("b"), map["d"] )
    }
    @Test
    fun `finding routes using sampleData1`() {
        assertEquals(10, partOne(sampleData1).size)
    }
    @Test
    fun `finding routes using sampleData2`() {
        assertEquals(19, partOne(sampleData2).size)
    }
    @Test
    fun `finding routes using sampleData3`() {
        assertEquals(226, partOne(sampleData3).size)
    }
    @Test
    fun `finding routes using Puzzle Input`() {
        assertEquals(3410, partOne(puzzleInput).size)
    }

    @Test
    fun `finding routes for part two using sampleData1`() {
        assertEquals(36, partTwo(sampleData1).size)
    }
    @Test
    fun `finding routes for part two using sampleData2`() {
        assertEquals(103, partTwo(sampleData2).size)
    }
    @Test
    fun `finding routes for part two using sampleData3`() {
        assertEquals(3509, partTwo(sampleData3).size)
    }
    @Test
    fun `finding routes for part two using puzzle input`() {
        assertEquals(98796, partTwo(puzzleInput).size)
    }
}

