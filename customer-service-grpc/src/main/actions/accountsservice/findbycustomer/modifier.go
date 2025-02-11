package findbycustomer

import (
	"github.com/golang/protobuf/ptypes/wrappers"
	"github.com/uber/ctf"
)

// CustomerIDRequestModifier sets the customer ID from entities in the request
func CustomerIDRequestModifier() RequestModifier {
	return func(t *ctf.T, entities Entities, request *wrappers.Int32Value) {
		request.Value = entities.Customer.ID()
	}
}
