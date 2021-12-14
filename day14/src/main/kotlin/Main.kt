fun List<String>.parse() = Pair(polymerTemplate(), insertionRules())

fun List<String>.polymerTemplate() = first()
fun List<String>.insertionRules() = drop(2).map(String::polymer).toMap()
fun String.polymer() = Pair(split(" -> ")[0], split(" -> ")[1])

fun applyPolymer(polymerTemplate:List<String>, insertionRules:Map<String,String>):List<String> =
    polymerTemplate.flatMap{listOf("${it[0]}${insertionRules[it]}","${insertionRules[it]}${it[1]}")  }

fun partOne(data:List<String>):Int {
    val (polymerTemplate, insertionRules) = data.parse()
    var result = polymerTemplate.windowed(2,1)
    (1..10).forEach {
        result = applyPolymer(result, insertionRules)
    }
    val concatonated =   result.map{it.last()}.joinToString("")
    val sortedResult =concatonated.groupingBy { it }.eachCount().toList().sortedBy { it.second }
    return sortedResult.last().second - sortedResult.first().second
}

fun partTwo(data:List<String>,steps:Int):Long {
    val (polymerTemplate, insertionRules) = data.parse()
    var qtyPerElementPair = mutableMapOf<String,Long>()

    polymerTemplate.windowed(2,1).forEach { elementPair -> qtyPerElementPair.increment(elementPair,1)   }

    (0 until steps).forEach { qtyPerElementPair = qtyPerElementPair.splitAllElementPairs(insertionRules).toMutableMap() }

    val qtyOfEachChar = qtyPerElementPair.countChars(polymerTemplate)
    return qtyOfEachChar.toList().maxOf { it.second } - qtyOfEachChar.toList().minOf { it.second }
}

fun Map<String,Long>.splitAllElementPairs(insertionRules:Map<String, String>):Map<String,Long> {
    val updatedQtyPerElementPair = mutableMapOf<String,Long>()
    toList().forEach { (key, value) ->
        val (elementPair1, elementPair2) = splitElementPair(key, insertionRules)
        updatedQtyPerElementPair.increment(elementPair1, value)
        updatedQtyPerElementPair.increment(elementPair2, value)
    }
    return updatedQtyPerElementPair
}

fun splitElementPair( elementPair:String, insertionRules:Map<String, String>) =
    Pair((elementPair[0] + insertionRules.getValue(elementPair)).toString(),
    (insertionRules.getValue(elementPair) + elementPair[1]).toString())

fun MutableMap<String,Long>.increment(key:String, num:Long) = set(key, getOrDefault(key,0) + num)

fun Map<String,Long>.countChars(polymerTemplate: String):Map<Char, Long> {
    val results = mutableMapOf<Char, Long>(polymerTemplate.last() to 1)
    toList().forEach { (key, value) ->
        results[key.first()] = (results[key.first()] ?: 0) + value
    }
    return results
}