package addaccount

import (
	"github.com/stretchr/testify/require"
	"pl.piomin.services.grpc.customer.model"
	accountsservice "../"
	"github.com/uber/ctf"
)

// actionName for this action
const (
	actionName = "test://accountsservice/actions/addaccount"
)

// RequestModifier is a modifier function which adds to the request before calling the action
type RequestModifier func(*ctf.T, Entities, *model.Account)

// ResponseAssertion is an assertion function to assert response
type ResponseAssertion func(*ctf.T, Entities, *model.Account, error)

// Entities are the entity inputs to the action
type Entities struct {
	AccountsService accountsservice.Interface
	Customer       interface {
		ID() int32
	}
}

// Params is input required to make calls to accounts-service::AddAccount
type Params struct {
	Entities         Entities
	RequestModifiers []RequestModifier
	Assertions      struct {
		Response []ResponseAssertion
	}
}

// New triggers an action to call accounts-service::AddAccount
func New(p *Params) *ctf.Action {
	return ctf.NewActionV2(actionName,
		func(t *ctf.T) {
			request := &model.Account{}

			for _, modifier := range p.RequestModifiers {
				modifier(t, p.Entities, request)
			}

			res, err := p.Entities.AccountsService.AddAccount(t.Context(), request)
			
			for _, assertion := range p.Assertions.Response {
				assertion(t, p.Entities, res, err)
			}
		},
	)
}
