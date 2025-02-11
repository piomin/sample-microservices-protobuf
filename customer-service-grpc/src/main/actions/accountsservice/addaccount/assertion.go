package addaccount

import (
	"github.com/stretchr/testify/require"
	"pl.piomin.services.grpc.customer.model"
	"github.com/uber/ctf"
)

// AssertSuccessfulResponse asserts the response is not nil and no error occurred
func AssertSuccessfulResponse() ResponseAssertion {
	return func(t *ctf.T, entities Entities, response *model.Account, err error) {
		require.NoError(t, err, "there should be no error while adding account")
		require.NotNil(t, response, "response should not be nil while adding account")
	}
}

// AssertAccountNumber asserts the account number matches expected value
func AssertAccountNumber(expected string) ResponseAssertion {
	return func(t *ctf.T, entities Entities, response *model.Account, err error) {
		require.Equal(t, expected, response.Number, "account number should match expected value")
	}
}

// AssertCustomerID asserts the customer ID matches expected value
func AssertCustomerID(expected int32) ResponseAssertion {
	return func(t *ctf.T, entities Entities, response *model.Account, err error) {
		require.Equal(t, expected, response.CustomerId, "customer ID should match expected value")
	}
}

// AssertAccountID asserts the account ID matches expected value
func AssertAccountID(expected int32) ResponseAssertion {
	return func(t *ctf.T, entities Entities, response *model.Account, err error) {
		require.Equal(t, expected, response.Id, "account ID should match expected value")
	}
}
