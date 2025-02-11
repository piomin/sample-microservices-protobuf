package accountsservice

import (
	"context"
	"github.com/golang/protobuf/ptypes/empty"
	"github.com/golang/protobuf/ptypes/wrappers"
	pb "pl.piomin.services.grpc.customer.model"
)

type gateway struct {
	accountsServiceClient pb.AccountsServiceClient
	tenancy              string
}

// Interface for talking to AccountsService endpoints
type Interface interface {
	FindByNumber(context.Context, *wrappers.StringValue) (*pb.Account, error)
	FindByCustomer(context.Context, *wrappers.Int32Value) (*pb.Accounts, error)
	FindAll(context.Context, *empty.Empty) (*pb.Accounts, error)
	AddAccount(context.Context, *pb.Account) (*pb.Account, error)
}

// FindByNumber is RPC call to find account by number
func (g *gateway) FindByNumber(ctx context.Context, request *wrappers.StringValue) (*pb.Account, error) {
	return g.accountsServiceClient.FindByNumber(ctx, request)
}

// FindByCustomer is RPC call to find accounts by customer ID
func (g *gateway) FindByCustomer(ctx context.Context, request *wrappers.Int32Value) (*pb.Accounts, error) {
	return g.accountsServiceClient.FindByCustomer(ctx, request)
}

// FindAll is RPC call to find all accounts
func (g *gateway) FindAll(ctx context.Context, request *empty.Empty) (*pb.Accounts, error) {
	return g.accountsServiceClient.FindAll(ctx, request)
}

// AddAccount is RPC call to add a new account
func (g *gateway) AddAccount(ctx context.Context, request *pb.Account) (*pb.Account, error) {
	return g.accountsServiceClient.AddAccount(ctx, request)
}

// New creates a new gateway instance
func New(client pb.AccountsServiceClient, tenancy string) Interface {
	return &gateway{
		accountsServiceClient: client,
		tenancy:              tenancy,
	}
}
