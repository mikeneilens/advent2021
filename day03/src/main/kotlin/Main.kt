
typealias BinaryString = String

fun List<BinaryString>.mostCommonBitInColumn(column: Int) =
    if (count{it[column]=='0'} > size /2) '0' else '1'

fun gamma(list:List<BinaryString>) =
    (0 until list[0].length).map { ndx -> list.mostCommonBitInColumn(ndx) }.joinToString("")

fun invert(binaryString:BinaryString) = binaryString.map{if (it == '1') '0' else '1'}.joinToString("")

fun partOne(list: List<BinaryString>):Int {
    val gamma = gamma(list)
    val epsilon = invert(gamma)
    return gamma.toInt(2) * epsilon.toInt(2)
}

enum class Rating(val bit:Char) {Oxygen('1'), Scrubber('0')}

fun oxyGenRating(list:List<BinaryString>) = calcRating(list, Rating.Oxygen)

fun scrubberRating(list:List<BinaryString>)= calcRating(list, Rating.Scrubber)

fun calcRating(list:List<BinaryString>, rating:Rating, ndx:Int = 0):String =
    if (list.size == 1) list.first()
    else {
        val filteredList = if (list.mostCommonBitInColumn(ndx) == rating.bit) list.filter { it[ndx] == '1' } else list.filter { it[ndx] == '0' }
        calcRating(filteredList, rating, ndx + 1)
    }

fun partTwo(list:List<BinaryString>) = scrubberRating(list).toInt(2) * oxyGenRating(list).toInt(2)