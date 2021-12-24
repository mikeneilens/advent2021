import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

val sampleData = """
#############
#...........#
###B#C#B#D###
  #A#D#C#A#  
  #########  
""".trimIndent().split("\n")

class MainTest {
    @Test
    fun `parsing data`() {
        val amphipods = sampleData.toAmphipods()
        assertEquals(6, amphipods.size)
        assertEquals(Position(3,2), amphipods[0].position)
        assertFalse(amphipods[0].isInCorrectRoom) //B
        assertEquals(5, amphipods[0].destinationColum)
        assertFalse(amphipods[1].isInCorrectRoom) //C
        assertEquals(7, amphipods[1].destinationColum)
        assertFalse(amphipods[2].isInCorrectRoom) //B
        assertEquals(5, amphipods[2].destinationColum)
        assertTrue(amphipods[3].isInCorrectRoom)  //D
        assertEquals(9, amphipods[3].destinationColum)
        assertFalse(amphipods[4].isInCorrectRoom) //D
        assertEquals(9, amphipods[4].destinationColum)
        assertFalse(amphipods[5].isInCorrectRoom) //A
        assertEquals(3, amphipods[5].destinationColum)
    }
    @Test
    fun `first steps allowed for first B when no other amphipods have moved`() {
        val amphipods = sampleData.toAmphipods()
        val amphipodB = amphipods.first()
        val otherAmphipods = amphipods.drop(1)
        val expectedResult = listOf(
            Move(Position(3, 2), Position(1, 1),30),
            Move(Position(3, 2), Position(2, 1),20),
            Move(Position(3, 2), Position(4, 1),20),
            Move(Position(3, 2), Position(6, 1),40),
            Move(Position(3, 2), Position(8, 1),60),
            Move(Position(3, 2), Position(10, 1),80),
            Move(Position(3, 2), Position(11, 1),90)
        )
        assertEquals(expectedResult,amphipodB.stepsAllowed(otherAmphipods))
    }
    @Test
    fun `moving amphipod`() {
        val amphipods = sampleData.toAmphipods()
        val amphipodB = amphipods.first()
        val movedAmphipod = amphipodB.moveAmphipod(amphipodB.steps[2])
        assertEquals(listOf(2), movedAmphipod.stepsTaken)
        assertEquals(amphipodB.steps, movedAmphipod.steps)
    }
    @Test
    fun `no second steps allowed if amphipod has moved out of a room but no others have`() {
        val amphipods = sampleData.toAmphipods()
        val amphipodB = amphipods.first()
        val otherAmphipods = amphipods.filter{it != amphipodB}
        val movedAmphipod = amphipodB.moveAmphipod(amphipodB.steps[2]) //moves it to the left
        assertEquals(0, movedAmphipod.stepsAllowed(otherAmphipods).size)
    }
    @Test
    fun `second steps is allowed if amphipod has moved out of a room and other amphipods have moved out of its room`() {
        val amphipods = sampleData.toAmphipods()
        val amphipodB = amphipods.first()
        val amphipodC = amphipods[1]
        val amphipodD = amphipods[4]
        val movedAmphipodC = amphipodC.moveAmphipod(amphipodC.steps[5]) //moves it somewhere to the right
        val movedAmphipodD = amphipodC.moveAmphipod(amphipodC.steps[5]) //moves it somewhere to the right
        val otherAmphipods = amphipods.filter{it != amphipodB}
            .map{if(it.position == amphipodC.position) movedAmphipodC else it  }
            .map{if(it.position == amphipodD.position) movedAmphipodD else it  }
        val movedAmphipod = amphipodB.moveAmphipod(amphipodB.steps[2]) //moves it somewhere to the left

        val expectedResult = listOf(
            Move(Position(x=4, y=1),Position(x=5, y=2),20),
            Move(Position(x=4, y=1),Position(x=5, y=3),30)
        )
        assertEquals(expectedResult, movedAmphipod.stepsAllowed(otherAmphipods))
    }
    @Test
    fun `second steps is not allowed if amphipod has moved left out of a room and other amphipods have moved out of its room but one is in the way`() {
        val amphipods = sampleData.toAmphipods()
        val amphipodB = amphipods.first()
        val amphipodC = amphipods[1]
        val amphipodD = amphipods[4]
        val movedAmphipodC = amphipodC.moveAmphipod(amphipodC.steps[1]) //moves it somewhere to the left
        println(movedAmphipodC.position)
        val movedAmphipodD = amphipodC.moveAmphipod(amphipodC.steps[5]) //moves it somewhere to the right
        val otherAmphipods = amphipods.filter{it != amphipodB}
            .map{if(it.position == amphipodC.position) movedAmphipodC else it  }
            .map{if(it.position == amphipodD.position) movedAmphipodD else it  }
        val movedAmphipod = amphipodB.moveAmphipod(amphipodB.steps[0]) //moves it somewhere to the left

        assertEquals(0, movedAmphipod.stepsAllowed(otherAmphipods).size)
    }
    @Test
    fun `second steps is not allowed if amphipod has moved right out of a room and other amphipods have moved out of its room but one is in the way`() {
        val amphipods = sampleData.toAmphipods()
        val amphipodB = amphipods.first()
        val amphipodC = amphipods[1]
        val amphipodD = amphipods[4]
        val movedAmphipodC = amphipodC.moveAmphipod(amphipodC.steps[1]) //moves it somewhere to the left
        val movedAmphipodD = amphipodC.moveAmphipod(amphipodC.steps[5]) //moves it somewhere to the right
        val otherAmphipods = amphipods.filter{it != amphipodB}
            .map{if(it.position == amphipodC.position) movedAmphipodC else it  }
            .map{if(it.position == amphipodD.position) movedAmphipodD else it  }
        val movedAmphipod = amphipodB.moveAmphipod(amphipodB.steps[6]) //moves it somewhere to the far right
        assertEquals(0, movedAmphipod.stepsAllowed(otherAmphipods).size)
    }
    @Test
    fun `first steps are limited if another amphipod has moved alraedy`() {
        val amphipods = sampleData.toAmphipods()
        val amphipodB = amphipods.first()
        val amphipodC = amphipods[1]
        val amphipodD = amphipods[4]
        val movedAmphipodC = amphipodC.moveAmphipod(amphipodC.steps[0]) //moves it somewhere to the left of B
        val movedAmphipodD = amphipodC.moveAmphipod(amphipodC.steps[4]) //moves it somewhere to the right of B
        val otherAmphipods = amphipods.filter{it != amphipodB}
            .map{if(it.position == amphipodC.position) movedAmphipodC else it  }
            .map{if(it.position == amphipodD.position) movedAmphipodD else it  }
        val expectedResult = listOf(
            Move(Position(x=3, y=2), Position(x=2, y=1),20),
            Move(Position(x=3, y=2), Position(x=4, y=1),20),
            Move(Position(x=3, y=2), Position(x=6, y=1),40)
        )
        assertEquals(expectedResult, amphipodB.stepsAllowed(otherAmphipods))
    }
    @Test
    fun `first steps are not allowed if at back of room and the position above is not clear`() {
        val amphipods = sampleData.toAmphipods()
        val amphipodAtBack = amphipods.first{it.position.y == 3}
        val otherAmphipods = amphipods.filter{it != amphipodAtBack}
        assertEquals(0, amphipodAtBack.stepsAllowed(otherAmphipods).size)
    }

    @Test
    fun `cacluating moves`() {
        assertEquals(12521, calcCost(sampleData))
    }
    @Test
    fun `calculating moves with puzzle input`() {
        assertEquals(14348, calcCost(puzzleInput))
    }
}
