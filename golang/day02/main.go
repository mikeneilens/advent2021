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

func createListOfCommands(data string, functionCreator func(commandText string, qty int) func(*submarine)) []func(*submarine) {
	listOfWords := parser.ParseIntoListOfWords(data)
	var result = make([]func(*submarine), len(listOfWords))

	for ndx, words := range listOfWords {
		command := words[0]
		qty, _ := strconv.Atoi(words[1])
		result[ndx] = functionCreator(command, qty)
	}
	return result
}

func part1CommandCreator(command string, qty int) func(*submarine) {
	switch command {
	case "forward":
		return func(sub *submarine) { sub.x += qty }
	case "up":
		return func(sub *submarine) { sub.y -= qty }
	case "down":
		return func(sub *submarine) { sub.y += qty }
	default:
		return func(sub *submarine) {}
	}
}

func part2CommandCreator(command string, qty int) func(*submarine) {
	switch command {
	case "forward":
		return func(sub *submarine) { sub.x += qty; sub.y += sub.aim * qty }
	case "up":
		return func(sub *submarine) { sub.aim -= qty }
	case "down":
		return func(sub *submarine) { sub.aim += qty }
	default:
		return func(sub *submarine) {}
	}
}

func day02(data string, functionCreator func(commandText string, qty int) func(*submarine)) int {
	var sub = submarine{0, 0, 0}

	commands := createListOfCommands(data, functionCreator)

	for _, command := range commands {
		command(&sub)
	}
	return sub.x * sub.y
}
