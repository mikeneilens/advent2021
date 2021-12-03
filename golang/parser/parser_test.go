package parser

import (
	"reflect"
	"testing"
)

func Test_parseIntoListOfInts(t *testing.T) {
	data := `1
2
3`
	expectedResult := []int{1, 2, 3}
	result := ParseIntoListOfInts(data)
	if !reflect.DeepEqual(result, expectedResult) {
		t.Fatalf(`parseIntoListOfInts([1,2,3]) expected %v, but returned %v`, expectedResult, result)
	}
}

func Test_parseIntoListOfWords(t *testing.T) {
	data := `ab cd ef
gh ij
klm nop`
	expectedResult0 := []string{"ab", "cd", "ef"}
	expectedResult1 := []string{"gh", "ij"}
	expectedResult2 := []string{"klm", "nop"}
	expectedResult := [][]string{expectedResult0, expectedResult1, expectedResult2}
	result := ParseIntoListOfWords(data)
	if !reflect.DeepEqual(result, expectedResult) {
		t.Fatalf(`parseIntoListOfWords() expected %v, but returned %v`, expectedResult, result)
	}
}

func Test_parseIntoListOfStrings(t *testing.T) {
	data := `1
2
3`
	expectedResult := []string{"1", "2", "3"}
	result := ParseIntoListOfStrings(data)
	if !reflect.DeepEqual(result, expectedResult) {
		t.Fatalf(`parseIntoListOfStrings(["1","2","3"]) expected %v, but returned %v`, expectedResult, result)
	}
}

func Test_parseIntoListOfDigits(t *testing.T) {
	data := `123
456
789`
	result := ParseIntoListOfDigits(data)
	if len(result) != 3 {
		t.Fatalf(`parseIntoListOfDigits(["123","456","789"]) expected 3, but returned %v`, result)
	}
	if result[2][0] != 7 {
		t.Fatalf(`parseIntoListOfDigits(["123","456","789"]) expected 7 at position 2,0, but returned %v`, result[2][0])
	}
	if result[2][1] != 8 {
		t.Fatalf(`parseIntoListOfDigits(["123","456","789"]) expected 8 at position 2,1, but returned %v`, result[2][1])
	}
	if result[2][2] != 9 {
		t.Fatalf(`parseIntoListOfDigits(["123","456","789"]) expected 9 at position 2,2, but returned %v`, result[2][2])
	}
}
