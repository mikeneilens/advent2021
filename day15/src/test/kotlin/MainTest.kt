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
        assertEquals(1, cavern[Position(0,0,9,9)]?.num)
        assertEquals(6, cavern[Position(1,3,9,9)]?.num)
        assertEquals(7, cavern[Position(9,5,9,9)]?.num)
        assertEquals(1, cavern[Position(9,9,9,9)]?.num)
    }
    @Test
    fun `find path using sample data`() {
        val cavern = sampleData.parse()
        cavern[Position(0,0,9,9)]?.cheapestCostToGetHere = 0
        val results = findPath(cavern,Position(0,0,9,9), Position(9,9,9,9))
        assertEquals(40, cavern[Position(9,9,9,9)]?.cheapestCostToGetHere)
    }

    @Test
    fun `find path2 using sample data`() {
        assertEquals(40, sampleData.partOne())
    }

    @Test
    fun `find path2 using puzzleInput`() {
        assertEquals(685, puzzleInput.partOne())
    }
}