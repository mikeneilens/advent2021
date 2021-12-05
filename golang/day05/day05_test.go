package day05

import (
	"testing"
)

var sampleData = `0,9 -> 5,9
8,0 -> 0,8
9,4 -> 3,4
2,2 -> 2,1
7,0 -> 7,4
6,4 -> 2,0
0,9 -> 2,9
3,4 -> 1,4
0,0 -> 8,8
5,5 -> 8,2`

func Test_parsing_SampleData(t *testing.T) {

	result := parseInput(sampleData)
	if !(result[0].start.x == 0 && result[0].start.y == 9 && result[0].end.x == 5 && result[0].end.y == 9) {
		t.Fatalf(`parseInput(sampleData) expected (0,9) (5,9) but returned %v`, result)
	}
}

func Test_partOne_WithSampleData(t *testing.T) {
	result := partOne(sampleData)
	if result != 5 {
		t.Fatalf(`part one with sample data should return 5 but returned %v`, result)
	}
}
func Test_partOne_WithPuzzleInput(t *testing.T) {
	result := partOne(puzzleInput)
	if result != 5608 {
		t.Fatalf(`part one with sample data should return 5 but returned %v`, result)
	}
}

func Test_partTwo_WithSampleData(t *testing.T) {
	result := partTwo(sampleData)
	if result != 12 {
		t.Fatalf(`part one with sample data should return 5 but returned %v`, result)
	}
}

func Test_partTwo_WithPuzzleInput(t *testing.T) {
	result := partTwo(puzzleInput)
	if result != 20299 {
		t.Fatalf(`part one with sample data should return 5 but returned %v`, result)
	}
}
