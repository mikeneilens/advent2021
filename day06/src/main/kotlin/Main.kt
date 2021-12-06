
fun partOne(data:String, days:Int):Long {
    var fishMap = turnListOfNumbersIntoMap(data)
    (0 until days).forEach {
        val newFishAges = listOf(1,2,3,4,5,6,7,8,0).mapIndexed {i,v ->Pair(i,fishMap[v])}.toMap().toMutableMap()
        newFishAges[6] = newFishesAgedSix(fishMap)
        fishMap = newFishAges
    }
    return fishMap.values.filter{it!=null}.sumOf{it!!}
}

fun newFishesAgedSix(fishMap: Map<Int, Long?>) =
    if (fishMap[7] == null && fishMap[0] == null) null
    else if (fishMap[7] != null && fishMap[0] != null) fishMap[7]!! + fishMap[0]!!
    else if (fishMap[7] != null) fishMap[7]!!
    else fishMap[0]

fun turnListOfNumbersIntoMap(data:String):Map<Int, Long?> {
    val fishMap = mutableMapOf<Int, Long>()
    data.split(",").map(String::toInt).forEach { fishAge ->
        fishMap[fishAge] = fishMap.getOrDefault(fishAge, 0L) + 1L
    }
    return fishMap
}