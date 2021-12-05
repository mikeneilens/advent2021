import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

val sampleData = """
    0,9 -> 5,9
    8,0 -> 0,8
    9,4 -> 3,4
    2,2 -> 2,1
    7,0 -> 7,4
    6,4 -> 2,0
    0,9 -> 2,9
    3,4 -> 1,4
    0,0 -> 8,8
    5,5 -> 8,2
""".trimIndent().split("\n")

class MainTest {
    @Test
    fun `string to a line`() {
        assertEquals(Line(Point(6,4),Point(2,0)),"6,4 -> 2,0".toLine())
    }
    @Test
    fun `updating ventmap with lines of every orientation,no diagonals`() {
        val data = listOf(
            Triple("0,2 -> 2,2", listOf(Point(0,2),Point(1,2),Point(2,2)),1),
            Triple("2,2 -> 0,2", listOf(Point(0,2),Point(1,2),Point(2,2)),1),
            Triple("2,0 -> 2,2", listOf(Point(2,0),Point(2,1),Point(2,2)),1),
            Triple("2,2 -> 2,0", listOf(Point(2,0),Point(2,1),Point(2,2)),1),
            Triple("0,0 -> 2,2", listOf(Point(0,0),Point(1,1),Point(2,2)),null),
            Triple("2,2 -> 0,0", listOf(Point(0,0),Point(1,1),Point(2,2)),null),
            Triple("0,2 -> 2,0", listOf(Point(0,2),Point(1,1),Point(2,0)),null),
            Triple("2,0 -> 0,2", listOf(Point(0,2),Point(1,1),Point(2,0)),null,)
        )
        for (test in data) {
            val ventsMap =  mutableMapOf<Point, Int>()
            updateMap(test.first.toLine(), ventsMap, includeDiagonals = false)
            ventsMap.print()
            for ( point in test.second) {
                assertEquals(ventsMap[point], test.third )
            }
        }
    }

    @Test
    fun `updating vent map  with sample data`() {
        val ventsMap = mutableMapOf<Point, Int>()
        updateMap(sampleData, ventsMap)
        assertEquals(1, ventsMap[Point(7,0)])
        assertEquals(2, ventsMap[Point(3,4)])
    }
    @Test
    fun `points to avoid on sample data`() {
        val ventsMap = mutableMapOf<Point, Int>()
        updateMap(sampleData, ventsMap)
        assertEquals(5, ventsMap.pointsToAvoid().size )
    }
    @Test
    fun `part one with sample data`() {
        assertEquals(5, partOne(sampleData) )
    }
    @Test
    fun `part one with puzzle inpupt`() {
        assertEquals(5608, partOne(puzzleInput) )
    }
    @Test
    fun `updating ventmap with lines of every orientation`() {
        val data = listOf(
            Triple("0,2 -> 2,2", listOf(Point(0,2),Point(1,2),Point(2,2)),1),
            Triple("2,2 -> 0,2", listOf(Point(0,2),Point(1,2),Point(2,2)),1),
            Triple("2,0 -> 2,2", listOf(Point(2,0),Point(2,1),Point(2,2)),1),
            Triple("2,2 -> 2,0", listOf(Point(2,0),Point(2,1),Point(2,2)),1),
            Triple("0,0 -> 2,2", listOf(Point(0,0),Point(1,1),Point(2,2)),1),
            Triple("2,2 -> 0,0", listOf(Point(0,0),Point(1,1),Point(2,2)),1),
            Triple("0,2 -> 2,0", listOf(Point(0,2),Point(1,1),Point(2,0)),1),
            Triple("2,0 -> 0,2", listOf(Point(0,2),Point(1,1),Point(2,0)),1,)
        )
        for (test in data) {
            val ventsMap =  mutableMapOf<Point, Int>()
            updateMap(test.first.toLine(), ventsMap, includeDiagonals = true)
            for ( point in test.second) {
                assertTrue(ventsMap[point] == test.third )
            }
        }
    }
    @Test
    fun `updating vent map with sample data including diagonals`() {
        val ventsMap = mutableMapOf<Point, Int>()
        updateMap(sampleData, ventsMap, includeDiagonals = true )
        ventsMap.print()
        assertEquals(1, ventsMap[Point(7,0)])
        assertEquals(2, ventsMap[Point(3,4)])
        assertEquals(3, ventsMap[Point(4,4)])
        assertEquals(3, ventsMap[Point(6,4)])
    }
    @Test
    fun `part two with sample data`() {
        assertEquals(12, partTwo(sampleData) )
    }
    @Test
    fun `part two with puzzle input`() {
        assertEquals(20299, partTwo(puzzleInput) )
    }

}
