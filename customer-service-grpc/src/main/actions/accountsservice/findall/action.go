package findall

import (
	"github.com/golang/protobuf/ptypes/empty"
	"github.com/stretchr/testify/require"
	"pl.piomin.services.grpc.customer.model"
	accountsservice "../"
	"github.com/uber/ctf"
)

// actionName for this action
const (
	actionName = "test://accountsservice/actions/findall"
)

// ResponseAssertion is an assertion function to assert response
type ResponseAssertion func(*ctf.T, Entities, *model.Accounts, error)

// Entities are the entity inputs to the action
type Entities struct {
	AccountsService accountsservice.Interface
}

// Params is input required to make calls to accounts-service::FindAll
type Params struct {
	Entities    Entities
	Assertions  struct {
		Response []ResponseAssertion
	}
}

// New triggers an action to call accounts-service::FindAll
func New(p *Params) *ctf.Action {
	return ctf.NewActionV2(actionName,
		func(t *ctf.T) {
			request := &empty.Empty{}

			res, err := p.Entities.AccountsService.FindAll(t.Context(), request)
			
			for _, assertion := range p.Assertions.Response {
				assertion(t, p.Entities, res, err)
			}
		},
	)
}
