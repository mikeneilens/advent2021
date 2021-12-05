import kotlin.math.abs

data class Point(val x:Int, val y:Int)
data class Line(val start:Point, val end:Point)

fun String.toLine():Line {
    val pointStrings = split(" -> ")
    val startNumbers=pointStrings[0].split(",").map(String::toInt)
    val endNumbers=pointStrings[1].split(",").map(String::toInt)
    return Line(Point(startNumbers[0],startNumbers[1]),Point(endNumbers[0],endNumbers[1]))
}

fun updateMap(listOfLineText:List<String>, ventsMap:MutableMap<Point,Int>, includeDiagonals:Boolean = false) =
    listOfLineText.map(String::toLine).forEach {updateMap(it,ventsMap,includeDiagonals)}

fun updateMap(line:Line, ventsMap:MutableMap<Point,Int>, includeDiagonals:Boolean = false) {
    val xIncrement = increment(line.start.x, line.end.x)
    val yIncrement = increment(line.start.y, line.end.y)
    multipliers(line.start, line.end, includeDiagonals)?.forEach {
        val point = Point(line.start.x + xIncrement * it,line.start.y + yIncrement * it)
        ventsMap[point] = (ventsMap[point] ?: 0) + 1
    }
}

fun MutableMap<Point, Int>.pointsToAvoid() = filter { it.value >= 2 }.keys

fun increment(start:Int, end:Int) = if ((end - start) > 0) 1 else if ((end - start) < 0) -1 else 0

fun multipliers(start:Point, end:Point, includeDiagonals: Boolean) =
    if (!includeDiagonals && start.x != end.x && start.y != end.y) null
    else if (start.x != end.x) 0..abs(start.x - end.x )
    else 0..abs(start.y - end.y )

fun partOne(data:List<String>): Int {
    val ventsMap = mutableMapOf<Point, Int>()
    updateMap(data, ventsMap)
    return ventsMap.pointsToAvoid().size
}

fun partTwo(data:List<String>): Int {
    val ventsMap = mutableMapOf<Point, Int>()
    updateMap(data, ventsMap, true)
    return ventsMap.pointsToAvoid().size
}


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