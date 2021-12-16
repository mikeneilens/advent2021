import java.util.PriorityQueue

data class Position(val x:Int, val y:Int, private val max:Int  ) {
    val surroundingPositions:List<Position> by lazy {
        listOf(Position(x - 1, y, max),Position(x , y - 1, max),Position(x + 1, y, max),Position(x , y + 1, max))
            .filter{ it.x in 0..max && it.y in 0..max}}

    override fun toString(): String = "Position($x, $y)"
}

data class RiskLevel(val num:Int, var cheapestCostToGetHere:Int = Int.MAX_VALUE)

typealias Cavern = Map<Position, RiskLevel>

fun List<String>.parse():Cavern {
    val cavern = mutableMapOf<Position, RiskLevel>()
    val caveSize = size - 1
    forEachIndexed {y, row->
        row.forEachIndexed { x, char ->
            cavern[Position(x,y, caveSize)] = RiskLevel(char.toString().toInt())
        }
    }
    return cavern
}

data class QueueItem(val position:Position, val cost:Int)

fun findPath2(cavern:Cavern, start:Position, target:Position):Int {

    val stack = PriorityQueue<QueueItem> { a, b -> a.cost - b.cost }
    stack.offer(QueueItem(start,0))

    while (stack.isNotEmpty()) {
        val (position, _) = stack.poll()
        if (position != target) {
            position.surroundingPositions.filter { nextPosition -> cavern.isValidMove(position, nextPosition)  }.forEach {nextPosition ->
                cavern.getValue(nextPosition).cheapestCostToGetHere = cavern.getValue(position).cheapestCostToGetHere + cavern.getValue(nextPosition).num
                stack.offer(QueueItem(nextPosition, cavern.getValue(nextPosition).cheapestCostToGetHere))
            }
        } else {
            println("reached target ${cavern[position]}")
            return cavern[target]?.cheapestCostToGetHere ?: 0
        }
    }
    return cavern[target]?.cheapestCostToGetHere ?: 0
}

fun Cavern.isValidMove(currentPosition: Position, nextPosition: Position) =
    getValue(currentPosition).cheapestCostToGetHere + getValue(nextPosition).num < getValue(nextPosition).cheapestCostToGetHere

fun List<String>.partOne():Int {
    val cavern = parse()
    val caveSize = size - 1
    cavern[Position(0,0,caveSize)]?.cheapestCostToGetHere = 0
    return findPath2(cavern, Position(0,0,caveSize), Position(caveSize,caveSize,caveSize))
}

fun Cavern.makeFiveTimesBigger():Cavern {
    val result:MutableMap<Position, RiskLevel> = mutableMapOf()
    val caveSize = keys.maxOf { it.x } + 1
    val newCaveSize = 5 * (keys.maxOf { it.x} + 1) -1
    forEach { (position, riskLevel) ->
        (0..4).forEach { row ->
            (0..4).forEach { col ->
                result[Position(position.x + caveSize * col, position.y + caveSize * row  ,newCaveSize)] = calcRiskLevel(riskLevel, col, row)
            }
        }
    }
    return result
}

fun calcRiskLevel(riskLevel:RiskLevel, col:Int, row:Int):RiskLevel {
   val newNum = (riskLevel.num + row + col ) % 9
   return if (newNum == 0) RiskLevel(9) else RiskLevel(newNum)
}

fun List<String>.partTwo():Int {
    val cavern = parse().makeFiveTimesBigger()
    val caveSize = cavern.keys.maxOf { it.x }
    cavern[Position(0,0,caveSize)]?.cheapestCostToGetHere = 0
    return findPath2(cavern, Position(0,0,caveSize), Position(caveSize,caveSize,caveSize))
}

/*This is what I started with
fun findPath(cavern:Cavern, startPosition:Position, target:Position){
    if (startPosition != target ) startPosition.surroundingPositions.filter { nextPosition -> cavern.isValidMove(startPosition, nextPosition)}.forEach { nextPosition ->
        cavern.getValue(nextPosition).cheapestCostToGetHere = cavern.getValue(startPosition).cheapestCostToGetHere + cavern.getValue(nextPosition).num
        findPath(cavern,nextPosition, target)
    }
}
*/
