fun getNumbers(data:String):List<Int> =
    data.split("\n\n").first().split(",").map(String::toInt)

data class Board(val numbers:List<List<BoardItem>>, var won:Boolean = false   )
data class BoardItem(val number:Int, var called:Boolean = false)

fun getBoards(data:String):List<Board> {
    return data.split("\n\n").drop(1).map{it.split("\n").map(::rowToBoardNumbers)}.map(::Board)
}
fun rowToBoardNumbers(row:String) = row.trimStart()
    .replace("  "," ")
    .split(" ")
    .map{ BoardItem(it.toInt()) }

fun callNumberUntilWinner(numbers:List<Int>, boards:List<Board>):Pair<Int, Board> {
    var winner:Board? = null

    var call = 0
    while (winner==null && call < numbers.size) {
        updateBoardWithNumber(boards, numbers[call])
        winner = boards.firstOrNull { winningNumbers(it) != null }
        if (winner == null) call ++
    }
    return if (winner != null) Pair(numbers[call], winner) else Pair(numbers[call], Board(listOf()))
}

fun updateBoardWithNumber(boards:List<Board>, number:Int) {
    boards.forEach { board ->
        board.numbers.forEach{
            row->
            row.forEach { boardItem -> if(boardItem.number == number) boardItem.called = true }
        }
    }
}

fun winningNumbers(board:Board):Board? {
    val winningRow = winningRow(board.numbers)
    if (winningRow ) return board
    val winningColumn = winningColumn(board.numbers)
    if (winningColumn ) return board
    return null
}

fun partOne(data:String):Int {
    val numbers = getNumbers(data)
    val boards = getBoards(data)
    val (numberCalled, winningBoard) = callNumberUntilWinner(numbers, boards)
    val numbersNotCalled =  winningBoard.numbers.map{ row -> row.filter{!it.called}.sumOf { it.number }}.sum()
    return numbersNotCalled * numberCalled
}

fun printBoard(board:Board) {
    println("won ${board.won}")
    board.numbers.forEach { row ->
        val line = row.map{if (it.called) "Y" else "N"}.joinToString("")
        println(line)
    }
    println()
}
fun winningRow(board:List<List<BoardItem>>)= board.any{it.all{it.called}}

fun winningColumn(board:List<List<BoardItem>>) =  winningRow(board.rowToColumn())

fun <T>List<List<T>>.rowToColumn() = (0 until first().size).map{row -> (0 until size).map {col-> this[col][row]  }}

fun callNumberUntilOneLoser(numbers:List<Int>, boards:List<Board>):Pair<Int, Board> {
    var call = 0
    var lastWinner:Board? = null
    while (boards.count{!it.won} > 0 && call < numbers.size) {
        updateBoardWithNumber(boards, numbers[call])
        var boardNdx = 0
        while ( boardNdx < boards.size && boards.count{!it.won} > 0  ) {
            if (!boards[boardNdx].won && winningNumbers(boards[boardNdx]) != null )  {
                lastWinner = boards[boardNdx]
                boards[boardNdx].won = true
            }
            boardNdx ++
        }
        if (boards.count{!it.won} > 0) call ++
    }
    return if (lastWinner != null) Pair(numbers[call],lastWinner) else Pair(0,Board(listOf()))
}

fun partTwo(data:String):Int {
    val numbers = getNumbers(data)
    val boards = getBoards(data)
    val (numberCalled, losingBoard) = callNumberUntilOneLoser(numbers, boards)
    val numbersNotCalled =  losingBoard.numbers.map{ row -> row.filter{!it.called}.sumOf { it.number }}.sum()
    return numbersNotCalled * numberCalled
}


