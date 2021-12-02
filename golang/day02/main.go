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

func createListOfCommands(data string, functionCreator func(commandText string, commands []func(*submarine), ndx int, qty int)) []func(*submarine) {
	listOfWords := parser.ParseIntoListOfWords(data)
	var result = make([]func(*submarine), len(listOfWords))

	for ndx, record := range listOfWords {
		command := record[0]
		qty, _ := strconv.Atoi(record[1])
		functionCreator(command, result, ndx, qty)
	}
	return result
}

func part1FunctionCreator(command string, listOfCommands []func(*submarine), ndx int, qty int) {
	switch command {
	case "forward":
		listOfCommands[ndx] = func(sub *submarine) { sub.x += qty }
	case "up":
		listOfCommands[ndx] = func(sub *submarine) { sub.y -= qty }
	case "down":
		listOfCommands[ndx] = func(sub *submarine) { sub.y += qty }
	}
}

func part2FunctionCreator(command string, listOfCommands []func(*submarine), ndx int, qty int) {
	switch command {
	case "forward":
		listOfCommands[ndx] = func(sub *submarine) { sub.x += qty; sub.y += sub.aim * qty }
	case "up":
		listOfCommands[ndx] = func(sub *submarine) { sub.aim -= qty }
	case "down":
		listOfCommands[ndx] = func(sub *submarine) { sub.aim += qty }
	}
}

func day02(data string, functionCreator func(commandText string, commands []func(*submarine), ndx int, qty int)) int {
	var sub = submarine{0, 0, 0}

	commands := createListOfCommands(data, functionCreator)

	for _, command := range commands {
		command(&sub)
	}
	return sub.x * sub.y
}
