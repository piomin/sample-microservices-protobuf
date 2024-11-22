package findbyid

import (
	"github.com/golang/protobuf/ptypes/wrappers"
	"testing"
)

// IDRequestModifier sets the customer ID in the request
func IDRequestModifier(id int32) RequestModifier {
	return func(t *testing.T, entities Entities, request *wrappers.Int32Value) {
		if id <= 0 {
			t.Fatal("customer ID must be positive")
		}
		request.Value = id
	}
}

// DefaultRequestModifier sets default values for the request
func DefaultRequestModifier() RequestModifier {
	return func(t *testing.T, entities Entities, request *wrappers.Int32Value) {
		// Empty Int32Value is a valid default state
	}
}
