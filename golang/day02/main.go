package main

import (
	"advent/parser"
	"strconv"
)

func createListOfCommands(data string) []func(int, int, int) (int, int, int) {
	listOfWords := parser.ParseIntoListOfWords(data)
	var result = make([]func(int, int, int) (int, int, int), len(listOfWords))
	for ndx, record := range listOfWords {
		command := record[0]
		qty, _ := strconv.Atoi(record[1])
		switch command {
		case "forward":
			result[ndx] = func(x int, y int, aim int) (int, int, int) { return x + qty, y, aim }
		case "up":
			result[ndx] = func(x int, y int, aim int) (int, int, int) { return x, y - qty, aim }
		case "down":
			result[ndx] = func(x int, y int, aim int) (int, int, int) { return x, y + qty, aim }
		}
	}
	return result
}

func day02(data string, listOfCommandsCreator func(string) []func(int, int, int) (int, int, int)) int {
	var x, y, aim int

	instructions := listOfCommandsCreator(data)

	for _, instruction := range instructions {
		x, y, aim = instruction(x, y, aim)
	}
	return x * y
}

func CreateListOfCommandsP2(data string) []func(int, int, int) (int, int, int) {
	listOfWords := parser.ParseIntoListOfWords(data)
	var result = make([]func(int, int, int) (int, int, int), len(listOfWords))
	for ndx, record := range listOfWords {
		command := record[0]
		qty, _ := strconv.Atoi(record[1])
		switch command {
		case "forward":
			result[ndx] = func(x int, y int, aim int) (int, int, int) { return x + qty, y + aim*qty, aim }
		case "up":
			result[ndx] = func(x int, y int, aim int) (int, int, int) { return x, y, aim - qty }
		case "down":
			result[ndx] = func(x int, y int, aim int) (int, int, int) { return x, y, aim + qty }
		}
	}
	return result
}
