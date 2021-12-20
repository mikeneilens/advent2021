
data class Position(val x:Int, val y:Int) {
    fun rows() = ((y-1)..(y+1)).map{row ->
        ((x-1)..(x+1)).map{col -> Position(col, row) }
    }
}

typealias Image = Set<Position>

fun createImage(data:List<String>):Image {
    val image = mutableSetOf<Position>()
    data.forEachIndexed {y, row -> 
        row.forEachIndexed { x, c -> if (c == '#') image.add(Position(x,y)) }
    }
    return image
}

fun Image.getBinary(position: Position): String =
    position.rows().map{row -> row.map{  if ( it in this ) "1" else "0" }.joinToString("") }.joinToString ("")

fun Image.applyAlgorithmToImage(algorithm:String, borderSize:Int):Image {
    val output = mutableSetOf<Position>()
    val minX = toList().minOf { it.x } - borderSize
    val minY = toList().minOf { it.y } - borderSize
    val maxX = toList().maxOf { it.x } + borderSize
    val maxY = toList().maxOf { it.y } + borderSize
    (minY..maxY).forEach{y ->
        (minX..maxX).forEach { x->
            val ndx = getBinary(Position(x,y)).toInt(2)
            if (algorithm[ndx] == '#') output.add(Position(x,y))
        }
    }
    return output
}

fun Image.text():String {
    var output = ""
    val minX = toList().minOf { it.x } - 2
    val minY = toList().minOf { it.y } - 2
    val maxX = toList().maxOf { it.x } + 2
    val maxY = toList().maxOf { it.y } + 2
    (minY..maxY).forEach{y ->
        (minX..maxX).forEach { x->
            if (Position(x,y) in this) output = "$output#" else output = "$output."
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
    (1..25).forEach {
        image = image.applyAlgorithmToImage(algorithm,3)
        image = image.applyAlgorithmToImage(algorithm,if (algorithm[0] =='#') -1 else 3)
    }
    return image.size
}
