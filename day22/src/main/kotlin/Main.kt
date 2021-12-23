import kotlin.math.abs

data class Cuboid(val switch:Boolean, val xRange:LongRange, val yRange:LongRange, val zRange:LongRange) {
    val volume:Long = abs(((xRange.last - xRange.first) + 1 ) * ((yRange.last - yRange.first) + 1) * ((zRange.last - zRange.first) + 1))
}

data class Position(val x:Long, val y:Long, val z:Long)

fun String.toCuboid():Cuboid {
    val switch = startsWith("on")
    val valuesForX = split("=")[1].split(",")[0].split("..").map(String::toLong)
    val valuesForY = split("=")[2].split(",")[0].split("..").map(String::toLong)
    val valuesForZ = split("=")[3].split(",")[0].split("..").map(String::toLong)
    return Cuboid(switch, (valuesForX[0]..valuesForX[1]),(valuesForY[0]..valuesForY[1]),(valuesForZ[0]..valuesForZ[1]))
}

fun Cuboid.toSet():Set<Position> = xRange.flatMap{x -> yRange.flatMap{y->  zRange.map{ z -> Position(x,y,z)  }  }  }.toSet()

fun List<String>.parse() = map(String::toCuboid)

fun List<String>.partOne():Map<Position, Boolean> {
    val map = mutableMapOf<Position,Boolean>()
    parse().filter{it.isInitializer() }.forEach { cuboid ->
          cuboid.xRange.forEach { x-> cuboid.yRange.forEach { y-> cuboid.zRange.forEach { z ->
                      map[Position(x,y,z)] = cuboid.switch
                } } } }
    return map
}
fun Cuboid.isInitializer() = xRange.isInitializer()  && yRange.isInitializer() && zRange.isInitializer()
fun LongRange.isInitializer() = first in -50L..50L && last in -50L..50L

fun List<Cuboid>.partTwo():List<Cuboid> =
    drop(1).fold(listOf(first())){total, cuboid ->
        if (cuboid.switch) subtract(total, cuboid) + cuboid else subtract(total, cuboid)
    }

//Subtracts a range from another ranges giving the ranges that are left over and the ranges that were removed.
tailrec fun subtract(line:LongRange, otherLine:LongRange, rangesToKeep:List<LongRange> = listOf(),rangesRemoved:List<LongRange> = listOf()):Pair<List<LongRange>,List<LongRange>> {
    return when {
        line.last < otherLine.first -> Pair(rangesToKeep + listOf(line),rangesRemoved)
        line.first > otherLine.last -> Pair(rangesToKeep + listOf(line),rangesRemoved)
        line.first >= otherLine.first && line.last <= otherLine.last -> Pair(rangesToKeep,rangesRemoved + listOf(line))
        line.first < otherLine.first ->
            subtract(otherLine.first..line.last, otherLine, rangesToKeep + listOf((line.first until otherLine.first)),rangesRemoved)
        else ->
            subtract(otherLine.last+1..line.last, otherLine, rangesToKeep, rangesRemoved + listOf(line.first..otherLine.last) )
    }
}

fun subtract(cuboids:List<Cuboid>, otherCuboid:Cuboid):List<Cuboid> {
    val halvesForEachCuboidX = cuboids.map{remainingCuboid -> Pair(remainingCuboid,subtract(remainingCuboid.xRange, otherCuboid.xRange))}
    val cuboidsToKeepX = halvesForEachCuboidX.flatMap{ (remainingCuboid,keptOrRemoved ) -> keptOrRemoved.first.map { xRange -> Cuboid(remainingCuboid.switch, xRange, remainingCuboid.yRange, remainingCuboid.zRange)  } }
    val remainingCuboidsX = halvesForEachCuboidX.flatMap{ (remainingCuboid,keptOrRemoved ) -> keptOrRemoved.second.map { xRange -> Cuboid(remainingCuboid.switch, xRange, remainingCuboid.yRange, remainingCuboid.zRange)  } }

    val halvesForEachCuboidY = remainingCuboidsX.map{remainingCuboid -> Pair(remainingCuboid,subtract(remainingCuboid.yRange, otherCuboid.yRange))}
    val cuboidsToKeepY = halvesForEachCuboidY.flatMap{ (remainingCuboid,keptOrRemoved ) -> keptOrRemoved.first.map { yRange -> Cuboid(remainingCuboid.switch, remainingCuboid.xRange, yRange, remainingCuboid.zRange)  } }
    val remainingCuboidsY = halvesForEachCuboidY.flatMap{ (remainingCuboid,keptOrRemoved ) -> keptOrRemoved.second.map { yRange -> Cuboid(remainingCuboid.switch, remainingCuboid.xRange, yRange, remainingCuboid.zRange)  } }

    val halvesForEachCuboidZ = remainingCuboidsY.map{remainingCuboid -> Pair(remainingCuboid,subtract(remainingCuboid.zRange, otherCuboid.zRange))}
    val cuboidsToKeepZ = halvesForEachCuboidZ.flatMap{ (remainingCuboid,keptOrRemoved ) -> keptOrRemoved.first.map { zRange -> Cuboid(remainingCuboid.switch, remainingCuboid.xRange, remainingCuboid.yRange, zRange)  } }
    return cuboidsToKeepX + cuboidsToKeepY + cuboidsToKeepZ
}
