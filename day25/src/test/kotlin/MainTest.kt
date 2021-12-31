import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

val sampleData = """
    v...>>.vv>
    .vv>>.vv..
    >>.>v>...v
    >>v>>.>.v.
    v>v.vv.v..
    >.>>..v...
    .vv..>.>v.
    v.v..>>v.v
    ....v..v.>
""".trimIndent().split("\n")
class MainTest {
    @Test
    fun `parse sample data`() {
        val map = sampleData.parse()
        assertEquals("v...>>.vv>", map.toText()[0])
        assertEquals(".vv>>.vv..", map.toText()[1])
        assertEquals(">>.>v>...v", map.toText()[2])
        assertEquals("....v..v.>", map.toText().last())
    }
    @Test
    fun `find horizontal movers`() {
        val map = sampleData.subList(0,2).parse()
        assertEquals(setOf(Position(5,0),Position(4,1)),  map.horizontalMovers(map.maxSize))
    }
    @Test
    fun `find vertical movers`() {
        val map = sampleData.subList(0,2).parse()
        assertEquals(setOf(Position(0,0),Position(8,0),Position(1,1),Position(2,1),Position(6,1)),  map.verticalMovers(map.maxSize))
    }
    @Test
    fun `update map with horizontal movers`() {
        val map = sampleData.subList(0,2).parse().toMutableMap()
        val movers = setOf(Position(5,0),Position(4,1))
        map.updateWithHorizontalMovers(movers, map.maxSize)
        assertEquals('.', map[Position(5,0)])
        assertEquals('>', map[Position(6,0)])
        assertEquals('.', map[Position(4,1)])
        assertEquals('>', map[Position(5,1)])
    }
    @Test
    fun `update map with vertical movers`() {
        val map = sampleData.subList(0,2).parse().toMutableMap()
        val movers = setOf(Position(0,0),Position(8,0),Position(1,1),Position(2,1),Position(6,1))
        map.updateWithVerticalMovers(movers, map.maxSize)
        assertEquals('.', map[Position(0,0)])
        assertEquals('v', map[Position(0,1)])
        assertEquals('.', map[Position(8,0)])
        assertEquals('v', map[Position(8,1)])
        assertEquals('.', map[Position(1,1)])
        assertEquals('v', map[Position(1,0)])
        assertEquals('.', map[Position(2,1)])
        assertEquals('v', map[Position(2,0)])
        assertEquals('.', map[Position(6,1)])
        assertEquals('v', map[Position(6,0)])
    }

    @Test
    fun `part one with sample data`() {
        assertEquals(58, partOne(sampleData))
    }
    @Test
    fun `part one with puzzle input`() {
        assertEquals(384, partOne(puzzleInput))
    }

    fun Map<Position, Char>.toText():List<String> {
        return (0..maxY).map{ y ->
            (0..maxX).map{ x ->
                getValue(Position(x,y))
            }.joinToString("")
        }
    }
    fun List<String>.print() {
        forEach { println(it) }
    }
}