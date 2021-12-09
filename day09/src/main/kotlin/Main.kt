
data class Position(val x:Int, val y:Int ) {

    fun surroundingPositions(maxX:Int, maxY:Int):List<Position> =
        listOf(Position(x - 1, y),Position(x , y - 1),Position(x + 1, y),Position(x , y + 1))
            .filter{it.x >= 0 && it.x <= maxX && it.y >= 0 && it.y <= maxY}

}

data class HeightMap(val map:Map<Position, Int> ) {

    val maxX:Int by lazy { map.keys.maxOf { it.x } }
    val maxY:Int by lazy { map.keys.maxOf { it.y } }

    fun height(position:Position) = map.getValue(position)
}

fun parse(data:List<String>):HeightMap {
    val heightMap = mutableMapOf<Position, Int>()
    data.forEachIndexed { y, row ->
        row.mapIndexed{x, char -> heightMap[Position(x,y)] = "$char".toInt()}
    }
    return HeightMap(heightMap)
}

fun HeightMap.isLowerThanSurroundings(position:Position) =
    position.surroundingPositions(maxX,maxY).all { surroundingPosition -> height(surroundingPosition) > height(position) }


fun partOne(data:List<String>):Int {
    val heatMap = parse(data)
    return heatMap.map
        .keys
        .filter{ position -> heatMap.isLowerThanSurroundings(position)}
        .sumOf { lowPosition ->  heatMap.height(lowPosition) + 1 }
}

fun HeightMap.higherSurroundingPoints(position:Position, higherPositions:List<Position> = listOf()): List<Position> {
    val higherSurroundingPositions = position
        .surroundingPositions(maxX, maxY)
        .filter{surroundingPosition -> height(surroundingPosition) != 9 && height(surroundingPosition) > height(position)}

    return if (higherSurroundingPositions.isEmpty())
        higherPositions + position
    else
        higherSurroundingPositions.flatMap{surroundingPosition -> higherSurroundingPoints(surroundingPosition,higherPositions + position)}
}

fun HeightMap.basin(position:Position) = higherSurroundingPoints(position).distinct()

fun partTwo(data:List<String>):Int {
    val heatMap = parse(data)
    val biggestBasins = heatMap.map.keys
        .filter{ position -> heatMap.isLowerThanSurroundings(position)}
        .map{position -> heatMap.basin(position) }
        .sortedBy {basin -> basin.size }
        .takeLast(3)
        .map{it.size}
    return biggestBasins[0] * biggestBasins[1] * biggestBasins[2]
}