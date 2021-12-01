package parser

import (
	"reflect"
	"testing"
)

func Test_parseData(t *testing.T) {
	data := `1
2
3`
	expectedResult := []int{1, 2, 3}
	result := ParseIntoListOfInts(data)
	if !reflect.DeepEqual(result, expectedResult) {
		t.Fatalf(`parseData([1,2,3]) expected %v, but returned %v`, expectedResult, result)
	}
}
