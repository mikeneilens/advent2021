
data class Player(var position:Int, var score:Int = 0) {
    fun move(m:Int):Player {
        position = 1 + (position + m - 1) % 10
        return this
    }
    fun moveAndCreatePlayer(m:Int):Player {
        val newPosition = 1 + (position + m - 1) % 10
        return Player(newPosition, score + newPosition)
    }
}

class Game(private val player1:Player, private val player2:Player) {
    private var rolls = 0
    private var player = player1

    fun playGame() {
        while (!hasWon(player1) && !hasWon(player2)) {
            player.move(dice(++rolls)).move(dice(++rolls)).move(dice(++rolls))
            player.score += player.position
            player = if (player == player1) player2 else player1
        }
    }
    fun result() = if (hasWon(player1)) player2.score * rolls else player1.score * rolls

    private fun hasWon(player:Player) = player.score >= 1000

    private fun dice(r:Int) = 1 + (r -1) % 100
}

data class ScoreCard(var noPlayer1Wins:Long = 0L, var noOfPlayer2Wins:Long = 0L) {
    fun highestNumberOfWins() = if (noPlayer1Wins > noOfPlayer2Wins) noPlayer1Wins else noOfPlayer2Wins
}

val gameHistory = mutableMapOf<Pair<Player, Player>,ScoreCard>()

fun countWins(player1:Player, player2:Player):ScoreCard {
    if (player1.score >= 21) return ScoreCard(1,0)
    if (player2.score >= 21) return ScoreCard(0,1)
    val previousResult = gameHistory[Pair(player1, player2)]
    if (previousResult != null) return previousResult

    val scoreCard = ScoreCard()
    (1..3).forEach { dice1 ->
        (1..3).forEach { dice2 ->
            (1..3).forEach { dice3 ->
                val newPlayer1 = player1.moveAndCreatePlayer(dice1 + dice2 + dice3)
                val (player2Wins, player1Wins) = countWins(player2, newPlayer1)
                scoreCard.noPlayer1Wins += player1Wins
                scoreCard.noOfPlayer2Wins += player2Wins
            }
        }
    }

    gameHistory[Pair(player1, player2)] = scoreCard
    return scoreCard
}
