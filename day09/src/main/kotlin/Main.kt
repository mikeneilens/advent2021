
data class Position(val x:Int, val y:Int ) {
    fun surroundingPositions(maxX:Int, maxY:Int):List<Position> {
        val result = mutableListOf<Position>()
        if (x > 0 ) result += Position(x - 1, y)
        if (y > 0 ) result += Position(x , y - 1)
        if (x < maxX ) result += Position(x + 1, y)
        if (y < maxY ) result += Position(x , y + 1)
        return result
    }
}

typealias HeightMap = Map<Position, Int>
fun HeightMap.height(position:Position) = getValue(position)

fun parse(data:List<String>):Triple<Map<Position, Int>, Int, Int> {
    val heightMap = mutableMapOf<Position, Int>()
    data.forEachIndexed { y, row ->
        row.mapIndexed{x, char -> heightMap[Position(x,y)] = "$char".toInt()}
    }
    return Triple(heightMap, heightMap.keys.maxOf { it.x }, heightMap.keys.maxOf{it.y})
}

fun Map<Position, Int>.isLowerThanSurroundings(position:Position, maxX:Int, maxY:Int):Boolean {
    val height = height(position)
    return position.surroundingPositions(maxX,maxY).all { surroundingPosition -> height(surroundingPosition) > height }
}

fun partOne(data:List<String>):Int {
    val (heatMap, maxX, maxY) = parse(data)
    return heatMap
        .keys
        .filter{ position -> heatMap.isLowerThanSurroundings(position, maxX, maxY)}
        .sumOf { lowPosition ->  heatMap.height(lowPosition) + 1 }
}

fun HeightMap.higherSurroundingPoints(position:Position, maxX:Int, maxY:Int, higherPositions:List<Position> = listOf()): List<Position> {
    val higherSurroundingPositions = position
        .surroundingPositions(maxX, maxY)
        .filter{surroundingPosition -> height(surroundingPosition) > height(position) && height(surroundingPosition) != 9 }

    return if (higherSurroundingPositions.isEmpty())
        higherPositions + position
    else
        higherSurroundingPositions.flatMap{surroundingPosition -> higherSurroundingPoints(surroundingPosition, maxX, maxY, higherPositions + position)}
}

fun HeightMap.basin(position:Position, maxX:Int, maxY:Int) = higherSurroundingPoints(position, maxX, maxY).distinct()

fun partTwo(data:List<String>):Int {
    val (heatMap, maxX, maxY) = parse(data)
    val biggestBasins = heatMap.keys
        .filter{ position -> heatMap.isLowerThanSurroundings(position, maxX, maxY)}
        .map{position -> heatMap.basin(position, maxX, maxY) }
        .sortedBy {basin -> basin.size }
        .takeLast(3)
        .map{it.size}
    return biggestBasins[0] * biggestBasins[1] * biggestBasins[2]
}