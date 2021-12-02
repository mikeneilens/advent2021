package main

import (
	"advent/parser"
	"strconv"
)

type submarine struct {
	x   int
	y   int
	aim int
}

func createListOfCommands(data string) []func(*submarine) {
	listOfWords := parser.ParseIntoListOfWords(data)
	var result = make([]func(*submarine), len(listOfWords))

	for ndx, record := range listOfWords {
		command := record[0]
		qty, _ := strconv.Atoi(record[1])
		switch command {
		case "forward":
			result[ndx] = func(sub *submarine) { sub.x += qty }
		case "up":
			result[ndx] = func(sub *submarine) { sub.y -= qty }
		case "down":
			result[ndx] = func(sub *submarine) { sub.y += qty }
		}
	}
	return result
}

func CreateListOfCommandsP2(data string) []func(*submarine) {
	listOfWords := parser.ParseIntoListOfWords(data)
	var result = make([]func(*submarine), len(listOfWords))
	for ndx, record := range listOfWords {
		command := record[0]
		qty, _ := strconv.Atoi(record[1])
		switch command {
		case "forward":
			result[ndx] = func(sub *submarine) { sub.x += qty; sub.y += sub.aim * qty }
		case "up":
			result[ndx] = func(sub *submarine) { sub.aim -= qty }
		case "down":
			result[ndx] = func(sub *submarine) { sub.aim += qty }
		}
	}
	return result
}

func day02(data string, listOfCommandsCreator func(string) []func(*submarine)) int {
	var sub = submarine{0, 0, 0}

	commands := listOfCommandsCreator(data)

	for _, command := range commands {
		command(&sub)
	}
	return sub.x * sub.y
}
