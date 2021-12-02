data class Vector (val x:Int=0, val y:Int=0) {
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
fun executeOrder(current:Status, order:Order) = when(order.instruction) {
    Instruction.Forward -> Status(Vector(current.position.x + order.qty, current.position.y),current.aim)
    Instruction.Up -> Status(Vector(current.position.x, current.position.y - order.qty),current.aim)
    Instruction.Down -> Status(Vector(current.position.x, current.position.y + order.qty),current.aim)
}

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
