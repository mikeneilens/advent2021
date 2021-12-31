
data class Position(val x:Int, val y:Int) {
    fun nextPositionOnRow(maxX:Int) = Position((x + 1) % (maxX + 1), y)
    fun nextPositionOnColumn(maxY:Int) = Position(x, (y + 1) % (maxY + 1))
}

fun List<String>.parse():Map<Position, Char> =
    flatMapIndexed{y, row ->
        row.mapIndexed{x, char->
            Pair(Position(x,y),char)
        }
    }.toMap()

fun Map<Position, Char>.horizontalMovers(max:Int) =
    filter{it.value == '>' && getValue(it.key.nextPositionOnRow(max)) == '.' }.keys

fun Map<Position, Char>.verticalMovers(max:Int) =
    filter{it.value == 'v' && getValue(it.key.nextPositionOnColumn(max)) == '.' }.keys

fun MutableMap<Position, Char>.updateWithHorizontalMovers(movers:Set<Position>, max:Int) =
    movers.forEach{ position -> moveCucumber(position, position.nextPositionOnRow(max),'>') }

fun MutableMap<Position, Char>.updateWithVerticalMovers(movers:Set<Position>, max:Int) =
    movers.forEach{ position -> moveCucumber(position, position.nextPositionOnColumn(max) ,'v') }

fun MutableMap<Position, Char>.moveCucumber(oldPosition:Position, newPosition: Position, char:Char) {
    this[oldPosition] = '.'
    this[newPosition] = char
}

fun partOne(data:List<String>):Int {
    val map = data.parse().toMutableMap()
    val maxX = map.maxX
    val maxY = map.maxY
    var count = 1
    var notFinished  = true
    while (notFinished) {
        val horizontalMovers = map.horizontalMovers(maxX)
        map.updateWithHorizontalMovers(horizontalMovers, maxX)
        val verticalMovers = map.verticalMovers(maxY)
        map.updateWithVerticalMovers(verticalMovers, maxY)
        if (horizontalMovers.isEmpty() && verticalMovers.isEmpty()) notFinished = false
        else {
            count++
        }
    }
    return count
}

val Map<Position, Char>.maxX get() = keys.maxOf { it.x }
val Map<Position, Char>.maxY get() = keys.maxOf { it.y }