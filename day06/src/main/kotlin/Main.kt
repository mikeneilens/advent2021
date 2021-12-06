
fun partOne(data:String, days:Int):Long {
    var fishMap = turnListOfNumbersIntoMap(data)
    (0 until days).forEach {
        val newEights = fishMap[0]
        val newSevens = fishMap[8]
        val newSixes = if (fishMap[7] == null && fishMap[0] == null) null
        else if (fishMap[7] != null && fishMap[0] != null) fishMap[7]!! + fishMap[0]!!
        else if(fishMap[7] != null) fishMap[7]!!
        else fishMap[0]!!
        val newFives = fishMap[6]
        val newFours = fishMap[5]
        val newThrees = fishMap[4]
        val newTwos = fishMap[3]
        val newOnes = fishMap[2]
        val newZeroes = fishMap[1]
        fishMap = mapOf(0 to newZeroes, 1 to newOnes, 2 to newTwos, 3 to newThrees, 4 to newFours, 5 to newFives, 6 to newSixes, 7 to newSevens, 8 to newEights)
    }
    return fishMap.values.filter{it!=null}.sumOf{it!!}
}

fun turnListOfNumbersIntoMap(data:String):Map<Int, Long?> {
    val fishMap = mutableMapOf<Int, Long>()
    val fishAges = data.split(",").map(String::toInt)
    fishAges.forEach { fishAge ->
        fishMap[fishAge] = fishMap.getOrDefault(fishAge, 0L) + 1L
    }
    return fishMap
}