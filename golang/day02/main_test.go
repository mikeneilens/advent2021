package main

import "testing"

func Test_createListOfCommands_forForward(t *testing.T) {
	var data = `forward 5`
	commands := createListOfCommands(data, part1CommandCreator)
	sub := submarine{1, 2, 3}
	commands[0](&sub)
	if sub.x != 6 || sub.y != 2 || sub.aim != 3 {
		t.Fatalf(`ParseData('forward 5') on (1,2,3) expected (6,2,3), but returned (%v,%v,%v) `, sub.x, sub.y, sub.aim)
	}
}

func Test_createListOfCommands_forDown(t *testing.T) {
	var data = `down 7`
	commands := createListOfCommands(data, part1CommandCreator)
	sub := submarine{1, 2, 3}
	commands[0](&sub)
	if sub.x != 1 || sub.y != 9 || sub.aim != 3 {
		t.Fatalf(`ParseData('down 7') on (1,2,3) expected (1,9,3), but returned (%v,%v,%v) `, sub.x, sub.y, sub.aim)
	}
}

func Test_createListOfCommands_forUp(t *testing.T) {
	var data = `up 7`
	commands := createListOfCommands(data, part1CommandCreator)
	sub := submarine{1, 2, 3}
	commands[0](&sub)
	if sub.x != 1 || sub.y != -5 || sub.aim != 3 {
		t.Fatalf(`ParseData('up 7') on (1,2,3) expected (1,-5,3), but returned (%v,%v,%v) `, sub.x, sub.y, sub.aim)
	}
}

func Test_part01_using_SampleData(t *testing.T) {
	var sampleData = `forward 5
down 5
forward 8
up 3
down 8
forward 2`
	result := day02(sampleData, part1CommandCreator)
	if result != 150 {
		t.Fatalf(`PartOne(SampleData) expected 150, but returned %v `, result)
	}
}

func Test_part01_using_PuzzleInput(t *testing.T) {
	result := day02(PuzzleInput, part1CommandCreator)
	if result != 1383564 {
		t.Fatalf(`PartOne(PuzzleInput) expected 1383564, but returned %v `, result)
	}
}

func Test_part02_using_PuzzleInput(t *testing.T) {
	result := day02(PuzzleInput, part2CommandCreator)
	if result != 1488311643 {
		t.Fatalf(`PartTwo(PuzzleInput) expected 1488311643, but returned %v `, result)
	}
}
