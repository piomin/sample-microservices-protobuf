package pl.piomin.services.protobuf.customer.loadbalancer;

import org.springframework.cloud.client.ServiceInstance;

import java.net.URI;
import java.util.Map;

public class StaticServiceInstance implements ServiceInstance {

    private String serviceName;
    private String address;

    public StaticServiceInstance(String serviceName, String address) {
        this.serviceName = serviceName;
        this.address = address;
    }

    @Override
    public String getServiceId() {
        return serviceName;
    }

    @Override
    public String getHost() {
        return address.split(":", 2)[0];
    }

    @Override
    public int getPort() {
        return Integer.parseInt(address.split(":", 2)[1]);
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public URI getUri() {
        return null;
    }

    @Override
    public Map<String, String> getMetadata() {
        return Map.of();
    }
}
