data class Position(val x:Int, val y:Int )

typealias Paper = Set<Position>
typealias PaperFolder = (Paper)->Paper

fun String.toPosition() =
    Position( split(",").first().toString().toInt(),split(",").last().toString().toInt() )

fun List<String>.parse():Pair<Paper,List<PaperFolder>> {
    val papers = filter { !it.startsWith("fold") }.map{it.toPosition()}.toSet()
    val paperFolders= filter{it.startsWith("fold along ")}.map(String::getPaperFolder)
    return Pair(papers, paperFolders)
}

fun String.getPaperFolder():PaperFolder {
    if (startsWith("fold along y="))  {
        val y = removePrefix("fold along y=").toInt()
        return {paper:Paper -> foldAtLine(paper, y, true)}
    } else {
        val x = removePrefix("fold along x=").toInt()
        return {paper:Paper -> foldAtLine(paper, x, false)}
    }
}

fun foldAtLine(paper:Paper, n:Int, foldOnRow:Boolean):Paper {
    val foldedPaper = if (foldOnRow) paper.filter{it.y < n}.toMutableSet() else paper.toList().filter{it.x < n}.toMutableSet()
    val foldedPositions = if (foldOnRow)
            paper.filter{it.y > n}.map{Position(it.x, n * 2 - it.y)}
        else
            paper.filter{it.x > n}.map{Position(n * 2 - it.x,it.y)}
    foldedPositions.forEach {position -> foldedPaper.add(position) }
    return foldedPaper
}

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
