package findall

import (
	"context"
	"github.com/golang/protobuf/ptypes/empty"
	"github.com/stretchr/testify/require"
	"testing"
	gateway "../gateway"
	pb "pl.piomin.services.grpc.customer.model"
)

// actionName for this action
const (
	actionName = "test://customer-service-grpc/actions/findall"
)

// RequestModifier is a modifier function which modifies the request before calling the action
type RequestModifier func(*testing.T, Entities, *empty.Empty)

// ResponseAssertion is an assertion function to assert response
type ResponseAssertion func(*testing.T, Entities, *pb.Customers, error)

// Entities are the entity inputs to the action
type Entities struct {
	CustomerGateway gateway.Interface
}

// Params is input required to make calls to customer-service::findall
type Params struct {
	Entities         Entities
	RequestModifiers []RequestModifier
	Assertions      struct {
		Response []ResponseAssertion
	}
}

// New triggers an action to call customer-service::FindAll
func New(p *Params) func(*testing.T) {
	return func(t *testing.T) {
		request := &empty.Empty{}

		for _, modifier := range p.RequestModifiers {
			modifier(t, p.Entities, request)
		}

		res, err := p.Entities.CustomerGateway.FindAll(context.Background(), request)

		for _, assertion := range p.Assertions.Response {
			assertion(t, p.Entities, res, err)
		}
	}
}
