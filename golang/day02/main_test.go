package main

import "testing"

func Test_createListOfCommands_forForward(t *testing.T) {
	var data = `forward 5`
	parsedData := createListOfCommands(data)
	x, y, aim := parsedData[0](1, 2, 3)
	if x != 6 || y != 2 || aim != 3 {
		t.Fatalf(`ParseData('forward 5') on (1,2,3) expected (6,2,3), but returned (%v,%v,%v) `, x, y, aim)
	}
}

func Test_createListOfCommands_forDown(t *testing.T) {
	var data = `down 7`
	parsedData := createListOfCommands(data)
	x, y, aim := parsedData[0](1, 2, 3)
	if x != 1 || y != 9 || aim != 3 {
		t.Fatalf(`ParseData('down 7') on (1,2,3) expected (1,9,3), but returned (%v,%v,%v) `, x, y, aim)
	}
}

func Test_createListOfCommands_forUp(t *testing.T) {
	var data = `up 7`
	parsedData := createListOfCommands(data)
	x, y, aim := parsedData[0](1, 2, 3)
	if x != 1 || y != -5 || aim != 3 {
		t.Fatalf(`ParseData('up 7') on (1,2,3) expected (1,-5,3), but returned (%v,%v,%v) `, x, y, aim)
	}
}

func Test_part01_using_SampleData(t *testing.T) {
	var sampleData = `forward 5
down 5
forward 8
up 3
down 8
forward 2`
	result := day02(sampleData, createListOfCommands)
	if result != 150 {
		t.Fatalf(`PartOne(SampleData) expected 150, but returned %v `, result)
	}
}

func Test_part01_using_PuzzleInput(t *testing.T) {
	result := day02(PuzzleInput, createListOfCommands)
	if result != 1383564 {
		t.Fatalf(`PartOne(PuzzleInput) expected 1383564, but returned %v `, result)
	}
}

func Test_part02_using_PuzzleInput(t *testing.T) {
	result := day02(PuzzleInput, CreateListOfCommandsP2)
	if result != 1488311643 {
		t.Fatalf(`PartTwo(PuzzleInput) expected 1488311643, but returned %v `, result)
	}
}