import kotlin.math.abs

data class Position(val x:Int, val y:Int)

val energy = mapOf('A' to 1, 'B' to 10, 'C' to 100, 'D' to 1000)

fun List<String>.toAmphipods():List<Amphipod> {
    val destinationColumns = mapOf('A' to 3, 'B' to 5, 'C' to 7, 'D' to 9)
    val amphipods = mutableListOf<Amphipod>()
    forEachIndexed{y, row ->
        row.forEachIndexed{x, char ->
            if (char in destinationColumns.keys) {
                val destinationColumn = destinationColumns.getValue(char)
                if (x != destinationColumn || y != 3){
                    val position = Position(x, y)
                    amphipods.add(position.createAmphipod(destinationColumn,energy.getValue(char)))
                }
            }
        }
    }
    return amphipods
}

fun Position.createAmphipod(destinationColumn: Int, energy:Int):Amphipod {
    val step1 =  (1..11).map { Position(it,1) }.filter{it.x != 3 && it.x !=5 && it.x != 7 && it.x != 9 }.map{Move(this,it, energy * (abs(this.x - it.x) + abs(this.y - it.y)))}
    val step2 = step1.map{Move(it.end, Position(destinationColumn,2),energy * (abs(destinationColumn - it.end.x) + abs(2 - it.end.y)) )} + step1.map{Move(it.end, Position(destinationColumn,3),energy * (abs(destinationColumn - it.end.x) + abs(3 - it.end.y)) )}
    return Amphipod(step1 + step2, destinationColumn)
}

data class Move(val start:Position, val end:Position, val cost:Int)

data class Amphipod( val steps:List<Move>, val destinationColum:Int, val stepsTaken:List<Int> = listOf() ) {
    fun stepsAllowed(otherAmphipods:List<Amphipod>):List<Move> {
        val othersInHallway = otherAmphipods.filter{it != this && it.isInHallway}
        return steps
                //Is not at the back of the correct room then no steps
            .filter{ !isAtBackOfCorrectRoom}
                //Amphipods can only move twice
            .filter{ stepsTaken.size < 2}
                //Is not at the back of a room or is at the back and space above is clear
            .filter{notAtBackOfRoomOrSpaceAboveIsClear(otherAmphipods)}
                //only include steps that start at current position
            .filter{ step -> step.start == position  }
                //include steps where end of step is an empty position
            .filter{ step -> endOfMoveIsEmpty(step,otherAmphipods)  }
                //include steps where no other amphipods in the way
            .filter{ step ->  noOthersInTheWay(step, othersInHallway)}
                //include steps if not going to the destination room or destination room doesnt contain other amphipods from a different room
            .filter{step -> step.end.x != destinationColum
                    || destinationContainsNoIntruders(otherAmphipods)}
    }

    val position = if (stepsTaken.isEmpty()) steps.first().start else steps[stepsTaken.last()].end
    val isInHallway = position.y == 1
    val isInCorrectRoom = position.x == destinationColum
    val isAtBackOfCorrectRoom = isInCorrectRoom && position.y == 3

    fun endOfMoveIsEmpty(move: Move, otherAmphipods:List<Amphipod>) = otherAmphipods.all { other -> other.position != move.end }

    fun notAtBackOfRoomOrSpaceAboveIsClear(otherAmphipods: List<Amphipod>) =
        position.y != 3 || position.y == 3 && otherAmphipods.none{other -> other.position.x == position.x}

    fun noOthersInTheWay(move: Move, otherAmphipods:List<Amphipod>) =
        (move.end.x < move.start.x && otherAmphipods.none{ other -> other.position.x < move.start.x && other.position.x >= move.end.x})
                || (move.end.x > move.start.x && otherAmphipods.none{ other -> other.position.x > move.start.x && other.position.x <= move.end.x})

    fun destinationContainsNoIntruders(otherAmphipods: List<Amphipod>) =
        otherAmphipods.filter {other -> other.destinationColum != destinationColum }.none{other -> other.position.x == destinationColum}
    
    fun moveAmphipod(move:Move):Amphipod = Amphipod(steps, destinationColum, stepsTaken + steps.indexOf(move))

    fun cost() = stepsTaken.sumOf{ steps[it].cost  }
}

fun List<Amphipod>.allHome() = all{it.isInCorrectRoom}
fun List<Amphipod>.allStuck() = all{amphipod -> amphipod.stepsAllowed( this.filter{it != amphipod} ).isEmpty()}
fun List<Amphipod>.cost() = sumOf{it.cost()}


fun calcCost(data:List<String>):Int {

    var minCost = Int.MAX_VALUE
    var combinitationsTried = mutableSetOf<List<Amphipod>>()

    fun calcMoves(amphipods:List<Amphipod>) {

        if (amphipods in combinitationsTried) {
            return
        } else {
            combinitationsTried.add(amphipods)
        }
        if (amphipods.allHome()) {
            if (amphipods.cost() < minCost) minCost = amphipods.cost()
            return
        }
        if (amphipods.allStuck())return
        if (amphipods.cost() > minCost) return

        amphipods.forEach{ amphipod ->
            val otherAmphipods = amphipods.filter{it != amphipod}
            amphipod.stepsAllowed(otherAmphipods).forEach{ step ->
                val movedAmphipod = amphipod.moveAmphipod(step)
                val updatedAmphipods = amphipods.map{if (it == amphipod) movedAmphipod else it}
                calcMoves(updatedAmphipods)
            }
        }
    }
    val amphipods = data.toAmphipods()
    calcMoves(amphipods)
    return minCost
}
