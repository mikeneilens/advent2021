package parser

// This contains the parsers needed by advent of code to convert Puzzle Input text into something that functions can use.

import (
	"strconv"
	"strings"
)

func ParseIntoListOfInts(data string) []int {
	values := strings.Split(data, "\n")
	var result = make([]int, len(values))
	for ndx, value := range values {
		intValue, _ := strconv.Atoi(value)
		result[ndx] = intValue
		result[ndx] = intValue
	}
	return result
}
