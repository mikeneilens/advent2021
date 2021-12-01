package main

import (
	"advent/parser"
)

func Part01(data string) int {
	values := parser.ParseIntoListOfInts(data)
	return countIncreases(values, 1)
}

func Part02(data string) int {
	values := parser.ParseIntoListOfInts(data)
	return countIncreases(values, 3)
}

func countIncreases(values []int, offset int) int {
	noOfIncreases := 0
	for ndx, value := range values {
		if ndx >= offset {
			if value > values[ndx-offset] {
				noOfIncreases++
			}
		}
	}
	return noOfIncreases
}
