
fun List<String>.mostCommonBitInColumn(column: Int) =
    if (map{it[column]}.count{it=='0'} > size /2) '0' else '1'

fun gamma(list:List<String>):String =
    (0 until list[0].length).map { ndx -> list.mostCommonBitInColumn(ndx) }.joinToString("")

fun invert(binaryString:String):String = binaryString.map{if (it == '1') '0' else '1'}.joinToString("")

fun partOne(list: List<String>):Int {
    val gamma = gamma(list)
    val epsilon = invert(gamma)
    return gamma.binToInt() * epsilon.binToInt()
}

fun String.binToInt() = fold(0){ a, v -> 2 * a + v.digitToInt()}

enum class Rating(val bit:Char) {Oxygen('1'), Scrubber('0')}

fun oxyGenRating(list:List<String>) = calcRating(list, Rating.Oxygen)

fun scrubberRating(list:List<String>)= calcRating(list, Rating.Scrubber)

fun calcRating(list:List<String>, rating:Rating, ndx:Int = 0):String =
    if (list.size == 1) list.first()
    else {
        val filteredList = if (list.mostCommonBitInColumn(ndx) == rating.bit) list.filter { it[ndx] == '1' } else list.filter { it[ndx] == '0' }
        calcRating(filteredList, rating, ndx + 1)
    }

fun partTwo(list:List<String>):Int = scrubberRating(list).binToInt() * oxyGenRating(list).binToInt()