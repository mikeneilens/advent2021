package main

import (
	"strconv"
	"strings"
)

func Part01(data string) int {
	values := ParseData(data)
	return countIncreases(values, 1)
}

func Part02(data string) int {
	values := ParseData(data)
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

func ParseData(data string) []int {
	values := strings.Split(data, "\n")
	var result []int
	for _, value := range values {
		intValue, _ := strconv.Atoi(value)
		result = append(result, intValue)
	}
	return result
}
