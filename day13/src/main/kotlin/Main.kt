data class Position(val x:Int, val y:Int )

typealias Paper = Map<Position, Boolean>
typealias Folder = (Paper, Int)->Paper

fun String.toPosiiton() =
    Position( split(",").first().toString().toInt(),split(",").last().toString().toInt() )

fun List<String>.parse():Pair<Paper,List<Pair<Folder,Int>>> {
    val map = filter{!it.startsWith("fold")}.map{Pair(it.toPosiiton(),true)}.toMap()
    val folds= filter{it.startsWith("fold along ")}.map(String::getFold)
    return Pair(map, folds)
}

fun String.getFold():Pair<Folder,Int> =
    if (startsWith("fold along y="))  Pair(::foldY, removePrefix("fold along y=").toInt())
    else Pair(::foldX,removePrefix("fold along x=").toInt())

fun foldY(paper:Paper,y:Int):Paper {
    val newMap = paper.toList().filter{it.first.y < y}.toMap().toMutableMap()
    val foldedPositions = paper.toList().filter{it.first.y > y}.map{Position(it.first.x, y * 2 - it.first.y)}
    foldedPositions.forEach {position -> newMap[position] = true }
    return newMap
}
fun foldX(paper:Paper, x:Int):Paper {
    val newMap = paper.toList().filter{it.first.x < x}.toMap().toMutableMap()
    val foldedPositions = paper.toList().filter{it.first.x > x}.map{Position(x * 2 - it.first.x,it.first.y)}
    foldedPositions.forEach {position -> newMap[position] = true }
    return newMap
}

fun partOne(data:List<String>, noOfFolds:Int):Paper {
    val (map, folds) = data.parse()
    var newMap = map.toMutableMap()
    (0 until noOfFolds).forEach {
        val folder = folds[it].first
        newMap = folder(newMap, folds[it].second).toMutableMap()
    }
    return newMap
}
