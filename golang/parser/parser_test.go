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
