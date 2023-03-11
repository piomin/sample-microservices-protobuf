package pl.piomin.services.protobuf.customer.loadbalancer;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.core.env.Environment;
import pl.piomin.services.protobuf.customer.config.LoadBalancerConfigurationProperties;
import pl.piomin.services.protobuf.customer.config.ServiceConfig;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StaticServiceInstanceListSupplier implements ServiceInstanceListSupplier {

    private LoadBalancerConfigurationProperties properties;
    private Environment environment;

    @Override
    public String getServiceId() {
        return environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
    }

    @Override
    public Flux<List<ServiceInstance>> get() {
        ServiceConfig config = properties.getInstances().stream()
                .filter(it -> it.getName().equals(getServiceId()))
                .findAny()
                .orElseThrow();

        List<ServiceInstance> instances = Arrays.stream(config.getServers().split(",", 0))
                .map( it -> new StaticServiceInstance(getServiceId(), it))
                .collect(Collectors.toList());

        return Flux.just(instances);
    }
}
