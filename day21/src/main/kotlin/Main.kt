
fun dice(roll:Int) = listOf( 1 + (3 * (roll -1)) % 100 , 1 + (3 * (roll -1) + 1) % 100 , 1 + (3 * (roll -1) + 2) % 100)

data class Player(var position:Int, var score:Int = 0) {
    fun hasWon() = score >= target
    fun move() {
        rolls++
        position = 1 + (position + dice(rolls).sum() - 1) % 10
        score += position
    }
    companion object {
        val target = 1000
        var rolls = 0
    }
}

fun playGame(player1Start:Int, player2Start:Int):Int {
    var player1 = Player(player1Start)
    var player2 = Player(player2Start)

    while (!player1.hasWon() && !player2.hasWon()) {
        player1.move()
        if(!player1.hasWon()) {
            player2.move()
        }
    }
    if (player1.hasWon()) return player2.score * Player.rolls * 3
    else return player2.score * Player.rolls * 3
}

fun newPosition(oldPosition:Int, move:Int):Int = 1 + (oldPosition + move - 1) % 10