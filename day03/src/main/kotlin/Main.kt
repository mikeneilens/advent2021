fun mostCommonInColumn(list: List<String>, column: Int) =
    if (list.map{it[column]}.count{it=='0'} > list.size /2) 0 else 1

fun gamma(list:List<String>):Int {
    var result = 0
    (0 until list[0].length).forEach { ndx ->
        val power = list[0].length - ndx - 1
        val n = Math.pow(2.0, power.toDouble()) * mostCommonInColumn(list, ndx)
        result += n.toInt()
    }
    return result
}
fun epsilon(list:List<String>, gamma:Int):Int {
    return Math.pow(2.0, list[0].length.toDouble()).toInt() - 1 - gamma
}
fun partOne(list: List<String>):Int {
    val gamma = gamma(list)
    val epsilon = epsilon(list,gamma)
    return gamma * epsilon
}
fun binToInt(s:String):Int {
    var result = 0
    s.forEachIndexed { ndx,char ->
        val power = s.length - ndx - 1
        result += if (char == '1') Math.pow(2.0, power.toDouble()).toInt() else 0
    }
    return result
}

fun oxyGenRating(list:List<String>, ndx:Int = 0):String {
    return if (list.size == 1) list.first()
    else {
        val filtered = if (mostCommonInColumn(list,
                ndx) == 1) list.filter { it[ndx] == '1' } else list.filter { it[ndx] == '0' }
        oxyGenRating(filtered, ndx + 1)
    }
}
fun scrubberRating(list:List<String>, ndx:Int = 0):String {
    return if (list.size == 1) list.first()
    else {
        val filtered = if (mostCommonInColumn(list, ndx) != 1) list.filter { it[ndx] == '1' } else list.filter { it[ndx] == '0' }
        scrubberRating(filtered, ndx + 1)
    }
}
fun partTwo(list:List<String>):Int = binToInt(scrubberRating(list)) * binToInt(oxyGenRating(list))