package findall

import (
	"testing"
	"github.com/stretchr/testify/require"
	pb "pl.piomin.services.grpc.customer.model"
)

// AssertSuccessfulResponse asserts the basic success conditions
func AssertSuccessfulResponse() ResponseAssertion {
	return func(t *testing.T, entities Entities, response *pb.Customers, err error) {
		require.NoError(t, err, "there should be no error while finding all customers")
		require.NotNil(t, response, "response should not be nil while finding all customers")
		require.NotNil(t, response.Customers, "customers list should not be nil")
	}
}

// AssertCustomersCount asserts the total number of customers returned
func AssertCustomersCount(expectedCount int) ResponseAssertion {
	return func(t *testing.T, entities Entities, response *pb.Customers, err error) {
		require.Len(t, response.Customers, expectedCount, "number of customers should match expected count")
	}
}

// AssertCustomerByPesel asserts that a customer with given PESEL exists and has expected values
func AssertCustomerByPesel(pesel string, expectedName string, expectedType string) ResponseAssertion {
	return func(t *testing.T, entities Entities, response *pb.Customers, err error) {
		var found bool
		var customer *pb.Customer
		for _, c := range response.Customers {
			if c.Pesel == pesel {
				found = true
				customer = c
				break
			}
		}
		require.True(t, found, "customer with PESEL %s should exist", pesel)
		require.Equal(t, expectedName, customer.Name, "customer name should match expected value")

		var expectedCustomerType pb.Customer_CustomerType
		switch expectedType {
		case "INDIVIDUAL":
			expectedCustomerType = pb.Customer_INDIVIDUAL
		case "COMPANY":
			expectedCustomerType = pb.Customer_COMPANY
		default:
			t.Fatalf("invalid customer type: %s", expectedType)
		}
		require.Equal(t, expectedCustomerType, customer.Type, "customer type should match expected value")
	}
}

// AssertCustomerByID asserts that a customer with given ID exists and has expected values
func AssertCustomerByID(id int32, expectedPesel string, expectedName string, expectedType string) ResponseAssertion {
	return func(t *testing.T, entities Entities, response *pb.Customers, err error) {
		var found bool
		var customer *pb.Customer
		for _, c := range response.Customers {
			if c.Id == id {
				found = true
				customer = c
				break
			}
		}
		require.True(t, found, "customer with ID %d should exist", id)
		require.Equal(t, expectedPesel, customer.Pesel, "customer PESEL should match expected value")
		require.Equal(t, expectedName, customer.Name, "customer name should match expected value")

		var expectedCustomerType pb.Customer_CustomerType
		switch expectedType {
		case "INDIVIDUAL":
			expectedCustomerType = pb.Customer_INDIVIDUAL
		case "COMPANY":
			expectedCustomerType = pb.Customer_COMPANY
		default:
			t.Fatalf("invalid customer type: %s", expectedType)
		}
		require.Equal(t, expectedCustomerType, customer.Type, "customer type should match expected value")
	}
}

// AssertCustomerAccounts asserts that a customer has the expected accounts
func AssertCustomerAccounts(customerID int32, expectedAccountNumbers []string) ResponseAssertion {
	return func(t *testing.T, entities Entities, response *pb.Customers, err error) {
		var customer *pb.Customer
		for _, c := range response.Customers {
			if c.Id == customerID {
				customer = c
				break
			}
		}
		require.NotNil(t, customer, "customer with ID %d should exist", customerID)
		require.Len(t, customer.Accounts, len(expectedAccountNumbers), "number of accounts should match expected count")

		accountNumbers := make(map[string]bool)
		for _, account := range customer.Accounts {
			accountNumbers[account.Number] = true
		}

		for _, expectedNumber := range expectedAccountNumbers {
			require.True(t, accountNumbers[expectedNumber], "account with number %s should exist", expectedNumber)
		}
	}
}
