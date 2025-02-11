package findbycustomer

import (
	"github.com/stretchr/testify/require"
	"pl.piomin.services.grpc.customer.model"
	"github.com/uber/ctf"
)

// AssertSuccessfulResponse asserts the response is not nil and no error occurred
func AssertSuccessfulResponse() ResponseAssertion {
	return func(t *ctf.T, entities Entities, response *model.Accounts, err error) {
		require.NoError(t, err, "there should be no error while finding accounts by customer")
		require.NotNil(t, response, "response should not be nil while finding accounts by customer")
	}
}

// AssertAccountsCount asserts the number of accounts in the response
func AssertAccountsCount(expected int) ResponseAssertion {
	return func(t *ctf.T, entities Entities, response *model.Accounts, err error) {
		require.Len(t, response.Account, expected, "number of accounts should match expected count")
	}
}

// AssertAccountByNumber asserts an account with the given number exists
func AssertAccountByNumber(number string) ResponseAssertion {
	return func(t *ctf.T, entities Entities, response *model.Accounts, err error) {
		found := false
		for _, account := range response.Account {
			if account.Number == number {
				found = true
				break
			}
		}
		require.True(t, found, "account with number %s should exist", number)
	}
}
