
data class Position(val x:Int, val y:Int) {
    fun nextPositionOnRow(max:MaxSize) = Position((x + 1) % (max.x + 1), y)
    fun nextPositionOnColumn(max:MaxSize) = Position(x, (y + 1) % (max.y + 1))
}
data class MaxSize(val x:Int, val y:Int)

fun List<String>.parse():Map<Position, Char> =
    flatMapIndexed{y, row ->
        row.mapIndexed{x, char-> Pair(Position(x,y),char) }
    }.toMap()

fun Map<Position, Char>.horizontalMovers(max:MaxSize) =
    filter{it.value == '>' && getValue(it.key.nextPositionOnRow(max)) == '.' }.keys

fun Map<Position, Char>.verticalMovers(max:MaxSize) =
    filter{it.value == 'v' && getValue(it.key.nextPositionOnColumn(max)) == '.' }.keys

fun MutableMap<Position, Char>.updateWithHorizontalMovers(movers:Set<Position>, max:MaxSize) =
    movers.forEach{ position -> moveCucumber(position, position.nextPositionOnRow(max),'>') }

fun MutableMap<Position, Char>.updateWithVerticalMovers(movers:Set<Position>, max:MaxSize) =
    movers.forEach{ position -> moveCucumber(position, position.nextPositionOnColumn(max) ,'v') }

fun MutableMap<Position, Char>.moveCucumber(oldPosition:Position, newPosition: Position, char:Char) {
    this[oldPosition] = '.'
    this[newPosition] = char
}

fun partOne(data:List<String>):Int {
    val map = data.parse().toMutableMap()
    val maxSize = map.maxSize
    var count = 0
    while (true) {
        count++
        val horizontalMovers = map.horizontalMovers(maxSize)
        map.updateWithHorizontalMovers(horizontalMovers, maxSize)
        val verticalMovers = map.verticalMovers(maxSize)
        map.updateWithVerticalMovers(verticalMovers, maxSize)
        if (horizontalMovers.isEmpty() && verticalMovers.isEmpty()) return count
    }
}

val Map<Position, Char>.maxSize get() = MaxSize(keys.maxOf { it.x }, keys.maxOf { it.y })
val Map<Position, Char>.maxX get() = keys.maxOf { it.x }
val Map<Position, Char>.maxY get() = keys.maxOf { it.y }