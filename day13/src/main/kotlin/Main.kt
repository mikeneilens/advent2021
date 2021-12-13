data class Position(val x:Int, val y:Int )

typealias Paper = Set<Position>
typealias PaperFolder = (Paper)->Paper

fun String.toPosition() =
    Position( split(",").first().toString().toInt(),split(",").last().toString().toInt() )

fun List<String>.parse():Pair<Paper,List<PaperFolder>> {
    val papers = filter { !it.startsWith("fold") }.map{it.toPosition()}.toSet()
    val paperFolders= filter{it.startsWith("fold ")}.map(String::getPaperFolder)
    return Pair(papers, paperFolders)
}

fun String.getPaperFolder():PaperFolder  = {paper:Paper -> foldAtLine(paper, foldLine(), startsWith("fold along y="))}

fun String.foldLine() = split("=").last().toInt()

fun foldAtLine(paper:Paper, line:Int, foldOnRow:Boolean):Paper {
    val foldedPaper = foldedPaper(paper, foldOnRow, line)
    foldedPositions(paper, foldOnRow, line).forEach { position -> foldedPaper.add(position) }
    return foldedPaper
}

fun foldedPaper(paper: Paper, foldOnRow: Boolean, line: Int) = if (foldOnRow) paper.filter { it.y < line }.toMutableSet() else paper.toList().filter { it.x < line }.toMutableSet()

fun foldedPositions(paper: Paper, foldOnRow: Boolean, line: Int) =
    if (foldOnRow)
        paper.filter { it.y > line }.map { Position(it.x, line * 2 - it.y) }
    else
        paper.filter { it.x > line }.map { Position(line * 2 - it.x, it.y) }

fun partOne(data:List<String>):Paper {
    val (paper, paperFolders) = data.parse()
    return paperFolders.first()(paper)
}

fun partTwo(data:List<String>):Paper {
    val (paper, paperFolders) = data.parse()
    var foldedPaper = paper
    paperFolders.forEach { paperFolder ->
        foldedPaper = paperFolder(foldedPaper)
    }
    return foldedPaper
}

fun Paper.print() {
    val maxX = maxOf { it.x }
    val maxY = maxOf { it.y }
    (0..maxY).forEach{ y ->
        (0..maxX).forEach{ x ->
            if ( Position(x,y) in this) print('#') else print (' ')
        }
        println()
    }
}
