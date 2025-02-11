package findbynumber

import (
	"github.com/golang/protobuf/ptypes/wrappers"
	"github.com/uber/ctf"
)

// WithAccountNumber sets the account number in the request
func WithAccountNumber(number string) RequestModifier {
	return func(t *ctf.T, entities Entities, request *wrappers.StringValue) {
		request.Value = number
	}
}
