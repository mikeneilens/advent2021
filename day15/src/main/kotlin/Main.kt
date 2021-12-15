import java.util.PriorityQueue

data class Position(val x:Int, val y:Int, private val maxX:Int, private val maxY:Int  ) {
    fun surroundingPositions():List<Position> =
        listOf(Position(x - 1, y, maxX, maxY),Position(x , y - 1, maxX, maxY),Position(x + 1, y, maxX, maxY),Position(x , y + 1, maxX, maxY))
            .filter{ it.x in 0..maxX && it.y in 0..maxY}

    override fun toString(): String = "Position($x, $y)"
}

data class RiskLevel(val num:Int, var cheapestCostToGetHere:Int = Int.MAX_VALUE)

typealias Cavern = Map<Position, RiskLevel>

fun List<String>.parse():Cavern {
    val cavern = mutableMapOf<Position, RiskLevel>()
    val maxX = size - 1
    forEachIndexed {y, row->
        row.forEachIndexed { x, char ->
            cavern[Position(x,y, maxX, maxX)] = RiskLevel(char.toString().toInt())
        }
    }
    return cavern
}

fun findPath(cavern:Cavern, startPosition:Position, target:Position){
    if (startPosition != target ) startPosition.surroundingPositions().filter { nextPosition -> cavern.isValidMove(startPosition, nextPosition)}.forEach { nextPosition ->
        cavern.getValue(nextPosition).cheapestCostToGetHere = cavern.getValue(startPosition).cheapestCostToGetHere + cavern.getValue(nextPosition).num
        findPath(cavern,nextPosition, target)
    }
}

data class QueueItem(val position:Position, val cost:Int)

fun findPath2(cavern:Cavern, start:Position, target:Position):Int {

    val stack = PriorityQueue<QueueItem> { a, b -> a.cost - b.cost }
    stack.offer(QueueItem(start,0))

    while (stack.isNotEmpty()) {
        val (position, _) = stack.poll()
        if (position != target) {
            position.surroundingPositions().filter { nextPosition -> cavern.isValidMove(position, nextPosition)  }.forEach {nextPosition ->
                cavern.getValue(nextPosition).cheapestCostToGetHere = cavern.getValue(position).cheapestCostToGetHere + cavern.getValue(nextPosition).num
                stack.offer(QueueItem(nextPosition, cavern.getValue(nextPosition).cheapestCostToGetHere))
            }
        } else {
            println("reached target ${cavern[position]}")
        }
    }
    return cavern[target]?.cheapestCostToGetHere ?: 0
}

fun Cavern.isValidMove(currentPosition: Position, nextPosition: Position) =
    getValue(currentPosition).cheapestCostToGetHere + getValue(nextPosition).num < getValue(nextPosition).cheapestCostToGetHere

fun List<String>.partOne():Int {
    val cavern = parse()
    val maxX = size - 1
    cavern[Position(0,0,maxX,maxX)]?.cheapestCostToGetHere = 0
    return findPath2(cavern, Position(0,0,maxX,maxX), Position(maxX,maxX,maxX,maxX))
}
