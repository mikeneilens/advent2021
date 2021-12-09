import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MainTest {
    val sampleData = """
        2199943210
        3987894921
        9856789892
        8767896789
        9899965678
    """.trimIndent().split("\n")
    @Test
    fun `parse sample data`() {
        val heatMap = parse(sampleData)
        assertEquals(2, heatMap.map[Position(0,0)])
        assertEquals(8, heatMap.map[Position(9,4)])
    }
    @Test
    fun `surrounding positions`() {
        assertEquals( listOf(Position(x=1, y=2), Position(x=2, y=1), Position(x=3, y=2), Position(x=2, y=3)), Position(2,2).surroundingPositions(9,4) )
        assertEquals( listOf(Position(x=0, y=1), Position(x=1, y=2), Position(x=0, y=3)),Position(0,2).surroundingPositions(9,4) )
        assertEquals( listOf(Position(x=8, y=2), Position(x=9, y=1), Position(x=9, y=3)),Position(9,2).surroundingPositions(9,4) )
        assertEquals( listOf(Position(x=1, y=0), Position(x=3, y=0), Position(x=2, y=1)),Position(2,0).surroundingPositions(9,4) )
        assertEquals( listOf(Position(x=1, y=4), Position(x=2, y=3), Position(x=3, y=4)),Position(2,4).surroundingPositions(9,4) )
    }
    @Test
    fun `position is lower than surroundings`() {
        val heatMap = parse(sampleData)
        assertFalse(heatMap.isLowerThanSurroundings(Position(0,0)))
        assertTrue(heatMap.isLowerThanSurroundings(Position(1,0)))
        assertTrue(heatMap.isLowerThanSurroundings(Position(2,2)))
        assertFalse(heatMap.isLowerThanSurroundings(Position(2,3)))
        assertFalse(heatMap.isLowerThanSurroundings(Position(5,0)))
    }
    @Test
    fun `part one with sample data`() {
        assertEquals(15, partOne(sampleData))
    }
    @Test
    fun `part one with puzzle input`() {
        assertEquals(508, partOne(puzzleInput))
    }
    @Test
    fun `find positions in a basin`() {
        val heatMap = parse(sampleData)

        val basin10 = heatMap.higherSurroundingPoints(Position(1,0))
        assertEquals(listOf(Position(x=1, y=0),Position(x=0, y=0), Position(x=0, y=1)), basin10.distinct())

        val basin90 = heatMap.higherSurroundingPoints(Position(9,0))
        assertEquals(listOf(Position(x=9, y=0), Position(x=8, y=0), Position(x=7, y=0), Position(x=6, y=0), Position(x=5, y=0), Position(x=6, y=1), Position(x=8, y=1), Position(x=9, y=1), Position(x=9, y=2)), basin90.distinct())

        val basin22 = heatMap.higherSurroundingPoints(Position(2,2))
        assertEquals(listOf(Position(x=2, y=2), Position(x=1, y=2), Position(x=2, y=1), Position(x=3, y=2), Position(x=3, y=1), Position(x=4, y=1), Position(x=4, y=2), Position(x=5, y=2), Position(x=4, y=3), Position(x=3, y=3), Position(x=2, y=3), Position(x=1, y=3), Position(x=0, y=3), Position(x=1, y=4)), basin22.distinct())

    }
    @Test
    fun `find heights in a basin`() {
        val heatMap = parse(sampleData)
        assertEquals(3, heatMap.basin(Position(1,0)).size)
        assertEquals(9, heatMap.basin(Position(9,0)).size)
        assertEquals(14, heatMap.basin(Position(2,2)).size)
        assertEquals(9, heatMap.basin(Position(6,4)).size)
    }
    @Test
    fun `part two with sample data`() {
        assertEquals(1134, partTwo(sampleData))
    }
    @Test
    fun `part two with puzzle input`() {
        assertEquals(1564640, partTwo(puzzleInput))
    }

}