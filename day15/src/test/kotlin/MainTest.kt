import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

val sampleData = """
    1163751742
    1381373672
    2136511328
    3694931569
    7463417111
    1319128137
    1359912421
    3125421639
    1293138521
    2311944581
""".trimIndent().trimIndent().split("\n")

class MainTest {
    @Test
    fun `parse sample data`() {
        val cavern = sampleData.parse()
        assertEquals(1, cavern[Position(0,0,9)]?.num)
        assertEquals(6, cavern[Position(1,3,9)]?.num)
        assertEquals(7, cavern[Position(9,5,9)]?.num)
        assertEquals(1, cavern[Position(9,9,9)]?.num)
    }
    @Test
    fun `find path using sample data`() {
        val cavern = sampleData.parse()
        cavern[Position(0,0,9)]?.cheapestCostToGetHere = 0
        findPath2(cavern,Position(0,0,9), Position(9,9,9))
        assertEquals(40, cavern[Position(9,9,9)]?.cheapestCostToGetHere)
    }

    @Test
    fun `find path2 using sample data`() {
        assertEquals(40, sampleData.partOne())
    }

    @Test
    fun `find path2 using puzzleInput`() {
        assertEquals(685, puzzleInput.partOne())
    }
    @Test
    fun `making a cave five times bigger using sample data`() {
        val cavern = sampleData.parse()
        val bigCavern = cavern.makeFiveTimesBigger()
        assertEquals(2500, bigCavern.size)
        assertEquals(1, bigCavern[Position(0,0,49)]?.num)

        assertEquals("11637517422274862853338597396444961841755517295286", bigCavern.toList().filter{it.first.y == 0}.sortedBy { it.first.x }.map{it.second.num}.joinToString(""))
    }
    @Test
    fun `find path2 for Part Two using sample data`() {
        assertEquals(315, sampleData.partTwo())
    }

    @Test
    fun `find path2 for Part Two using puzzle input`() {
        assertEquals(2995, puzzleInput.partTwo())
    }
}