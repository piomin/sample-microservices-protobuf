package addcustomer

import (
	"context"
	"github.com/stretchr/testify/require"
	"testing"
	gateway "../gateway"
	pb "pl.piomin.services.grpc.customer.model"
)

// actionName for this action
const (
	actionName = "test://customer-service-grpc/actions/addcustomer"
)

// RequestModifier is a modifier function which modifies the request before calling the action
type RequestModifier func(*testing.T, Entities, *pb.Customer)

// ResponseAssertion is an assertion function to assert response
type ResponseAssertion func(*testing.T, Entities, *pb.Customer, error)

// Entities are the entity inputs to the action
type Entities struct {
	CustomerGateway gateway.Interface
}

// Params is input required to make calls to customer-service::addcustomer
type Params struct {
	Entities         Entities
	RequestModifiers []RequestModifier
	Assertions      struct {
		Response []ResponseAssertion
	}
}

// New triggers an action to call customer-service::AddCustomer
func New(p *Params) func(*testing.T) {
	return func(t *testing.T) {
		request := &pb.Customer{}

		for _, modifier := range p.RequestModifiers {
			modifier(t, p.Entities, request)
		}

		res, err := p.Entities.CustomerGateway.AddCustomer(context.Background(), request)

		for _, assertion := range p.Assertions.Response {
			assertion(t, p.Entities, res, err)
		}
	}
}
