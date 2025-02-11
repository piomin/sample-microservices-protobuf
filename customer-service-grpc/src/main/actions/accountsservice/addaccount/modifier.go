package addaccount

import (
	"pl.piomin.services.grpc.customer.model"
	"github.com/uber/ctf"
)

// WithAccountNumber sets the account number in the request
func WithAccountNumber(number string) RequestModifier {
	return func(t *ctf.T, entities Entities, request *model.Account) {
		request.Number = number
	}
}

// CustomerIDRequestModifier sets the customer ID from entities in the request
func CustomerIDRequestModifier() RequestModifier {
	return func(t *ctf.T, entities Entities, request *model.Account) {
		request.CustomerId = entities.Customer.ID()
	}
}
