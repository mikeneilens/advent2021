
data class Position(val x:Int, val y:Int) {
    fun positionsOfBits() = listOf(
        Position(x-1,y-1),Position(x,y-1),Position(x+1,y-1),
        Position(x-1,y),Position(x,y),Position(x+1,y),
        Position(x-1,y+1),Position(x,y+1),Position(x+1,y+1)
    )

}

typealias Image = Set<Position>

fun createImage(data:List<String>):Image {
    val image = mutableSetOf<Position>()
    data.forEachIndexed {y, row -> 
        row.forEachIndexed { x, c -> if (c == '#') image.add(Position(x,y)) }
    }
    return image
}

data class Boundary(val xRange:IntRange, val yRange:IntRange)

fun Image.boundary(borderSize:Int) =
    Boundary( (toList().minOf { it.x } - borderSize)..(toList().maxOf { it.x } + borderSize),
        (toList().minOf { it.y } - borderSize)..(toList().maxOf { it.y } + borderSize)
    )

fun Image.getBinary(position: Position): String =
    position.positionsOfBits().joinToString("") { if (it in this) "1" else "0" }

fun Image.applyAlgorithmToImage(algorithm:String, borderSize:Int):Image {
    val output = mutableSetOf<Position>()
    boundary(borderSize).yRange.forEach{y ->
        boundary(borderSize).xRange.forEach { x->
            val ndx = getBinary(Position(x,y)).toInt(2)
            if (algorithm[ndx] == '#') output.add(Position(x,y))
        }
    }
    return output
}

fun Image.text():String {
    var output = ""
    boundary(2).yRange.forEach{y ->
        boundary(2).xRange.forEach { x->
            output = if (Position(x,y) in this) "$output#" else "$output."
        }
        output = "$output\n"
    }
    return output
}

fun partOne(algorithm:String, data:List<String>):Int =
    createImage(data)
        .applyAlgorithmToImage(algorithm,3)
        .applyAlgorithmToImage(algorithm,if (algorithm[0] =='#') -1 else 3).size

fun partTwo(algorithm:String, data:List<String>):Int {
    var image = createImage(data)
    (1..25).forEach { _ ->
        image = image.applyAlgorithmToImage(algorithm,3)
        image = image.applyAlgorithmToImage(algorithm,if (algorithm[0] =='#') -1 else 3)
    }
    return image.size
}
