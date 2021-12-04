typealias CalledNumbers = MutableList<Boolean> //stores whether a number has been called or not.

fun getNumbers(data:String):List<Int> =
    data.split("\n\n").first().split(",").map(String::toInt)

fun getBingoCards(data:String):List<BingoCard> =
    data.split("\n\n")
        .drop(1)
        .map{it.split("\n").map(::rowToBingoCardNumbers)}.map(BingoCard::make)

fun rowToBingoCardNumbers(row:String) = row.trimStart()
    .replace("  "," ")
    .split(" ")
    .map{ it.toInt() }

data class BingoCard(val numbers:List<List<Int>>, val rotatedNumbers:List<List<Int>>, var hasWon:Boolean = false) {
    fun refreshWinStatus(calledNumbers: CalledNumbers):BingoCard {
        this.hasWon = hasWinningNumbers(calledNumbers)
        return this
    }

    fun hasWinningNumbers(calledNumbers: CalledNumbers) =
        numbers.hasWinningRow(calledNumbers) || rotatedNumbers.hasWinningRow(calledNumbers)

    fun sumOfNumbersNotCalled(calledNumbers: CalledNumbers) =
        numbers.sumOf { row -> row.filter { !calledNumbers[it] }.sum() }

    companion object {
        fun make(numbers:List<List<Int>>) = BingoCard(numbers,numbers.rowToColumn() )
        fun List<List<Int>>.hasWinningRow(calledNumbers: CalledNumbers)= any{ row -> row.all{calledNumbers[it]}}
    }
}
fun <T>List<List<T>>.rowToColumn() = (0 until first().size).map{row -> (0 until size).map {col-> this[col][row]  }}

fun findFirstlWinner(numbers:List<Int>, bingoCards:List<BingoCard>, calledNumbers:CalledNumbers ):Pair<Int, BingoCard> {
    val (winningBingoCard, lastNumberCalled) =  (numbers.indices).asSequence().mapNotNull{ ndx ->
        calledNumbers[numbers[ndx]] = true
        val possibleWinner = bingoCards.map{ bingoCard ->  bingoCard.refreshWinStatus(calledNumbers)}.firstOrNull{it.hasWon}
        if (possibleWinner != null) Pair(possibleWinner, numbers[ndx]) else null
    }.first ()
    return Pair(lastNumberCalled, winningBingoCard)
}

fun findWinner(data:String, winningBingoCardFinder:(List<Int>,List<BingoCard>,CalledNumbers)->Pair<Int, BingoCard>):Int {
    val numbers = getNumbers(data)
    val bingoCards = getBingoCards(data)
    val calledNumbers = MutableList(100){false}
    val (lastNumberCalled, winningBingoCard) = winningBingoCardFinder(numbers, bingoCards, calledNumbers)
    return winningBingoCard.sumOfNumbersNotCalled(calledNumbers) * lastNumberCalled
}

fun partOne(data:String) = findWinner(data, ::findFirstlWinner)

tailrec fun findLastWinner(numbers:List<Int>, bingoCards:List<BingoCard>, calledNumbers: CalledNumbers):Pair<Int, BingoCard> {
    val (lastNumberCalled, winningBingoCard) = findFirstlWinner(numbers, bingoCards, calledNumbers)
    return if (bingoCards.any { !it.hasWon }) {
        findLastWinner(numbers, bingoCards.filter{!it.hasWon}, calledNumbers)
    } else {
        Pair(lastNumberCalled,winningBingoCard)
    }
}

fun partTwo(data:String) = findWinner(data, ::findLastWinner)


