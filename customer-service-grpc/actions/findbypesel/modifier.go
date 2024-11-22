package findbypesel

import (
	"github.com/golang/protobuf/ptypes/wrappers"
	"testing"
)

// PeselRequestModifier sets the PESEL value in the request
func PeselRequestModifier(pesel string) RequestModifier {
	return func(t *testing.T, entities Entities, request *wrappers.StringValue) {
		if pesel == "" {
			t.Fatal("PESEL cannot be empty")
		}
		request.Value = pesel
	}
}

// DefaultRequestModifier sets default values for the request
func DefaultRequestModifier() RequestModifier {
	return func(t *testing.T, entities Entities, request *wrappers.StringValue) {
		// Empty StringValue is a valid default state
	}
}
