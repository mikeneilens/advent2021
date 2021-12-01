package main

import (
	"testing"
)

var sampleData = `199
200
208
210
200
207
240
269
260
263`

func Test_Part01_using_sample_data(t *testing.T) {
	result := Part01(sampleData)
	if result != 7 {
		t.Fatalf(`Part01(sampleData) expected 7, but returned %v`, result)
	}
}

func Test_Part01_using_Puzzle_Input(t *testing.T) {
	result := Part01(PuzzleInput)
	if result != 1676 {
		t.Fatalf(`Part01(PuzzleInput) expected 1676, but returned %v`, result)
	}
}

func Test_Part02_using_sample_data(t *testing.T) {
	result := Part02(sampleData)
	if result != 5 {
		t.Fatalf(`Part02(sampleData) expected 5, but returned %v`, result)
	}
}

func Test_Part02_using_Puzzle_Input(t *testing.T) {
	result := Part02(PuzzleInput)
	if result != 1706 {
		t.Fatalf(`Part02(PuzzleInput) expected 1706, but returned %v`, result)
	}
}
