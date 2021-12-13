data class Position(val x:Int, val y:Int )

typealias Paper = Map<Position, Boolean>
typealias Folder = (Paper)->Paper

fun String.toPosition() =
    Position( split(",").first().toString().toInt(),split(",").last().toString().toInt() )

fun List<String>.parse():Pair<Paper,List<Folder>> {
    val map = filter { !it.startsWith("fold") }.associate { Pair(it.toPosition(), true) }
    val folders= filter{it.startsWith("fold along ")}.map(String::getFold)
    return Pair(map, folders)
}

fun String.getFold():Folder {
    if (startsWith("fold along y="))  {
        val y = removePrefix("fold along y=").toInt()
        return {paper:Paper -> foldAtRowY(paper, y)}
    } else {
        val x = removePrefix("fold along x=").toInt()
        return {paper:Paper -> foldAtColX(paper, x)}
    }
}

fun foldAtRowY(paper:Paper, y:Int):Paper {
    val newMap = paper.toList().filter{it.first.y < y}.toMap().toMutableMap()
    val foldedPositions = paper.toList().filter{it.first.y > y}.map{Position(it.first.x, y * 2 - it.first.y)}
    foldedPositions.forEach {position -> newMap[position] = true }
    return newMap
}
fun foldAtColX(paper:Paper, x:Int):Paper {
    val newMap = paper.toList().filter{it.first.x < x}.toMap().toMutableMap()
    val foldedPositions = paper.toList().filter{it.first.x > x}.map{Position(x * 2 - it.first.x,it.first.y)}
    foldedPositions.forEach {position -> newMap[position] = true }
    return newMap
}

fun partOne(data:List<String>):Paper {
    val (map, folders) = data.parse()
    return folders.first()(map)
}

fun partTwo(data:List<String>):Paper {
    val (map, folders) = data.parse()
    var newMap = folders.first()(map)
    folders.forEach { folder ->
        newMap = folder(newMap).toMutableMap()
    }
    return newMap
}

fun Paper.print() {
    val maxX = keys.maxOf { it.x }
    val maxY = keys.maxOf { it.y }
    (0..maxY).forEach{ y ->
        (0..maxX).forEach{ x ->
            if ( get(Position(x,y)) ?: false) print('#') else print (' ')
        }
        println()
    }
}
