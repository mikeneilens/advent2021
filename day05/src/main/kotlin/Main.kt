import kotlin.math.abs

data class Point(val x:Int, val y:Int)

data class Line(val start:Point, val end:Point)

fun String.toLine():Line {
    val (start, end) = split(" -> ").toPair()
    return Line(start.toPoint(),end.toPoint())
}

fun String.toPoint() = split(",")
    .map(String::toInt)
    .toPair()
    .let{Point(it.first, it.second)}

fun List<String>.updateMap(ventsMap: MutableMap<Point, Int>, includeDiagonals: Boolean = false) =
    map(String::toLine).forEach { it.updateMap(ventsMap, includeDiagonals) }

fun Line.updateMap(ventsMap: MutableMap<Point, Int>, includeDiagonals: Boolean = false) {
    val xIncrement = increment(start.x, end.x)
    val yIncrement = increment(start.y, end.y)
    multipliers(start, end, includeDiagonals)?.forEach {
        val point = Point(start.x + xIncrement * it, start.y + yIncrement * it)
        ventsMap[point] = (ventsMap[point] ?: 0) + 1
    }
}

fun MutableMap<Point, Int>.pointsToAvoid() = filter { it.value >= 2 }.keys

fun increment(start:Int, end:Int) = if ((end - start) > 0) 1 else if ((end - start) < 0) -1 else 0

fun multipliers(start:Point, end:Point, includeDiagonals: Boolean) =
    if (!includeDiagonals && start.x != end.x && start.y != end.y) null
    else if (start.x != end.x) 0..abs(start.x - end.x )
    else 0..abs(start.y - end.y )

fun partOne(data:List<String>,ventsMap: MutableMap<Point, Int> = mutableMapOf()): Int {
    data.updateMap(ventsMap)
    return ventsMap.pointsToAvoid().size
}

fun partTwo(data:List<String>,ventsMap: MutableMap<Point, Int> = mutableMapOf()): Int {
    data.updateMap(ventsMap, true)
    return ventsMap.pointsToAvoid().size
}

fun <T>List<T>.toPair() =Pair(get(0),get(1))

fun Map<Point,Int>.print() {
    if (keys.isEmpty()) return
    val maxX = keys.maxOf { it.x }
    val maxy = keys.maxOf { it.y }
    (0..maxy).forEach { y->
        (0..maxX).forEach { x ->
            print(getOrDefault(Point(x,y),'.'))
        }
        println()
    }
}