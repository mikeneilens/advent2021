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
        val (heatMap, maxX, maxY) = parse(sampleData)
        assertEquals(4, maxY)
        assertEquals(9, maxX)
        assertEquals(2, heatMap[Position(0,0)])
        assertEquals(8, heatMap[Position(9,4)])
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
        val (heatMap, maxX, maxY) = parse(sampleData)
        assertFalse(heatMap.isLowerThanSurroundings(Position(0,0),maxX, maxY))
        assertTrue(heatMap.isLowerThanSurroundings(Position(1,0),maxX, maxY))
        assertTrue(heatMap.isLowerThanSurroundings(Position(2,2),maxX, maxY))
        assertFalse(heatMap.isLowerThanSurroundings(Position(2,3),maxX, maxY))
        assertFalse(heatMap.isLowerThanSurroundings(Position(5,0),maxX, maxY))

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
        val (heatMap, maxX, maxY) = parse(sampleData)

        val basin10 = heatMap.higherSurroundingPoints(Position(1,0), maxX, maxY)
        assertEquals(listOf(Position(x=1, y=0),Position(x=0, y=0), Position(x=0, y=1)), basin10.distinct())

        val basin90 = heatMap.higherSurroundingPoints(Position(9,0), maxX, maxY)
        assertEquals(listOf(Position(x=9, y=0), Position(x=8, y=0), Position(x=7, y=0), Position(x=6, y=0), Position(x=5, y=0), Position(x=6, y=1), Position(x=8, y=1), Position(x=9, y=1), Position(x=9, y=2)), basin90.distinct())

        val basin22 = heatMap.higherSurroundingPoints(Position(2,2), maxX, maxY)
        assertEquals(listOf(Position(x=2, y=2), Position(x=1, y=2), Position(x=2, y=1), Position(x=3, y=2), Position(x=2, y=3), Position(x=3, y=1), Position(x=4, y=1), Position(x=4, y=2), Position(x=5, y=2), Position(x=4, y=3), Position(x=3, y=3), Position(x=1, y=3), Position(x=0, y=3), Position(x=1, y=4)), basin22.distinct())

    }
    @Test
    fun `find heights in a basin`() {
        val (heatMap, maxX, maxY) = parse(sampleData)
        assertEquals(3, heatMap.basin(Position(1,0), maxX, maxY).size)
        assertEquals(9, heatMap.basin(Position(9,0), maxX, maxY).size)
        assertEquals(14, heatMap.basin(Position(2,2), maxX, maxY).size)
        assertEquals(9, heatMap.basin(Position(6,4), maxX, maxY).size)
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