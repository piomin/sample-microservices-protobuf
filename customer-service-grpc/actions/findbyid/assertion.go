package findbyid

import (
	"testing"
	"github.com/stretchr/testify/require"
	pb "pl.piomin.services.grpc.customer.model"
)

// AssertSuccessfulResponse asserts the basic success conditions
func AssertSuccessfulResponse() ResponseAssertion {
	return func(t *testing.T, entities Entities, response *pb.Customer, err error) {
		require.NoError(t, err, "there should be no error while finding customer by ID")
		require.NotNil(t, response, "response should not be nil while finding customer by ID")
	}
}

// AssertPesel asserts that the customer PESEL matches expected value
func AssertPesel(expectedPesel string) ResponseAssertion {
	return func(t *testing.T, entities Entities, response *pb.Customer, err error) {
		require.Equal(t, expectedPesel, response.Pesel, "customer PESEL should match expected value")
	}
}

// AssertName asserts that the customer name matches expected value
func AssertName(expectedName string) ResponseAssertion {
	return func(t *testing.T, entities Entities, response *pb.Customer, err error) {
		require.Equal(t, expectedName, response.Name, "customer name should match expected value")
	}
}

// AssertType asserts that the customer type matches expected value
func AssertType(expectedType string) ResponseAssertion {
	return func(t *testing.T, entities Entities, response *pb.Customer, err error) {
		var expectedCustomerType pb.Customer_CustomerType
		switch expectedType {
		case "INDIVIDUAL":
			expectedCustomerType = pb.Customer_INDIVIDUAL
		case "COMPANY":
			expectedCustomerType = pb.Customer_COMPANY
		default:
			t.Fatalf("invalid customer type: %s", expectedType)
		}
		require.Equal(t, expectedCustomerType, response.Type, "customer type should match expected value")
	}
}

// AssertID asserts that the customer ID matches expected value
func AssertID(expectedID int32) ResponseAssertion {
	return func(t *testing.T, entities Entities, response *pb.Customer, err error) {
		require.Equal(t, expectedID, response.Id, "customer ID should match expected value")
	}
}

// AssertAccountsCount asserts the number of accounts associated with the customer
func AssertAccountsCount(expectedCount int) ResponseAssertion {
	return func(t *testing.T, entities Entities, response *pb.Customer, err error) {
		require.Len(t, response.Accounts, expectedCount, "number of customer accounts should match expected count")
	}
}
