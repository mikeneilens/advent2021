import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MainTest {
    val sampleData = "target area: x=20..30, y=-10..-5"
    @Test
    fun `parse sample data `() {
        assertEquals(TargetArea(20,30,-10,-5),sampleData.parse())
    }
    @Test
    fun `find valid X velocities for sample data`() {
        val target = sampleData.parse()
        val result = target.xValuesInRange()
        assertEquals(listOf(6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30), result)
    }
    @Test
    fun `find valid Y velocities for sample data`() {
        val target = sampleData.parse()
        val result = target.yValuesInRange()
        assertEquals(listOf(-10, -9, -8, -7, -6, -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9), result)
    }
    @Test
    fun `find which velocities hit target for puzzle input`() {
        val target = sampleData.parse()
        assertTrue(target.velocityHitsTarget(7,2).first , "7, 2 hits target")
        assertTrue(target.velocityHitsTarget(6,3).first, "6, 3 hits target")
        assertTrue(target.velocityHitsTarget(9,0).first, "9, 0 hits target")
        assertTrue(target.velocityHitsTarget(6,9).first, "6, 9 hits target")
        assertFalse(target.velocityHitsTarget(17,-4).first, "17, -4 misses target")

        assertEquals(45, target.velocityHitsTarget(6,9).second )
    }
    @Test
    fun `find valid x and y velocities for puzzle input`() {
        val target = puzzleInput.parse()
        val validX = target.xValuesInRange()
        val validY = target.yValuesInRange()
        val (hitsTarget, height) = target.velocityHitsTarget(validX.first(), validY.last())
        println("hits target: $hitsTarget, height: $height")
    }
    @Test
    fun `Part One`() {
        assertEquals(4950, partOne(puzzleInput))
    }
    @Test
    fun `Part Two using sample data`() {
       assertEquals(112,  partTwo(sampleData))
    }

    @Test
    fun `Part Two using puzzle input`() {
        assertEquals(1477,  partTwo(puzzleInput))
    }

}