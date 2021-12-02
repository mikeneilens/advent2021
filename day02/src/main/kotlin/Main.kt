data class Vector (val x:Int=0, val y:Int=0) {
    operator fun plus(other:Vector) = Vector(x + other.x, y + other.y)
    operator fun plus(order:Order) = when(order.instruction) {
        Instruction.Forward -> Vector(x + order.qty, y)
        Instruction.Up -> Vector(x, y - order.qty)
        Instruction.Down -> Vector(x, y + order.qty)
    }
    fun total() =  x * y
}

enum class Instruction{
    Forward, Up, Down;
    companion object {
        fun from(s:String):Instruction {
            return when (s) {
                "forward" -> Forward
                "up" -> Up
                "down" -> Down
                else -> Down
            }
        }
    }
}
data class Order(val instruction:Instruction, val qty:Int )
data class Status(val position:Vector=Vector(),val aim:Int=0)

fun String.toOrder():Order {
    val parts = split(" ")
    return Order(Instruction.from(parts[0]),parts[1].toInt())
}

fun partOne(data:List<String>):Int {
    val orders = data.map(String::toOrder)
    return orders.fold(Status(),::executeOrder).position.total()
}
fun executeOrder(current:Status, order:Order) = Status(current.position + order,current.aim)

fun partTwo(data:List<String>):Int {
    val orders = data.map(String::toOrder)
    return orders.fold(Status(),::executeOrder2).position.total()
}

fun executeOrder2(current:Status, order:Order):Status =
    when (order.instruction) {
        Instruction.Forward -> Status(Vector(current.position.x + order.qty ,current.position.y + current.aim * order.qty), current.aim)
        Instruction.Down -> Status(current.position, current.aim + order.qty)
        Instruction.Up -> Status(current.position, current.aim - order.qty)
    }
