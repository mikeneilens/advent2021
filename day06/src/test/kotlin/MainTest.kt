import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {
    val sampleData = "3,4,3,1,2"
    val puzzleInput = "2,5,3,4,4,5,3,2,3,3,2,2,4,2,5,4,1,1,4,4,5,1,2,1,5,2,1,5,1,1,1,2,4,3,3,1,4,2,3,4,5,1,2,5,1,2,2,5,2,4,4,1,4,5,4,2,1,5,5,3,2,1,3,2,1,4,2,5,5,5,2,3,3,5,1,1,5,3,4,2,1,4,4,5,4,5,3,1,4,5,1,5,3,5,4,4,4,1,4,2,2,2,5,4,3,1,4,4,3,4,2,1,1,5,3,3,2,5,3,1,2,2,4,1,4,1,5,1,1,2,5,2,2,5,2,4,4,3,4,1,3,3,5,4,5,4,5,5,5,5,5,4,4,5,3,4,3,3,1,1,5,2,4,5,5,1,5,2,4,5,4,2,4,4,4,2,2,2,2,2,3,5,3,1,1,2,1,1,5,1,4,3,4,2,5,3,4,4,3,5,5,5,4,1,3,4,4,2,2,1,4,1,2,1,2,1,5,5,3,4,1,3,2,1,4,5,1,5,5,1,2,3,4,2,1,4,1,4,2,3,3,2,4,1,4,1,4,4,1,5,3,1,5,2,1,1,2,3,3,2,4,1,2,1,5,1,1,2,1,2,1,2,4,5,3,5,5,1,3,4,1,1,3,3,2,2,4,3,1,1,2,4,1,1,1,5,4,2,4,3"

    @Test
    fun `turning a list of values into a map`() {
        val result = parse(sampleData)
        assertEquals(1,result[1])
        assertEquals(1,result[2])
        assertEquals(2,result[3])
        assertEquals(1,result[4])
    }
    @Test
    fun `if number of fish age 7 is 2 and number of fish age 0 is 3 the total of fish that are now aged 6 is 5`(){
        var fishForEachAge = mapOf<Int, Long>( 7 to 2L, 0 to 3L )
        assertEquals(5L, calcFishAgedSix(fishForEachAge))
    }
    @Test
    fun `if number of fish age 7 is 2 and no other fish the total of fish that are now aged 6 is 2`(){
        var fishForEachAge = mapOf<Int, Long>( 7 to 2L )
        assertEquals(2L, calcFishAgedSix(fishForEachAge))
    }
    @Test
    fun `if number of fish age 0 is 3 and no other fish the total of fish that are now aged 6 is 2`(){
        var fishForEachAge = mapOf<Int, Long>( 0 to 3L )
        assertEquals(3L, calcFishAgedSix(fishForEachAge))
    }
    @Test
    fun `if there are no fish aged 7 or 0 the total of fish that are now aged 6 is 0`(){
        var fishForEachAge = mapOf<Int, Long>( 1 to 4L, 2 to 3L,3 to 4L,4 to 6L, 5 to 3L, 6 to 1L, 8 to 3L )
        assertEquals(0L, calcFishAgedSix(fishForEachAge))
    }
    @Test
    fun `part one using sample data for 80 days`() {
        assertEquals(5934,partOne(sampleData,80))
    }
    @Test
    fun `day 5 part one`() {
        assertEquals(345387,partOne(puzzleInput,80))
    }
    @Test
    fun `part two using sample data for 256 days`() {
        assertEquals(26984457539L,partOne(sampleData,256))
    }
    @Test
    fun `day 5 part two`() {
        assertEquals(1574445493136L,partOne(puzzleInput,256))
    }
}