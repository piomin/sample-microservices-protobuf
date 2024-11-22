package gateway

import (
	"context"
	"github.com/golang/protobuf/ptypes/empty"
	"github.com/golang/protobuf/ptypes/wrappers"
	pb "pl.piomin.services.grpc.customer.model"
	"go.uber.org/yarpc"
)

type gateway struct {
	customerClient pb.CustomersServiceClient
	tenancy       string
}

// Interface for talking to CustomersService endpoints
type Interface interface {
	FindByPesel(context.Context, *wrappers.StringValue, ...yarpc.CallOption) (*pb.Customer, error)
	FindById(context.Context, *wrappers.Int32Value, ...yarpc.CallOption) (*pb.Customer, error)
	FindAll(context.Context, *empty.Empty, ...yarpc.CallOption) (*pb.Customers, error)
	AddCustomer(context.Context, *pb.Customer, ...yarpc.CallOption) (*pb.Customer, error)
}

// New creates a new gateway instance
func New(client pb.CustomersServiceClient, tenancy string) Interface {
	return &gateway{
		customerClient: client,
		tenancy:       tenancy,
	}
}

// FindByPesel is RPC call to find customer by PESEL
func (g *gateway) FindByPesel(ctx context.Context, request *wrappers.StringValue, opts ...yarpc.CallOption) (*pb.Customer, error) {
	requestHeaders := []yarpc.CallOption{}
	requestHeaders = append(requestHeaders, opts...)
	return g.customerClient.FindByPesel(ctx, request, requestHeaders...)
}

// FindById is RPC call to find customer by ID
func (g *gateway) FindById(ctx context.Context, request *wrappers.Int32Value, opts ...yarpc.CallOption) (*pb.Customer, error) {
	requestHeaders := []yarpc.CallOption{}
	requestHeaders = append(requestHeaders, opts...)
	return g.customerClient.FindById(ctx, request, requestHeaders...)
}

// FindAll is RPC call to get all customers
func (g *gateway) FindAll(ctx context.Context, request *empty.Empty, opts ...yarpc.CallOption) (*pb.Customers, error) {
	requestHeaders := []yarpc.CallOption{}
	requestHeaders = append(requestHeaders, opts...)
	return g.customerClient.FindAll(ctx, request, requestHeaders...)
}

// AddCustomer is RPC call to add a new customer
func (g *gateway) AddCustomer(ctx context.Context, request *pb.Customer, opts ...yarpc.CallOption) (*pb.Customer, error) {
	requestHeaders := []yarpc.CallOption{}
	requestHeaders = append(requestHeaders, opts...)
	return g.customerClient.AddCustomer(ctx, request, requestHeaders...)
}
