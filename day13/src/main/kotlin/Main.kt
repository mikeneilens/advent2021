data class Position(val x:Int, val y:Int )

typealias Paper = Set<Position>
typealias PaperFolder = (Paper)->Paper


fun List<String>.parse() = Pair(paper(), paperFolders())

private fun List<String>.paper() = filterNot{it.startsWith("fold")}.map (String::toPosition).toSet()

fun String.toPosition() = Position( split(",").first().toString().toInt(),split(",").last().toString().toInt() )

private fun List<String>.paperFolders():List<PaperFolder> = filter{it.startsWith("fold")}.map(::makePaperFolder)

fun makePaperFolder(foldInstruction:String) = { paper:Paper -> paper.foldAtLine(foldInstruction.lineToFold, foldInstruction.shouldFoldAlongY) }

val String.lineToFold get() = split("=").last().toInt()
val String.shouldFoldAlongY get() = startsWith("fold along y=")

fun Paper.foldAtLine(line: Int, foldOnRow: Boolean):Paper = preservedPositions(this, foldOnRow, line) union foldedPositions(this, foldOnRow, line)

fun preservedPositions(paper: Paper, foldOnRow: Boolean, line: Int) = if (foldOnRow) paper.beforeRow(line) else paper.beforeColumn(line)

fun Paper.beforeRow(row:Int) = filter { it.y < row }.toSet()
fun Paper.beforeColumn(column:Int) = filter { it.x < column }.toSet()

fun foldedPositions(paper: Paper, foldOnRow: Boolean, line: Int) =
    if (foldOnRow) paper.filter { it.y > line }.map { Position(it.x, line * 2 - it.y) }.toSet()
    else paper.filter { it.x > line }.map { Position(line * 2 - it.x, it.y) }.toSet()

fun partOne(data:List<String>):Paper {
    val (paper, paperFolders) = data.parse()
    return paperFolders.first()(paper)
}

fun partTwo(data:List<String>):Paper {
    val (paper, paperFolders) = data.parse()
    return paperFolders.fold(paper){foldedPaper, paperFolder -> paperFolder(foldedPaper) }
}

fun Paper.print() {
    val output = (0..maxOf { it.y }).fold("The result is: \n"){ result, y ->
        (0..maxOf { it.x }).fold(result){ line, x -> if (Position(x,y) in this) "$line#" else "$line " } + '\n'   }
    println(output)
}
