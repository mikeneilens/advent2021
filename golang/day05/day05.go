package day05

import (
	"advent/parser"
	"strconv"
	"strings"
)

type point struct {
	x int
	y int
}
type line struct {
	start point
	end   point
}

func partOne(data string) int {
	var ventMap = make(map[point]int)
	updateMap(data, ventMap, false)
	return noOfPointsWithMoreThanOneVent(ventMap)
}
func partTwo(data string) int {
	var ventMap = make(map[point]int)
	updateMap(data, ventMap, true)
	return noOfPointsWithMoreThanOneVent(ventMap)
}

func parseInput(data string) []line {
	linesAsString := parser.ParseIntoListOfWords(data)
	var result = make([]line, len(linesAsString))
	for i, lineAsString := range linesAsString {
		startParts := strings.Split(lineAsString[0], ",")
		endParts := strings.Split(lineAsString[2], ",")
		result[i] = line{start: makePoint(startParts), end: makePoint(endParts)}
	}

	return result
}

func makePoint(parts []string) point {
	x, _ := strconv.Atoi(parts[0])
	y, _ := strconv.Atoi(parts[1])
	return point{x: x, y: y}
}

func updateMap(data string, ventsMap map[point]int, includeDiagonals bool) {
	lines := parseInput(data)
	for _, line := range lines {
		updateMapWithLine(line, ventsMap, includeDiagonals)
	}
}

func updateMapWithLine(l line, ventsMap map[point]int, includeDiagonals bool) {
	var xIncrement = getIncrement(l.start.x, l.end.x)
	var yIncrement = getIncrement(l.start.y, l.end.y)
	var max = multiplierMax(l.start, l.end, includeDiagonals)
	for n := 0; n < max; n++ {
		point := point{x: l.start.x + xIncrement*n, y: l.start.y + yIncrement*n}
		ventsMap[point] = ventsMap[point] + 1
	}
}

func noOfPointsWithMoreThanOneVent(ventsMap map[point]int) int {
	var result = 0
	for key := range ventsMap {
		if ventsMap[key] > 1 {
			result++
		}
	}
	return result
}

func getIncrement(start int, end int) int {
	if (end - start) > 0 {
		return 1
	} else if (end - start) < 0 {
		return -1
	} else {
		return 0
	}
}

func multiplierMax(start point, end point, includeDiagonals bool) int {
	if !includeDiagonals && start.x != end.x && start.y != end.y {
		return -1
	} else if start.x != end.x {
		return diffPlusOne(start.x, end.x)
	} else {
		return diffPlusOne(start.y, end.y)
	}
}

func diffPlusOne(a int, b int) int {
	if a < b {
		return b - a + 1
	} else {
		return a - b + 1
	}

}
