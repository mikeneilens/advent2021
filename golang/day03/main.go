package main

import (
	"advent/parser"
)

func mostCommonBitInColumn(listOfDigits [][]int, col int) int {
	var noOfZeros = 0
	for _, digits := range listOfDigits {
		if digits[col] == 0 {
			noOfZeros++
		}
	}
	if noOfZeros > len(listOfDigits)/2 {
		return 0
	} else {
		return 1
	}
}

func calcGamma(data string) []int {
	listOfDigits := parser.ParseIntoListOfDigits(data)
	noOfDigits := len(listOfDigits[0])
	result := make([]int, len(listOfDigits[0]))
	for i := 0; i < noOfDigits; i++ {
		result[i] += mostCommonBitInColumn(listOfDigits, i)
	}
	return result
}

func partOne(data string) int {
	gamma := calcGamma(data)
	epsilon := invert(gamma)
	return binToInt(gamma) * binToInt(epsilon)
}

func binToInt(digits []int) int {
	var result = 0
	for _, digit := range digits {
		result = result*2 + digit
	}
	return result
}

func invert(digits []int) []int {
	result := make([]int, len(digits))
	for ndx, digit := range digits {
		if digit == 0 {
			result[ndx] += 1
		} else {
			result[ndx] += 0
		}
	}
	return result
}

func oxyGenRating(listOfDigits [][]int) []int {
	return calcRating(listOfDigits, 1, 0)
}

func scrubberRating(listOfDigits [][]int) []int {
	return calcRating(listOfDigits, 0, 0)
}

func calcRating(listOfDigits [][]int, ratingBit int, ndx int) []int {
	if len(listOfDigits) == 1 {
		return listOfDigits[0]
	} else {
		var filteredList = [][]int{}
		if mostCommonBitInColumn(listOfDigits, ndx) == ratingBit {
			filteredList = filterListIntoRowsWithTheCorrectBitInColumn(listOfDigits, ndx, 1)
		} else {
			filteredList = filterListIntoRowsWithTheCorrectBitInColumn(listOfDigits, ndx, 0)
		}
		return calcRating(filteredList, ratingBit, ndx+1)
	}
}

func filterListIntoRowsWithTheCorrectBitInColumn(listOfDigits [][]int, col int, bit int) [][]int {
	var result = [][]int{}
	for _, digits := range listOfDigits {
		if digits[col] == bit {
			result = append(result, digits)
		}
	}
	return result
}

func partTwo(data string) int {
	listOfDigits := parser.ParseIntoListOfDigits(data)
	return binToInt(oxyGenRating(listOfDigits)) * binToInt(scrubberRating(listOfDigits))
}
