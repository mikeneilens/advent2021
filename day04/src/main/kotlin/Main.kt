fun getNumbers(data:String):List<Int> =
    data.split("\n\n").first().split(",").map(String::toInt)

data class BingoCard(val numbers:List<List<Int>>, val rotatedNumbers:List<List<Int>>, private var won:Boolean = false) {
    fun setWon(hasWon:Boolean):BingoCard {
        this.won = hasWon
        return this
    }
    val hasWon  get() = won
}

fun getBingoCards(data:String):List<BingoCard> =
    data.split("\n\n").drop(1).map{it.split("\n").map(::rowToBingoCardNumbers)}.map{BingoCard(it,it.rowToColumn())}

fun rowToBingoCardNumbers(row:String) = row.trimStart()
    .replace("  "," ")
    .split(" ")
    .map{ it.toInt() }

fun <T>List<List<T>>.rowToColumn() = (0 until first().size).map{row -> (0 until size).map {col-> this[col][row]  }}

fun callNumberUntilWinner(numbers:List<Int>, bingoCards:List<BingoCard>, calledNumbers:MutableList<Boolean> ):Pair<Int, BingoCard> {
    val (bingoCard, lastNumberCalled) =  (numbers.indices).asSequence().mapNotNull{ ndx ->
        calledNumbers[numbers[ndx]] = true
        val possibleWinner = bingoCards.map{ bingoCard ->  bingoCard.setWon(bingoCard.hasWon(calledNumbers))}.firstOrNull{it.hasWon}
        if (possibleWinner != null) Pair(possibleWinner, numbers[ndx]) else null
    }.first ()
    return Pair(lastNumberCalled, bingoCard)
}

fun BingoCard.hasWon(calledNumbers: MutableList<Boolean>) =
    winningRow(numbers, calledNumbers) || winningRow(rotatedNumbers, calledNumbers )

fun winningRow(bingoCardNumbers:List<List<Int>>, calledNumbers: MutableList<Boolean>)= bingoCardNumbers.any{row -> row.all{calledNumbers[it]}}

fun partOne(data:String):Int {
    val numbers = getNumbers(data)
    val bingoCards = getBingoCards(data)
    val calledNumbers = MutableList(100){false}
    val (numberCalled, winningBingoCard) = callNumberUntilWinner(numbers, bingoCards, calledNumbers)
    return winningBingoCard.numbersNotCalled(calledNumbers) * numberCalled
}

fun BingoCard.numbersNotCalled(calledNumbers: MutableList<Boolean>) =
    numbers.sumOf { row -> row.filter { !calledNumbers[it] }.sum() }

tailrec fun callNumberUntilOneLoser(numbers:List<Int>, bingoCards:List<BingoCard>, calledNumbers: MutableList<Boolean>):Pair<Int, BingoCard> {
    val (lastNumberCalled, winningBingoCard) = callNumberUntilWinner(numbers, bingoCards, calledNumbers)
    return if (bingoCards.any { !it.hasWon }) {
        callNumberUntilOneLoser(numbers, bingoCards.filter{!it.hasWon}, calledNumbers)
    } else {
        Pair(lastNumberCalled,winningBingoCard)
    }
}

fun partTwo(data:String):Int {
    val numbers = getNumbers(data)
    val bingoCards = getBingoCards(data)
    val calledNumbers = MutableList(100){false}
    val (lastNumberCalled, losingBoard) = callNumberUntilOneLoser(numbers, bingoCards,calledNumbers)
    return losingBoard.numbersNotCalled(calledNumbers) * lastNumberCalled
}


