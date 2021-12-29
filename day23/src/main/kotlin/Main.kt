import kotlin.math.abs

data class Position(val x:Int, val y:Int)

val energy = mapOf('A' to 1, 'B' to 10, 'C' to 100, 'D' to 1000)
val destinationColumns = mapOf('A' to 3, 'B' to 5, 'C' to 7, 'D' to 9)

fun List<String>.toAmphipods(depth: Int):List<Amphipod> =
     flatMapIndexed{y, row ->
        row.mapIndexed{x, char ->
            if (char in destinationColumns.keys) {
                if (x != destinationColumns[char] || y != depth){
                    val position = Position(x, y)
                    position.createAmphipod(destinationColumns.getValue(char), depth, energy.getValue(char))
                } else //an amphipod already in the right place is given a dummy first move.
                    Amphipod(listOf(Move(Position(x,y),Position(x,y),0)),destinationColumns.getValue(char))
            } else null
        }.filterNotNull()
    }

fun Position.createAmphipod(destinationColumn: Int, depth: Int, energy:Int):Amphipod {
    val firstSteps =  (1..11).mapNotNull { if (it !in destinationColumns.values)  Position(it,1) else null }
        .map{Move(this,it, energy * (abs(this.x - it.x) + abs(this.y - it.y)))}
    val secondSteps = (2..depth).flatMap{row -> firstSteps.map{firstStep ->  secondMove(firstStep.end, destinationColumn, row, energy) } }
    return Amphipod(firstSteps + secondSteps, destinationColumn)
}

private fun secondMove(firstStepEnd:Position, destinationColumn:Int, row:Int, energy:Int) =
    Move(firstStepEnd, Position(destinationColumn, row), energy * (abs(destinationColumn - firstStepEnd.x) + abs(row - firstStepEnd.y)))

data class Move(val start:Position, val end:Position, val cost:Int)

data class Amphipod(val possibleSteps:List<Move>, val destinationColumn:Int, val stepsTaken:List<Move> = listOf(), val depth:Int = 3 ) {
    fun stepsAllowed(otherAmphipods:List<Amphipod>):List<Move> {
        val othersInHallway = otherAmphipods.filter{it != this && it.isInHallway}
        val allStepsAllowed = possibleSteps
            .filter{ step -> step.start == position               //only include steps that start at current position
                     && endOfMoveIsUnoccupied(step,otherAmphipods)    //include steps where end of step is an empty position
                     && noOthersInTheWayInHallway(step, othersInHallway)   //include steps where no other amphipods in the way
                     && noOthersInTheWayInRoom(step, otherAmphipods)   //include steps where no other amphipods in the way
                     && (step.end.x != destinationColumn         //include steps if not going to the destination room
                    || destinationContainsNoIntruders(otherAmphipods))} // or destination room does not contain other amphipods from a different room

        //This final filter ensures that when going into a room the amphipod moves to the lowest level in the room
        return allStepsAllowed.filter{s1 ->  s1.end.x != destinationColumn || s1.end.x == destinationColumn && s1.end.y == allStepsAllowed.filter{s2 -> s2.end.x == destinationColumn}.maxOf { it.end.y }}
    }

    val position = if (stepsTaken.isEmpty()) possibleSteps.first().start else stepsTaken.last().end
    private val isInHallway = position.y == 1
    val isInCorrectRoom = position.x == destinationColumn
    val isAtBackOfCorrectRoom = isInCorrectRoom && position.y == depth

    private fun endOfMoveIsUnoccupied(move: Move, otherAmphipods:List<Amphipod>) = otherAmphipods.all { other -> other.position != move.end }

    fun atBackOfRoom(otherAmphipods: List<Amphipod>):Boolean {
        val amphipodsInRoom = otherAmphipods.filter{it.position.x == this.position.x && it.position.y > this.position.y}
        val backOfRoom = depth - amphipodsInRoom.size
        return position.y == backOfRoom && amphipodsInRoom.all{it.destinationColumn == this.destinationColumn}
    }

    private fun noOthersInTheWayInHallway(move: Move, otherAmphipods:List<Amphipod>) =
        (move.end.x < move.start.x && otherAmphipods.none{ other -> other.position.x < move.start.x && other.position.x >= move.end.x})
                || (move.end.x > move.start.x && otherAmphipods.none{ other -> other.position.x > move.start.x && other.position.x <= move.end.x})

    fun noOthersInTheWayInRoom(move: Move, otherAmphipods:List<Amphipod>)=
        (move.end.y == 1 && otherAmphipods.none{it.position.x == move.start.x && it.position.y <= move.start.y  } )
                || (move.start.y == 1 && otherAmphipods.none{it.position.x == move.end.x && it.position.y <= move.end.y  } )


    private fun destinationContainsNoIntruders(otherAmphipods: List<Amphipod>) =
        otherAmphipods.filter {other -> other.destinationColumn != destinationColumn }.none{ other -> other.position.x == destinationColumn}
    
    fun moveAmphipod(move:Move):Amphipod = Amphipod(possibleSteps.filter{it.start == move.end}, destinationColumn, stepsTaken + move)
}

fun List<Amphipod>.allHome() = all{it.isInCorrectRoom}
fun List<Amphipod>.allStuck() = all{amphipod -> amphipod.stepsAllowed( this.filter{it != amphipod} ).isEmpty()}

fun calcCost(data:List<String>, depth:Int = 3):Int {

    var minCost = Int.MAX_VALUE
    val combinationsTried = mutableSetOf<List<Amphipod>>()

    fun calcMoves(amphipods:List<Amphipod>, cost:Int = 0) {
        if (amphipods in combinationsTried) {
            return
        } else {
            combinationsTried.add(amphipods)
        }
        if (amphipods.allHome()) {
            if (cost < minCost) minCost = cost
            return
        }
        if (amphipods.allStuck())return

        amphipods
            .filter{it.stepsTaken.size < 2 &&  !it.isAtBackOfCorrectRoom }
            .forEach{ amphipod ->
                val otherAmphipods = amphipods.filter{it != amphipod}
                amphipod.stepsAllowed(otherAmphipods)
                    .filter{it.cost + cost < minCost }
                    .forEach{ step ->
                        val movedAmphipod = amphipod.moveAmphipod(step)
                        val updatedAmphipods = amphipods.map{if (it == amphipod) movedAmphipod else it}
                        calcMoves(updatedAmphipods, cost + step.cost )
                    }
            }
    }
    val amphipods = data.toAmphipods(depth)
    calcMoves(amphipods)
    return minCost
}
