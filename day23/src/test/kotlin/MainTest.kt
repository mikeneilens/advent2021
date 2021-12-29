import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

val sampleData = """
#############
#...........#
###B#C#B#D###
  #A#D#C#A#  
  #########  
""".trimIndent().split("\n")
val sampleDataP2 = """
#############
#...........#
###B#C#B#D###
  #D#C#B#A#
  #D#B#A#C#
  #A#D#C#A#  
  #########  
""".trimIndent().split("\n")

class MainTest {
    @Test
    fun `parsing data`() {
        val amphipods = sampleData.toAmphipods(3)
        assertEquals(6, amphipods.size)
        assertEquals(Position(3,2), amphipods[0].position)
        assertFalse(amphipods[0].isInCorrectRoom) //B
        assertEquals(5, amphipods[0].destinationColumn)
        assertFalse(amphipods[1].isInCorrectRoom) //C
        assertEquals(7, amphipods[1].destinationColumn)
        assertFalse(amphipods[2].isInCorrectRoom) //B
        assertEquals(5, amphipods[2].destinationColumn)
        assertTrue(amphipods[3].isInCorrectRoom)  //D
        assertEquals(9, amphipods[3].destinationColumn)
        assertFalse(amphipods[4].isInCorrectRoom) //D
        assertEquals(9, amphipods[4].destinationColumn)
        assertFalse(amphipods[5].isInCorrectRoom) //A
        assertEquals(3, amphipods[5].destinationColumn)
    }
    @Test
    fun `first steps allowed for first B when no other amphipods have moved`() {
        val amphipods = sampleData.toAmphipods(3)
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
    fun `is at back of room`() {
        val amphipodAt1 = Amphipod(listOf(Move(Position(3,1), Position(1,1),0)), 3, listOf(), 3 )
        val amphipodAt2 = Amphipod(listOf(Move(Position(3,2), Position(1,1),0)), 3, listOf(), 3 )
        val amphipodAt3 = Amphipod(listOf(Move(Position(3,3), Position(1,1),0)), 3, listOf(), 3 )
        val amphipodAt3WrongDestination = Amphipod(listOf(Move(Position(3,3), Position(1,1),0)), 5, listOf(), 3 )
        assertTrue(amphipodAt2.atBackOfRoom( listOf(amphipodAt1,amphipodAt3)))
        assertTrue(amphipodAt3.atBackOfRoom( listOf(amphipodAt1, amphipodAt2)))
        assertFalse(amphipodAt2.atBackOfRoom( listOf()))
        assertFalse(amphipodAt2.atBackOfRoom( listOf(amphipodAt3WrongDestination)))
    }
    @Test
    fun `cannot move into a room if other amphipods are in the way`() {
        val amphipodAtRow1 = Amphipod(listOf(Move(Position(1,1), Position(3,3),0)), 3, listOf(), 3 )
        val amphipodAtRow2 = Amphipod(listOf(Move(Position(3,2), Position(1,1),0)), 3, listOf(), 3 )
        val amphipodAtRow3 = Amphipod(listOf(Move(Position(3,3), Position(1,1),0)), 3, listOf(), 3 )
        val amphipodAtRow4 = Amphipod(listOf(Move(Position(3,4), Position(1,1),0)), 3, listOf(), 3 )
        assertTrue(amphipodAtRow1.noOthersInTheWayInRoom(amphipodAtRow1.possibleSteps[0],listOf()))
        assertFalse(amphipodAtRow1.noOthersInTheWayInRoom(amphipodAtRow1.possibleSteps[0],listOf(amphipodAtRow2)))
        assertFalse(amphipodAtRow1.noOthersInTheWayInRoom(amphipodAtRow1.possibleSteps[0],listOf(amphipodAtRow3)))
        assertTrue(amphipodAtRow1.noOthersInTheWayInRoom(amphipodAtRow1.possibleSteps[0],listOf(amphipodAtRow4)))
    }
    @Test
    fun `cannot move out of a room if other amphipods are in the way`() {
        val amphipodAtRow2 = Amphipod(listOf(Move(Position(3,2), Position(1,1),0)), 3, listOf(), 3 )
        val amphipodAtRow3 = Amphipod(listOf(Move(Position(3,3), Position(1,1),0)), 3, listOf(), 3 )
        val amphipodAtRow4 = Amphipod(listOf(Move(Position(3,4), Position(1,1),0)), 3, listOf(), 3 )
        assertTrue(amphipodAtRow3.noOthersInTheWayInRoom(amphipodAtRow3.possibleSteps[0],listOf()))
        assertTrue(amphipodAtRow2.noOthersInTheWayInRoom(amphipodAtRow2.possibleSteps[0],listOf()))
        assertTrue(amphipodAtRow2.noOthersInTheWayInRoom(amphipodAtRow2.possibleSteps[0],listOf(amphipodAtRow3)))
        assertFalse(amphipodAtRow3.noOthersInTheWayInRoom(amphipodAtRow3.possibleSteps[0],listOf(amphipodAtRow2)))
        assertTrue(amphipodAtRow3.noOthersInTheWayInRoom(amphipodAtRow3.possibleSteps[0],listOf(amphipodAtRow4)))
    }

    @Test
    fun `moving amphipod`() {
        val amphipods = sampleData.toAmphipods(3)
        val amphipodB = amphipods.first()
        val movedAmphipod = amphipodB.moveAmphipod(amphipodB.possibleSteps[2])
        assertEquals(listOf(2), movedAmphipod.stepsTaken)
        assertEquals(amphipodB.possibleSteps, movedAmphipod.possibleSteps)
    }
    @Test
    fun `no second steps allowed if amphipod has moved out of a room but no others have`() {
        val amphipods = sampleData.toAmphipods(3)
        val amphipodB = amphipods.first()
        val otherAmphipods = amphipods.filter{it != amphipodB}
        val movedAmphipod = amphipodB.moveAmphipod(amphipodB.possibleSteps[2]) //moves it to the left
        assertEquals(0, movedAmphipod.stepsAllowed(otherAmphipods).size)
    }
    @Test
    fun `second steps is allowed if amphipod has moved out of a room and other amphipods have moved out of its room`() {
        val amphipods = sampleData.toAmphipods(3)
        val amphipodB = amphipods.first()
        val amphipodC = amphipods[1]
        val amphipodD = amphipods[4]
        val movedAmphipodC = amphipodC.moveAmphipod(amphipodC.possibleSteps[5]) //moves it somewhere to the right
        val movedAmphipodD = amphipodC.moveAmphipod(amphipodC.possibleSteps[5]) //moves it somewhere to the right
        val otherAmphipods = amphipods.filter{it != amphipodB}
            .map{if(it.position == amphipodC.position) movedAmphipodC else it  }
            .map{if(it.position == amphipodD.position) movedAmphipodD else it  }
        val movedAmphipod = amphipodB.moveAmphipod(amphipodB.possibleSteps[2]) //moves it somewhere to the left

        val expectedResult = listOf(
            Move(Position(x=4, y=1),Position(x=5, y=2),20),
            Move(Position(x=4, y=1),Position(x=5, y=3),30)
        )
        assertEquals(expectedResult, movedAmphipod.stepsAllowed(otherAmphipods))
    }
    @Test
    fun `second steps is not allowed if amphipod has moved left out of a room and other amphipods have moved out of its room but one is in the way`() {
        val amphipods = sampleData.toAmphipods(3)
        val amphipodB = amphipods.first()
        val amphipodC = amphipods[1]
        val amphipodD = amphipods[4]
        val movedAmphipodC = amphipodC.moveAmphipod(amphipodC.possibleSteps[1]) //moves it somewhere to the left
        println(movedAmphipodC.position)
        val movedAmphipodD = amphipodC.moveAmphipod(amphipodC.possibleSteps[5]) //moves it somewhere to the right
        val otherAmphipods = amphipods.filter{it != amphipodB}
            .map{if(it.position == amphipodC.position) movedAmphipodC else it  }
            .map{if(it.position == amphipodD.position) movedAmphipodD else it  }
        val movedAmphipod = amphipodB.moveAmphipod(amphipodB.possibleSteps[0]) //moves it somewhere to the left

        assertEquals(0, movedAmphipod.stepsAllowed(otherAmphipods).size)
    }
    @Test
    fun `second steps is not allowed if amphipod has moved right out of a room and other amphipods have moved out of its room but one is in the way`() {
        val amphipods = sampleData.toAmphipods(3)
        val amphipodB = amphipods.first()
        val amphipodC = amphipods[1]
        val amphipodD = amphipods[4]
        val movedAmphipodC = amphipodC.moveAmphipod(amphipodC.possibleSteps[1]) //moves it somewhere to the left
        val movedAmphipodD = amphipodC.moveAmphipod(amphipodC.possibleSteps[5]) //moves it somewhere to the right
        val otherAmphipods = amphipods.filter{it != amphipodB}
            .map{if(it.position == amphipodC.position) movedAmphipodC else it  }
            .map{if(it.position == amphipodD.position) movedAmphipodD else it  }
        val movedAmphipod = amphipodB.moveAmphipod(amphipodB.possibleSteps[6]) //moves it somewhere to the far right
        assertEquals(0, movedAmphipod.stepsAllowed(otherAmphipods).size)
    }
    @Test
    fun `first steps are limited if another amphipod has moved already`() {
        val amphipods = sampleData.toAmphipods(3)
        val amphipodB = amphipods.first()
        val amphipodC = amphipods[1]
        val amphipodD = amphipods[4]
        val movedAmphipodC = amphipodC.moveAmphipod(amphipodC.possibleSteps[0]) //moves it somewhere to the left of B
        val movedAmphipodD = amphipodC.moveAmphipod(amphipodC.possibleSteps[4]) //moves it somewhere to the right of B
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
        val amphipods = sampleData.toAmphipods(3)
        val amphipodAtBack = amphipods.first{it.position.y == 3}
        val otherAmphipods = amphipods.filter{it != amphipodAtBack}
        assertEquals(0, amphipodAtBack.stepsAllowed(otherAmphipods).size)
    }

    @Test
    fun `calculating moves using sample data`() {
        assertEquals(12521, calcCost(sampleData))
    }
    @Test
    fun `calculating moves with puzzle input`() {
        assertEquals(14348, calcCost(puzzleInput))
    }
    @Test
    fun `calculating moves part 2`() {
        assertEquals(40954, calcCost(puzzleInputP2, 5))
    }
}
