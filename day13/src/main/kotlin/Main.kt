data class Position(val x:Int, val y:Int )

typealias Paper = Set<Position>
typealias PaperFolder = (Paper)->Paper

fun String.toPosition() =
    Position( split(",").first().toString().toInt(),split(",").last().toString().toInt() )

fun List<String>.parse() = Pair(paper(), paperFolders())

private fun List<String>.paper() = filterNot{it.startsWith("fold")}.map { it.toPosition() }.toSet()
private fun List<String>.paperFolders():List<PaperFolder> = filter{it.startsWith("fold")}.map(::getPaperFolder)

fun getPaperFolder(foldInstruction:String) = {paper:Paper -> foldAtLine(paper, foldInstruction.lineToFold, foldInstruction.shouldFoldAlongY)}

val String.lineToFold get() = split("=").last().toInt()
val String.shouldFoldAlongY get() = startsWith("fold along y=")

fun foldAtLine(paper:Paper, line:Int, foldOnRow:Boolean):Paper {
    val foldedPaper = foldedPaper(paper, foldOnRow, line)
    return foldedPositions(paper, foldOnRow, line).fold(foldedPaper){result, position -> result + position}
}

fun foldedPaper(paper: Paper, foldOnRow: Boolean, line: Int) = if (foldOnRow) paper.filter { it.y < line }.toSet() else paper.filter { it.x < line }.toSet()

fun foldedPositions(paper: Paper, foldOnRow: Boolean, line: Int) =
    if (foldOnRow) paper.filter { it.y > line }.map { Position(it.x, line * 2 - it.y) }
    else paper.filter { it.x > line }.map { Position(line * 2 - it.x, it.y) }

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
