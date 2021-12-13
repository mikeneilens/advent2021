import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

val sampleData = """
    6,10
    0,14
    9,10
    0,3
    10,4
    4,11
    6,0
    6,12
    4,1
    0,13
    10,12
    3,4
    3,0
    8,4
    1,10
    2,14
    8,10
    9,0
    fold along y=7
    fold along x=5
""".trimIndent().split("\n")

class MainTest {
    @Test
    fun `parse sample data`() {
        val (map, folds) = sampleData.parse()
        assertEquals(2, folds.size)
        assertEquals(7, folds[0].second)
        assertEquals(5, folds[1].second)
        assertTrue(map[Position(6,10)] ?: false)
        assertTrue(map[Position(10,12)] ?: false)
    }
    @Test
    fun `folding sample data along y=7`() {
        val (map, folds) = sampleData.parse()
        val foldedMap = foldY(map,folds[0].second)
        assertTrue(foldedMap[Position(0,0)] ?: false)
        assertFalse(foldedMap[Position(1,0)] ?: false)
        assertTrue(foldedMap[Position(2,0)] ?: false)
        assertTrue(foldedMap[Position(3,0)] ?: false)
        assertFalse(foldedMap[Position(4,0)] ?: false)
        assertFalse(foldedMap[Position(5,0)] ?: false)
        assertTrue(foldedMap[Position(6,0)] ?: false)
        assertFalse(foldedMap[Position(7,0)] ?: false)
        assertFalse(foldedMap[Position(8,0)] ?: false)
        assertTrue(foldedMap[Position(9,0)] ?: false)

        assertEquals(17, foldedMap.keys.size)
    }
    @Test
    fun `folding sample data along y=7 and then x = 5`() {
        val foldedMap = partOne(sampleData,2)

        assertTrue(foldedMap[Position(0,0)] ?: false)
        assertTrue(foldedMap[Position(1,0)] ?: false)
        assertTrue(foldedMap[Position(2,0)] ?: false)
        assertTrue(foldedMap[Position(3,0)] ?: false)
        assertTrue(foldedMap[Position(4,0)] ?: false)

        assertTrue(foldedMap[Position(0,1)] ?: false)
        assertFalse(foldedMap[Position(1,1)] ?: false)
        assertFalse(foldedMap[Position(2,1)] ?: false)
        assertFalse(foldedMap[Position(3,1)] ?: false)
        assertTrue(foldedMap[Position(4,1)] ?: false)

        assertTrue(foldedMap[Position(0,2)] ?: false)
        assertFalse(foldedMap[Position(1,2)] ?: false)
        assertFalse(foldedMap[Position(2,2)] ?: false)
        assertFalse(foldedMap[Position(3,2)] ?: false)
        assertTrue(foldedMap[Position(4,2)] ?: false)

        assertTrue(foldedMap[Position(0,4)] ?: false)
        assertTrue(foldedMap[Position(1,4)] ?: false)
        assertTrue(foldedMap[Position(2,4)] ?: false)
        assertTrue(foldedMap[Position(3,4)] ?: false)
        assertTrue(foldedMap[Position(4,4)] ?: false)

        assertEquals(16, foldedMap.keys.size)
    }
    @Test
    fun `part one using puzzle input`() {
        val newMap = partOne(puzzleInput,1)
        assertEquals(712, newMap.keys.size)
    }
}