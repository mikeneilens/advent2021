fun dice(roll:Int) = listOf( 1 + (3 * (roll -1)) % 100 , 1 + (3 * (roll -1) + 1) % 100 , 1 + (3 * (roll -1) + 2) % 100)

data class Player(var position:Int, var score:Int = 0) {
    fun move(m:Int):Player {
        position = 1 + (position + m - 1) % 10
        return this
    }
}
class Game(val player1:Player, val player2:Player) {
    var rolls = 0
    var player = player1

    fun playGame() {
        while (!hasWon(player1) && !hasWon(player2)) {
            player.move(dice(++rolls)).move(dice(++rolls)).move(dice(++rolls))
            player.score += player.position
            player = if (player == player1) player2 else player1
        }
    }
    fun result() = if (hasWon(player1)) player2.score * rolls else player1.score * rolls

    fun hasWon(player:Player) = player.score >= 1000

    fun dice(r:Int) = 1 + (r -1) % 100
}

data class ScoreCard(var player1Wins:Long = 0L, var player2Wins:Long = 0L) {
    fun highestNumberOfWins() = if (player1Wins > player2Wins) player1Wins else player2Wins
}

val gameHistory = mutableMapOf<Pair<Player, Player>,ScoreCard>()

fun countWin(player1:Player, player2:Player):ScoreCard {
    if (player1.score >= 21) return ScoreCard(1,0)
    if (player2.score >= 21) return ScoreCard(0,1)
    val previousResult = gameHistory[Pair(player1, player2)]
    if (previousResult != null) return previousResult
    val scoreCard = ScoreCard()
    (1..3).forEach { dice1 ->
        (1..3).forEach { dice2 ->
            (1..3).forEach { dice3 ->
                val newPosition = 1 + (player1.position + dice1 + dice2 + dice3 - 1) % 10
                val newPlayer1 = Player(newPosition, player1.score + newPosition )
                val (player2Wins, player1Wins) = countWin(player2, newPlayer1)
                scoreCard.player1Wins += player1Wins
                scoreCard.player2Wins += player2Wins
            }
        }
    }
    gameHistory[Pair(player1, player2)] = scoreCard
    return scoreCard
}
