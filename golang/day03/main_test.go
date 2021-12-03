package main

import (
	"advent/parser"
	"reflect"
	"testing"
)

var sampleData = `00100
11110
10110
10111
10101
01111
00111
11100
10000
11001
00010
01010`

func Test_BinToInt(t *testing.T) {
	var result = binToInt([]int{0, 1, 0, 0, 1})
	if result != 9 {
		t.Fatalf(`"binToInt(01001") expected 9, but returned %v `, result)
	}
}
func Test_Invert(t *testing.T) {
	var result = invert([]int{0, 1, 0, 0, 1})
	if !reflect.DeepEqual(result, []int{1, 0, 1, 1, 0}) {
		t.Fatalf(`invert("01001") expected 10110, but returned %v `, result)
	}
}

func Test_mostCommonBitInColumn(t *testing.T) {
	var listOfDigits = parser.ParseIntoListOfDigits(sampleData)
	var result_col0 = mostCommonBitInColumn(listOfDigits, 0)
	if result_col0 != 1 {
		t.Fatalf(`"mostCommonBitInColumn(sampleData, 0) expected 1, but returned %v `, result_col0)
	}
	var result_col1 = mostCommonBitInColumn(listOfDigits, 1)
	if result_col1 != 0 {
		t.Fatalf(`"mostCommonBitInColumn(sampleData, 1) expected 0, but returned %v `, result_col0)
	}
}

func Test_calcGamma_using_sample_data(t *testing.T) {
	var result = calcGamma(sampleData)
	if !reflect.DeepEqual(result, []int{1, 0, 1, 1, 0}) {
		t.Fatalf(`"calcGamma(sampleData) expected 10110, but returned %v `, result)
	}
}

func Test_partOne_using_sample_data(t *testing.T) {
	var result = partOne(sampleData)
	if result != 198 {
		t.Fatalf(`"partOne(sampleData) expected 198, but returned %v `, result)
	}
}

func Test_partOne_using_puzzleInput(t *testing.T) {
	var result = partOne(puzzleInput)
	if result != 2583164 {
		t.Fatalf(`"partOne(sampleData) expected 2583164, but returned %v `, result)
	}
}

func Test_filterListIntoRowsWithTheCorrectBitInColumn(t *testing.T) {
	data := [][]int{{1, 0, 0, 1}, {0, 1, 1, 0}, {0, 1, 0, 1}}
	var result = filterListIntoRowsWithTheCorrectBitInColumn(data, 3, 1)
	if len(result) != 2 {
		t.Fatalf(`"filterListIntoRowsWithTheCorrectBitInColumn(data, col=3, bit =1) expected slice length of 2, but returned %v `, len(result))
	}
	if !reflect.DeepEqual(result[0], []int{1, 0, 0, 1}) {
		t.Fatalf(`"filterListIntoRowsWithTheCorrectBitInColumn(data, col=3, bit =1) expected row 0 to be 1001, but returned %v `, result[0])
	}
	if !reflect.DeepEqual(result[1], []int{0, 1, 0, 1}) {
		t.Fatalf(`"filterListIntoRowsWithTheCorrectBitInColumn(data, col=3, bit =1) expected row 1 to be 0101, but returned %v `, result[1])
	}

}

func Test_oxyGenRating_usingSampleData(t *testing.T) {
	listOfDigits := parser.ParseIntoListOfDigits(sampleData)
	result := oxyGenRating(listOfDigits)
	if !reflect.DeepEqual(result, []int{1, 0, 1, 1, 1}) {
		t.Fatalf(`"oxyGenRating(sampleData) expected 10111, but returned %v `, result)
	}
}
func Test_scrubberRating_usingSampleData(t *testing.T) {
	listOfDigits := parser.ParseIntoListOfDigits(sampleData)
	result := scrubberRating(listOfDigits)
	if !reflect.DeepEqual(result, []int{0, 1, 0, 1, 0}) {
		t.Fatalf(`"scrubberRating(sampleData) expected 01010, but returned %v `, result)
	}
}
func Test_partTwo_usingSampleData(t *testing.T) {
	result := partTwo(sampleData)
	if result != 230 {
		t.Fatalf(`"PartTwo(sampleData) expected 230, but returned %v `, result)
	}
}

func Test_partTwo_usingPuzzleInput(t *testing.T) {
	result := partTwo(puzzleInput)
	if result != 2784375 {
		t.Fatalf(`"PartTwo(puzzleInput) expected 2784375, but returned %v `, result)
	}
}
