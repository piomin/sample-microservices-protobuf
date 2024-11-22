package addcustomer

import (
	"testing"
	pb "pl.piomin.services.grpc.customer.model"
)

// NameRequestModifier sets the customer name in the request
func NameRequestModifier(name string) RequestModifier {
	return func(t *testing.T, entities Entities, request *pb.Customer) {
		request.Name = name
	}
}

// PeselRequestModifier sets the customer PESEL in the request
func PeselRequestModifier(pesel string) RequestModifier {
	return func(t *testing.T, entities Entities, request *pb.Customer) {
		request.Pesel = pesel
	}
}

// TypeRequestModifier sets the customer type in the request
func TypeRequestModifier(customerType string) RequestModifier {
	return func(t *testing.T, entities Entities, request *pb.Customer) {
		switch customerType {
		case "INDIVIDUAL":
			request.Type = pb.Customer_INDIVIDUAL
		case "COMPANY":
			request.Type = pb.Customer_COMPANY
		default:
			t.Fatalf("invalid customer type: %s", customerType)
		}
	}
}

// AccountsRequestModifier sets the customer accounts in the request
func AccountsRequestModifier(accountNumbers []string) RequestModifier {
	return func(t *testing.T, entities Entities, request *pb.Customer) {
		accounts := make([]*pb.Account, len(accountNumbers))
		for i, number := range accountNumbers {
			accounts[i] = &pb.Account{
				Number: number,
			}
		}
		request.Accounts = accounts
	}
}

// DefaultRequestModifier sets default values for required fields
func DefaultRequestModifier() RequestModifier {
	return func(t *testing.T, entities Entities, request *pb.Customer) {
		request.Type = pb.Customer_INDIVIDUAL // Default to INDIVIDUAL type
		request.Accounts = []*pb.Account{}    // Initialize empty accounts slice
	}
}
